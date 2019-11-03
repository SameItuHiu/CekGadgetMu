package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.example.bagasekaa.cekgadgetmu_2.model.Order;

public class user_order_mitra extends AppCompatActivity {

    public static final String ORDER_ID = " ";

    String nama_toko, info_barang, userID, mAlasan;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listView;
    List<Order> ListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_mitra);

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("toko").child(userID).child("order");

        listView = (ListView) findViewById(R.id.datalist1);

        //list to store artists
        ListData = new ArrayList<>();

        View view1 = getLayoutInflater().inflate(R.layout.popup_order_ditolak, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        //TextView Alasan = view1.findViewById(R.id.alasan);
        //mAlasan = Alasan.getText().toString();
        dialog.setContentView(view1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //getting the selected artist
                Order item = ListData.get(position);

                String status_order = item.getStatus_order();

                if (status_order.equals("ditolak")){
                    //dialog.show();
                }else{
                    //creating an intent
                    Intent intent = new Intent(getApplicationContext(), user_order_mitra2.class);

                    //putting artist name and id to intent
                    intent.putExtra(ORDER_ID, item.getId_order());

                    //starting the activity with intent
                    startActivity(intent);
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListData.clear();

                //iterating through all the nodes
                for (DataSnapshot s : dataSnapshot.getChildren()){

                    //getting artist
                    Order data = s.getValue(Order.class);
                    //adding artist to the list
                    ListData.add(data);
                }

                //creating adapter
                CustomAdapter2 dataAdapter = new CustomAdapter2(user_order_mitra.this, ListData);
                //attaching adapter to the listview
                listView.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(user_order_mitra.this, menu_user.class));
    }
}
package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

import id.example.bagasekaa.cekgadgetmu_2.model.Data;

public class user_order extends AppCompatActivity {

    public static final String ORDER_ID = " ";

    String nama_toko, info_barang, userID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView alasan;

    ListView listView;
    List<Data> ListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("account").child(userID).child("order");

        listView = (ListView) findViewById(R.id.datalist1);

        View view1 = getLayoutInflater().inflate(R.layout.popup_order_ditolak, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        alasan = view1.findViewById(R.id.alasan);
        dialog.setContentView(view1);

        //list to store artists
        ListData = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //getting the selected artist
                Data id_order = ListData.get(position);

                String status_order = id_order.getStatus_order();

                if (status_order.equals("ditolak")){
                    alasan.setText("Maaf atas ketidak nyamanannya, mungkin mitra kami sedang sibuk, atau sedang banyak order");
                    dialog.show();
                }else{
                    //creating an intent
                    Intent intent = new Intent(getApplicationContext(), user_order2.class);

                    //putting artist name and id to intent
                    intent.putExtra(ORDER_ID, id_order.getId_order());

                    //starting the activity with intent
                    startActivity(intent);
                    finish();
                }
            }
        });

        //attaching value event listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListData.clear();

                //iterating through all the nodes
                for (DataSnapshot s : dataSnapshot.getChildren()){

                    //getting artist
                    Data data = s.getValue(Data.class);
                    //adding artist to the list
                    ListData.add(data);
                }

                //creating adapter
                CustomAdapter dataAdapter = new CustomAdapter(user_order.this, ListData);
                //attaching adapter to the listview
                listView.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, menu_user.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void back(View view) {
        startActivity(new Intent(user_order.this, menu_user.class));
        finish();
    }
}
package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class menu_user extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    TextView txt_order;
    ImageView ic_order;
    ImageView alert_order,alert_toko;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        final Button open_servis = findViewById(R.id.btn_buka_servis);
        final TextView nama_user = findViewById(R.id.nama_user);
        final TextView id = findViewById(R.id.id);
        final LinearLayout layout_mitra = findViewById(R.id.layout_mitra);

        txt_order = findViewById(R.id.txt_order);
        ic_order = findViewById(R.id.ic_order);
        alert_order = findViewById(R.id.alert_order);
        alert_toko = findViewById(R.id.alert_toko);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String text1 = dataSnapshot.child("account").child(userID).child("nama").getValue().toString();
                String status = dataSnapshot.child("account").child(userID).child("status").getValue().toString();
                Boolean alert = dataSnapshot.child("account").child(userID).child("alert").getValue(Boolean.class);
                Boolean alert_toko1 = dataSnapshot.child("toko").child(userID).child("alert").getValue(Boolean.class);

                if (status.equals("user") ){
                    open_servis.setVisibility(View.VISIBLE);

                }else if(status.equals("verif")){
                    open_servis.setVisibility(View.VISIBLE);
                } else{
                    layout_mitra.setVisibility(View.VISIBLE);
                }


//                if (alert == null){
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("account").child(userID).child("alert").setValue(false);
//
//                }else if (alert){
//                    alert_order.setVisibility(View.VISIBLE);
//                }else{
//                    alert_order.setVisibility(View.GONE);
//                }
//
//                if (alert_toko1 == null && status.equals("mitra")){
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("toko").child(userID).child("alert").setValue(false);
//
//                }else if (alert_toko1){
//                    alert_toko.setVisibility(View.VISIBLE);
//                }else{
//                    alert_toko.setVisibility(View.GONE);
//                }

                nama_user.setText(text1);
                id.setText(userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });
    }

    public void maps(View view) {
        Intent intent = new Intent(menu_user.this, map.class);
        startActivity(intent);

    }

    public void chat(View view) {
        Intent intent = new Intent(menu_user.this, chat.class);
        startActivity(intent);

    }

    public void akun(View view) {
        Intent intent = new Intent(menu_user.this, user_account.class);
        startActivity(intent);

    }

    public void buka_servis(View view) {

        view = getLayoutInflater().inflate(R.layout.popup_verif_mitra, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String status = dataSnapshot.child("account").child(userID).child("status").getValue().toString();

                if (status.equals("verif")){
                    Intent intent = new Intent(menu_user.this, daftar_servis.class);
                    startActivity(intent);
                    finish();

                }else{
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });


    }

    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(menu_user.this, login.class);
        startActivity(intent);
        finish();
    }

    public void order(View view) {
        Intent intent = new Intent(menu_user.this, user_order.class);
        startActivity(intent);
    }

    public void order_masuk(View view) {
        Intent intent = new Intent(menu_user.this, user_order_mitra.class);
        startActivity(intent);
    }

}

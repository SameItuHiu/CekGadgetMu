package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_account_edit extends AppCompatActivity {

    String userID, status;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    LinearLayout layout_mitra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit);

        layout_mitra = findViewById(R.id.layout_mitra);

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("account").child(userID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status = dataSnapshot.child("status").getValue().toString();
                if (status.equals("mitra")){
                    layout_mitra.setVisibility(View.VISIBLE);
                }else {
                    layout_mitra.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(user_account_edit.this, user_account.class);
        startActivity(intent);
    }

    public void edit_telepon(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_telepon.class);
        startActivity(intent);
    }

    public void edit_email(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_email.class);
        startActivity(intent);
    }

    public void edit_password(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_password.class);
        startActivity(intent);
    }

    public void edit_nama_toko(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_toko_nama.class);
        startActivity(intent);
    }

    public void edit_alamat_toko(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_toko_alamat.class);
        startActivity(intent);
    }

    public void edit_lokasi_toko(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_toko_lokasi.class);
        startActivity(intent);
    }

    public void jadwal_buka(View view) {
        Intent intent = new Intent(user_account_edit.this, edit_jadwal_buka_toko.class);
        startActivity(intent);
    }
}

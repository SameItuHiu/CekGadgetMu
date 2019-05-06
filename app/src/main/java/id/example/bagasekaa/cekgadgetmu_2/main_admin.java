package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class main_admin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public String userId;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        final TextView nameuser = findViewById(R.id.nameUser);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        FirebaseUser user =  mAuth.getCurrentUser();
        userId = user.getUid();

        ref = db.getReference();
        ref.child("account").child(userId).child("nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameUser = dataSnapshot.getValue().toString();
                nameuser.setText(nameUser);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });

    }


    public void status(View view) {

    }

    public void account(View view) {
        Intent intent = new Intent(main_admin.this, profile_mitra.class);
        startActivity(intent);
        finish();
    }

    public void logout(View view) {
        Intent intent = new Intent(main_admin.this, login.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}

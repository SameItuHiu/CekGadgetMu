package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private EditText email,password;
    private FirebaseAuth mAuth;
    public String userId;
    FirebaseDatabase db;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

    }

    public void reset(View view) {
        Intent intent = new Intent(login.this, reset_password.class);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        //menampung imputan user
        final String emailUser = email.getText().toString().trim();
        final String passwordUser = password.getText().toString().trim();

        //validasi email dan password
        // jika email kosong
        if (emailUser.isEmpty()) {
            email.setError("Email tidak boleh kosong");
        }
        // jika email not valid
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
            email.setError("Email tidak valid");
        }
        // jika password kosong
        else if (passwordUser.isEmpty()) {
            password.setError("Password tidak boleh kosong");
        }
        //jika password kurang dari 6 karakter
        else if (passwordUser.length() < 6) {
            password.setError("Password minimal terdiri dari 6 karakter");
        } else {
            mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // ketika gagal locin maka akan do something
                            if (!task.isSuccessful()) {
                                Toast.makeText(login.this,
                                        "Gagal login karena " + task.getException().getMessage()
                                        , Toast.LENGTH_LONG).show();
                            } else {
                                FirebaseUser user =  mAuth.getCurrentUser();
                                userId = user.getUid();

                                setID();
                                ref = db.getReference();
                                ref.child("account").child(userId).child("status").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String cek = dataSnapshot.getValue().toString();

                                        Toast.makeText(login.this,
                                                cek,
                                                Toast.LENGTH_LONG).show();

                                        if (cek.equals("User")){
                                            startActivity(new Intent(login.this, main_user.class));
                                            finish();
                                        }else if (cek.equals("Mitra")){
                                            startActivity(new Intent(login.this, main_admin.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.e("Data", "Error: ", databaseError.toException());
                                    }
                                });
                            }
                        }


                    });
        }

    }
    private void setID() {
        DatabaseReference myRef = db.getReference("set");
        myRef.setValue(userId);
    }
    public void back(View view) {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

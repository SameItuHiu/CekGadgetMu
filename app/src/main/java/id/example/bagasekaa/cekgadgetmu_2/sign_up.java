package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    private EditText email,password,nama;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        nama = findViewById(R.id.nama);
        radioGroup = findViewById(R.id.radio);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("account");

    }

    public void back(View view) {
        Intent intent = new Intent(sign_up.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void signup(View view) {

        String emailUser = email.getText().toString().trim();
        String passwordUser = password.getText().toString().trim();
        final String namaUser = nama.getText().toString().trim();

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);



        //validasi email dan password
        // jika email kosong
        if (emailUser.isEmpty()){
            email.setError("Email tidak boleh kosong");
        }
        // jika email not valid
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
            email.setError("Email tidak valid");
        }
        // jika nama kosong
        else if (namaUser.isEmpty()){
            password.setError("Nama tidak boleh kosong");
        }
        // jika password kosong
        else if (passwordUser.isEmpty()){
            password.setError("Password tidak boleh kosong");
        }
        //jika password kurang dari 6 karakter
        else if (passwordUser.length() < 6){
            password.setError("Password minimal terdiri dari 6 karakter");
        }
        else {


            //create user dengan firebase auth
            mAuth.createUserWithEmailAndPassword(emailUser,passwordUser)
                    .addOnCompleteListener(sign_up.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //jika gagal register do something
                            if (!task.isSuccessful()){
                                Toast.makeText(sign_up.this,
                                        "Register gagal karena "+ task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }else {
                                FirebaseUser user =  mAuth.getCurrentUser();
                                String userId = user.getUid();
                                String status = radioButton.getText().toString();

                                    ref.child(userId).child("status").setValue(status);
                                    ref.child(userId).child("nama").setValue(namaUser);

                                Toast.makeText(sign_up.this,
                                        "Register berhasil",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(sign_up.this,MainActivity.class));
                            }
                        }
                    });
        }
    }


}


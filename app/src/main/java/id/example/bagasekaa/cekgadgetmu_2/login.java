package id.example.bagasekaa.cekgadgetmu_2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
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
    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseUser user;
    TextView txt_peringatan;
    EditText mEmail;

    BottomSheetDialog dialog2,dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        View view1 = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        txt_peringatan = view1.findViewById(R.id.txt_peringatan);
        dialog1 = new BottomSheetDialog(this);
        dialog1.setContentView(view1);

        View view = getLayoutInflater().inflate(R.layout.popup_reset_password, null);
        mEmail = view.findViewById(R.id.email);
        Button kirim = view.findViewById(R.id.kirim);
        dialog2 = new BottomSheetDialog(this);
        dialog2.setContentView(view);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEmail.getText().toString();
                if (mail.isEmpty()){
                    txt_peringatan.setText("Email tidak boleh kosong");
                    dialog1.show();

                }else{
                    mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(login.this,"Please check your email",Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }

    public void login(View view) {

        //menampung inputan user
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

            final AlertDialog.Builder mBuiler = new AlertDialog.Builder(login.this);
            final View mView = getLayoutInflater().inflate(R.layout.popup,null);
            mBuiler.setView(mView);
            final AlertDialog dialog = mBuiler.create();
            dialog.show();

            mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                    .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            user =  mAuth.getCurrentUser();
                            // ketika gagal login maka akan do something
                            if (!task.isSuccessful()) {
                                Toast.makeText(login.this,
                                        "Failed login " + task.getException().getMessage()
                                        , Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else if (user != null && user.isEmailVerified()){
                                //if(user.isEmailVerified()){
                                    dialog.dismiss();
                                    Intent intent = new Intent(login.this, menu_user.class);
                                    startActivity(intent);
                                    finish();
                                //}

                            }else {
                                Toast.makeText(login.this,
                                        "Cek email untuk melakukan verifikasi"
                                        , Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }


                    });
        }

    }
    public void back(View view) {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);

    }

    public void forgot(View view) {
dialog2.show();

    }

    public void signup(View view) {
        Intent intent = new Intent(login.this, sign_up.class);
        startActivity(intent);

    }
}

package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

public class edit_password extends AppCompatActivity {

    TextView mEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        mEmail = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();

    }

    public void back(View view) {
        Intent intent = new Intent(edit_password.this, user_account_edit.class);
        startActivity(intent);
    }

    public void kirim(View view) {
        String mail = mEmail.getText().toString();

        view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
        BottomSheetDialog dialog1 = new BottomSheetDialog(this);
        dialog1.setContentView(view);

        if (mail.isEmpty()){
            txt_peringatan.setText("Email tidak boleh kosong");
            dialog1.show();

        }else{
            mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(edit_password.this,"Please check your email",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}

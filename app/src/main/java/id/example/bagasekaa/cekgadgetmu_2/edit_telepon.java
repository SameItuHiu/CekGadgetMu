package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class edit_telepon extends AppCompatActivity {

    String userID, nomor,VerifikasiID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener stateListener;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    BottomSheetDialog dialog;

    EditText telepon_baru,digit1;
    TextView nomor_telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_telepon);

        telepon_baru = findViewById(R.id.telepon_baru);
        final TextView nomor_telepon = findViewById(R.id.nomor_telepon);

        //user auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("account").child(userID).child("No_Handphone");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nomor = dataSnapshot.getValue().toString();
                nomor_telepon.setText(nomor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        View view = getLayoutInflater().inflate(R.layout.popup_verif_otp, null);
//        dialog = new BottomSheetDialog(this);
//        digit1 = view.findViewById(R.id.digit);
//        nomor_telp = view.findViewById(R.id.nomornya);
//        Button resend = view.findViewById(R.id.resend);
//        Button cek = view.findViewById(R.id.cek);
//        dialog.setContentView(view);

//        resend.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        cek.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void back(View view) {
        Intent intent = new Intent(edit_telepon.this, user_account_edit.class);
        startActivity(intent);
    }

    public void simpan(View view) {
        view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        if (telepon_baru.getText().toString().isEmpty()){
            txt_peringatan.setText("Nomor tidak boleh kosong");
            dialog.show();
        }else{
            txt_peringatan.setText("Nomor telah diganti");
            dialog.show();
            databaseReference.setValue(telepon_baru.getText().toString());
        }


    }
}

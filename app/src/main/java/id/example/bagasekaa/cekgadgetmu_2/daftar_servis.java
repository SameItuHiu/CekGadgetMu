package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class daftar_servis extends AppCompatActivity {

    TextView Toko, Layanan,txt_salam;
    String userID,nama,txt;
    DatabaseReference ref;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_servis);
        context = this;

        Toko = findViewById(R.id.nama_toko);
        Layanan = findViewById(R.id.layanan);
        txt_salam = findViewById(R.id.salam);
        Button finish = findViewById(R.id.finish);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("account").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama = dataSnapshot.child("nama").getValue().toString();
                txt_salam.setText("Hello " + nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mToko = Toko.getText().toString();
                final String mLayanan = Layanan.getText().toString();

                View view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
                final TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(view);

                if (mToko.isEmpty()){
                    txt_peringatan.setText("Nama toko tidak boleh kosong");
                    dialog.show();
                }else if (mLayanan.isEmpty()){
                    txt_peringatan.setText("Layanan toko tidak boleh kosong");
                    dialog.show();
                }else{

                    Bundle bundle = new Bundle();
                    bundle.putString("nama_toko", mToko);
                    bundle.putString("layanan", mLayanan);
                    Intent intent = new Intent(daftar_servis.this, daftar_servis2.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }

    public void Selanjutnya(View view) {
       String mToko = Toko.getText().toString();
       String mLayanan = Layanan.getText().toString();

        view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        TextView txt_peringatan = view.findViewById(R.id.txt_peringatan);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

            if (mToko.isEmpty()){
                txt_peringatan.setText("Nama toko tidak boleh kosong");
                dialog.show();
            }else if (mLayanan.isEmpty()){
                txt_peringatan.setText("Layanan toko tidak boleh kosong");
                dialog.show();
            }else{

                Intent intent = new Intent(daftar_servis.this, daftar_servis2.class);
                FirebaseDatabase.getInstance().getReference().child("toko").child(userID).child("nama_toko").setValue(mToko);
                FirebaseDatabase.getInstance().getReference().child("toko").child(userID).child("layanan").setValue(mLayanan);
                //intent.putExtra(daftar_servis2.mToko, mToko);
                //intent.putExtra(daftar_servis2.mLayanan,mLayanan);
                //txt_peringatan.setText(mToko + mLayanan);
                //dialog.show();
                startActivity(intent);
            }
    }

    public void back(View view) {
        startActivity(new Intent(daftar_servis.this, menu_user.class));

    }
}

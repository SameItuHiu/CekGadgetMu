package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class daftar_servis2 extends AppCompatActivity {

    public static final String mToko = "";
    public static final String mLayanan = "";
    public static final String mLat = "";
    public static final String mLong = "";

    TextView Provinsi, Kota, Alamat,txt_peringatan,txt_salam;
    String userID,mContact,nama,lat;

    BottomSheetDialog dialog;

    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_servis2);

        Provinsi = findViewById(R.id.provinsi);
        Kota = findViewById(R.id.kota);
        Alamat = findViewById(R.id.alamat_lengkap);
        txt_salam = findViewById(R.id.salam);

        lat = getIntent().getStringExtra(mLat);
        //layanan = getIntent().getStringExtra(mLayanan);
        //latitut = getIntent().getStringExtra(mLat);
        //longitut = getIntent().getStringExtra(mLat);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("account").child(userID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mContact = dataSnapshot.child("No_Handphone").getValue().toString();
                nama = dataSnapshot.child("nama").getValue().toString();
                txt_salam.setText("Hello " + nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Data", "Error: ", databaseError.toException());
            }
        });
        View view = getLayoutInflater().inflate(R.layout.popup_peringatan, null);
        txt_peringatan = view.findViewById(R.id.txt_peringatan);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
    }

    public void back(View view) {
        startActivity(new Intent(daftar_servis2.this, daftar_servis.class));
    }

    public void Finish(View view) {

        Bundle bundle = getIntent().getExtras();
        String nama_toko = bundle.getString("nama_toko");
        String layanan = bundle.getString("layanan");

        String mProvinsi = Provinsi.getText().toString();
        String mKota = Kota.getText().toString();
        String mAlamat = Alamat.getText().toString();

        if (mProvinsi.isEmpty()){
            txt_peringatan.setText("Kolom provinsi tidak boleh kosong");
            dialog.show();
        }else if (mKota.isEmpty()){
            txt_peringatan.setText("Kolom kota tidak boleh kosong");
            dialog.show();
        }else if (mAlamat.isEmpty()){
            txt_peringatan.setText("Kolom alamat tidak boleh kosong");
            dialog.show();
        }else{
            Bundle bundle1 = new Bundle();
            bundle1.putString("nama_toko", nama_toko);
            bundle1.putString("layanan", layanan);
            bundle1.putString("provinsi", mProvinsi);
            bundle1.putString("kota", mKota);
            bundle1.putString("alamat", mAlamat);
            Intent intent = new Intent(daftar_servis2.this, set_lokasi.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        }
    }
}

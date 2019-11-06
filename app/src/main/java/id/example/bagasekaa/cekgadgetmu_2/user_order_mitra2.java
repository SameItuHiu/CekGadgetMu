package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.example.bagasekaa.cekgadgetmu_2.model.Rincian;

public class user_order_mitra2 extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String id_order,userID,id_pelanggan,catatan,mcatatan,key;

    LinearLayout Layout_button,layout_isi,layout_catatan,layout_feedback;

    BottomSheetDialog dialog1, dialog ,dialog2;

    int total = 0;
    TextView total_harga;

    ListView list_harga;
    List<Rincian> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_mitra2);

        list_harga = (ListView) findViewById(R.id.list_harga);

        Intent intent = getIntent();
        id_order = intent.getStringExtra(user_order_mitra.ORDER_ID);

        total_harga = findViewById(R.id.total_harga);
        final TextView status = findViewById(R.id.status);
        final TextView order_dibuat = findViewById(R.id.order_dibuat);
        final TextView nama_pelanggan = findViewById(R.id.nama_pelanggan);
        final TextView barang_servis = findViewById(R.id.barang_servis);
        final TextView keluhan = findViewById(R.id.keluhan);
        final TextView arrival = findViewById(R.id.arrival);
        final TextView catatan1 = findViewById(R.id.catatan);
        final TextView like_dislike = findViewById(R.id.like_dislike);
        final TextView txt_komentar = findViewById(R.id.txt_komentar);

        Layout_button = findViewById(R.id.layout_button);
        layout_isi = findViewById(R.id.layout_isi);
        layout_catatan = findViewById(R.id.layout_catatan);
        layout_feedback = findViewById(R.id.layout_feedback);

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("toko").child(userID).child("order");
        databaseReference.orderByChild("id_order")
                .equalTo(id_order)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot s : dataSnapshot.getChildren()){
                            Integer day,month,year,hour,minute, nDay,nMonth,nYear,nHour,nMinute;
                            String mstatus, mbarang,mkeluhan,mpelanggan,mlike_dislike,mfeedback;

                            mstatus = s.child("status_order").getValue().toString();
                            mpelanggan = s.child("nama_pelanggan").getValue().toString();
                            mbarang = s.child("barang").getValue().toString();
                            mkeluhan = s.child("keluhan").getValue().toString();
                            id_pelanggan = s.child("id_pelanggan").getValue().toString();
                            key = s.getKey();

                            day = s.child("arrival").child("tanggal").child("day").getValue(Integer.class);
                            month = s.child("arrival").child("tanggal").child("month").getValue(Integer.class);
                            year = s.child("arrival").child("tanggal").child("year").getValue(Integer.class);
                            //hour = s.child("arrival").child("jam").child("hour").getValue(Integer.class);
                            //minute = s.child("arrival").child("jam").child("minute").getValue(Integer.class);

                            nDay = s.child("day").getValue(Integer.class);
                            nMonth = s.child("month").getValue(Integer.class);
                            nYear = s.child("year").getValue(Integer.class);
                            nHour = s.child("hour").getValue(Integer.class);
                            nMinute = s.child("minute").getValue(Integer.class);

                            status.setText(mstatus);
                            order_dibuat.setText(nHour + ":" + nMinute + " WIB "+" / " + nDay + "-" + nMonth + "-" + nYear);
                            barang_servis.setText(mbarang);
                            keluhan.setText(mkeluhan);
                            nama_pelanggan.setText(mpelanggan);
                            arrival.setText(day + "-" + month + "-" + year);

                            if (mstatus.equals("On Proses")){
                                Layout_button.setVisibility(View.GONE);
                                layout_isi.setVisibility(View.VISIBLE);

                            } else if (mstatus.equals("diterima")){
                                Layout_button.setVisibility(View.GONE);
                                layout_isi.setVisibility(View.GONE);
                                layout_catatan.setVisibility(View.VISIBLE);
                                mcatatan = s.child("catatan_order").getValue().toString();
                                catatan1.setText(mcatatan);
                                list_harga_barang();

                            }
                            //else if (mstatus.equals("ditolak")){

                            //    startActivity(new Intent(user_order_mitra2.this, user_order_mitra.class));

                            //}
                            else if (mstatus.equals("selesai")){
                                Layout_button.setVisibility(View.GONE);
                                layout_isi.setVisibility(View.GONE);
                                layout_catatan.setVisibility(View.VISIBLE);
                                layout_feedback.setVisibility(View.VISIBLE);
                                mcatatan = s.child("catatan_order").getValue().toString();
                                mlike_dislike = s.child("feedback").child("status").getValue().toString();
                                mfeedback = s.child("feedback").child("komentar").getValue().toString();

                                list_harga_barang();

                                like_dislike.setText(mlike_dislike);
                                txt_komentar.setText(mfeedback);
                                catatan1.setText(mcatatan);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Data", "Error: ", databaseError.toException());
                    }
                });

        //Jika orderan ditolak oleh mitra
        View view = getLayoutInflater().inflate(R.layout.popup_tolak_order, null);
        final EditText alasan = view.findViewById(R.id.alasan);
        Button kirim_alasan = view.findViewById(R.id.alasan_kirim);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        kirim_alasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAlasan = alasan.getText().toString();
                if (mAlasan.isEmpty()){
                    alasan.setError("Mohon diisi alasan");
                }else{
                    FirebaseDatabase.getInstance().getReference().child("toko").child(userID).child("order").child(id_order).child("status_order").setValue("ditolak");
                    FirebaseDatabase.getInstance().getReference().child("account").child(id_pelanggan).child("order").child(id_order).child("status_order").setValue("ditolak");
                    FirebaseDatabase.getInstance().getReference().child("account").child(id_pelanggan).child("order").child(id_order).child("alasan").setValue(mAlasan);
                    dialog.dismiss();
                    startActivity(new Intent(user_order_mitra2.this, user_order_mitra.class));
                    finish();
                }
            }
        });

        //Mengisi catatan kerusakan
        View view1 = getLayoutInflater().inflate(R.layout.popup_catatan, null);
        final EditText txt_catatan = view1.findViewById(R.id.catatan);
        Button catatan_kirim = view1.findViewById(R.id.catatan_kirim);
        dialog1 = new BottomSheetDialog(this);
        dialog1.setContentView(view1);

        catatan_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catatan = txt_catatan.getText().toString();
                dialog1.dismiss();
            }
        });

        //Mengisi rincian biaya
        View view2 = getLayoutInflater().inflate(R.layout.popup_rincian, null);
        final EditText sparepart = view2.findViewById(R.id.sparepart);
        final EditText harga = view2.findViewById(R.id.harga);
        final EditText jumlah = view2.findViewById(R.id.jumlah);
        final ListView mList = view2.findViewById(R.id.list_harga);

        Button tambah = view2.findViewById(R.id.tambah);
        dialog2 = new BottomSheetDialog(this);
        dialog2.setContentView(view2);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mSparepart = sparepart.getText().toString();
                final String mHarga = harga.getText().toString();
                final String mJumlah = jumlah.getText().toString();
                //checking if the value is provided
                if (mSparepart.isEmpty()){
                    sparepart.setError("Mohon di isi");
                }else if (mHarga.isEmpty()){
                    harga.setError("Mohon di isi");
                }else if (mJumlah.isEmpty()){
                    jumlah.setError("Mohon di isi");
                }else{
                        //getting a unique id using push().getKey() method
                        //it will create a unique id and we will use it as the Primary Key for our Artist

                        String id = databaseReference.child(key).child("rincian_harga").push().getKey();

                        //creating an Artist Object
                        Rincian rincian = new Rincian(mSparepart, mHarga, mJumlah);

                        //Saving the Artist
                        databaseReference.child(key).child("rincian_harga").child(id).setValue(rincian);
                        FirebaseDatabase.getInstance().getReference().child("account")
                                .child(id_pelanggan).child("order").child(key).child("rincian_harga").child(id).setValue(rincian);
                        //setting edittext to blank again
                        sparepart.setText("");
                        harga.setText("");
                        jumlah.setText("");

                        //displaying a success toast
                        Toast.makeText(user_order_mitra2.this,
                                "Barang telah ditambah"
                                , Toast.LENGTH_SHORT).show();


                    }
                }

        });

    }

    public void back(View view) {
        startActivity(new Intent(user_order_mitra2.this, user_order_mitra.class));
        finish();
    }

    public void catatan(View view) {
        dialog1.show();
    }

    public void rincian(View view) {
        dialog2.show();
    }

    public void tolak_order(View view) {
        dialog.show();
    }

    public void terima_order(View view) {
        Layout_button.setVisibility(View.GONE);
        FirebaseDatabase.getInstance().getReference().child("toko").child(userID).child("order").child(id_order).child("status_order").setValue("On Proses");
        FirebaseDatabase.getInstance().getReference().child("account").child(id_pelanggan).child("order").child(id_order).child("status_order").setValue("On Proses");
        finish();
        startActivity(getIntent());
    }

    public void kirim(View view) {
        FirebaseDatabase.getInstance().getReference().child("toko").child(userID).child("order").child(id_order).child("catatan_order").setValue(catatan);
        FirebaseDatabase.getInstance().getReference().child("account").child(id_pelanggan).child("order").child(id_order).child("catatan_mitra").setValue(catatan);

        FirebaseDatabase.getInstance().getReference().child("toko").child(userID).child("order").child(id_order).child("status_order").setValue("diterima");
        FirebaseDatabase.getInstance().getReference().child("account").child(id_pelanggan).child("order").child(id_order).child("status_order").setValue("diterima");
        finish();
        startActivity(getIntent());
    }

    public void list_harga_barang(){
        listdata = new ArrayList<>();
        //attaching value event listener
        FirebaseDatabase.getInstance().getReference()
                .child("toko").child(userID).child("order").child(key)
                .child("rincian_harga").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                listdata.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //getting artist
                    Rincian data = postSnapshot.getValue(Rincian.class);
                    //adding artist to the list
                    listdata.add(data);
                    Integer harga = Integer.valueOf(data.getHarga());
                    Integer jumlah = Integer.valueOf(data.getJumlah());
                    total = total + harga * jumlah;
                    String value = String.valueOf(total);
                    total_harga.setText(value);

                }

                //creating adapter
                CustomAdapter3 adapter = new CustomAdapter3(user_order_mitra2.this, listdata);
                //attaching adapter to the listview
                list_harga.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, user_order_mitra.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}

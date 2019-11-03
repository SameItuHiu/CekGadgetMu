package id.example.bagasekaa.cekgadgetmu_2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class map extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;

    BottomSheetBehavior bottomSheetBehavior;
    Button order;

    private Context context;

    String userID, nama_toko, status, key,mToko_buka,nToko_buka,mToko_tutup,nToko_tutup,mJasa,nomor;
    private DatabaseReference ref, ref1;

    LinearLayout llBottomSheet;
    TextView mToko,mStatus,toko_buka,toko_tutup,jasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = this;

        //user auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //maps
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        // get the bottom sheet view
        llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        //Set Component Bottom Sheet
        ImageView chat = llBottomSheet.findViewById(R.id.chat);
        order = llBottomSheet.findViewById(R.id.order);
        mToko = llBottomSheet.findViewById(R.id.name);
        mStatus = llBottomSheet.findViewById(R.id.status);
        toko_buka = llBottomSheet.findViewById(R.id.toko_buka);
        toko_tutup = llBottomSheet.findViewById(R.id.toko_tutup);
        jasa = llBottomSheet.findViewById(R.id.jasa);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("account").child(key).child("No_Handphone");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String hp = dataSnapshot.getValue().toString();

                        View view = getLayoutInflater().inflate(R.layout.popup_contact, null);
                        TextView nomor = view.findViewById(R.id.nomor);
                        TextView whatsapp = view.findViewById(R.id.whatsapp);

                        nomor.setText(hp);
                        whatsapp.setText(hp);
                        BottomSheetDialog dialog = new BottomSheetDialog(context);
                        dialog.setContentView(view);
                        dialog.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatus.getText().toString().equals("BUKA")){
                    Intent intent = new Intent(map.this, User_Diagnosa.class);
                    intent.putExtra(User_Diagnosa.key, key);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng latLng1 = new LatLng(-6.295503, 106.7083125);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 11));

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        ref = FirebaseDatabase.getInstance().getReference().child("toko");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    String mKey = s.getKey();

                    String mLat = s.child("alamat").child("kordinat").child("latitude").getValue(String.class);
                    Double nLat = Double.parseDouble(mLat);
                    String mLong = s.child("alamat").child("kordinat").child("longitude").getValue(String.class);
                    Double nLong = Double.parseDouble(mLong);
                    String nama_toko = s.child("nama_toko").getValue(String.class);

                    if (mKey.equals(userID)){
                        LatLng location = new LatLng(nLat,nLong);
                        mGoogleMap.addMarker(new MarkerOptions().position(location)
                                .title("Toko Anda")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }else{
                        LatLng location = new LatLng(nLat,nLong);
                        mGoogleMap.addMarker(new MarkerOptions().position(location)
                                .title(nama_toko)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                ref1 = FirebaseDatabase.getInstance().getReference("toko");
                        ref1.orderByChild("nama_toko")
                            .equalTo(marker.getTitle())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    key = snap.getKey();
                                    if (!key.equals(userID)){

                                        nama_toko = snap.child("nama_toko").getValue(String.class);
                                        mToko_buka = snap.child("jadwal").child("buka").child("jam").getValue().toString();
                                        nToko_buka = snap.child("jadwal").child("buka").child("menit").getValue().toString();
                                        mToko_tutup = snap.child("jadwal").child("tutup").child("jam").getValue().toString();
                                        nToko_tutup = snap.child("jadwal").child("tutup").child("menit").getValue().toString();
                                        mJasa = snap.child("layanan").getValue().toString();

                                        final Calendar c = Calendar.getInstance();
                                        int mHour = c.get(Calendar.HOUR_OF_DAY);
                                        int mMinute = c.get(Calendar.MINUTE);

                                        int jambuka = Integer.valueOf(mToko_buka);
                                        int menitbuka = Integer.valueOf(nToko_buka);

                                        int jamtutup = Integer.valueOf(mToko_tutup);
                                        int menittutup = Integer.valueOf(nToko_tutup);

                                        if (jambuka <= mHour && jamtutup >= mHour){
                                            mStatus.setText("BUKA");
                                            order.setText("Order Now");

                                        }else if(jambuka == mHour){
                                            if (menitbuka <= mMinute){
                                                mStatus.setText("BUKA");
                                                order.setText("Order Now");
                                            }else{
                                                mStatus.setText("TUTUP");
                                                order.setText("Not Available");
                                            }
                                        }else if(jamtutup == mHour){
                                            if (menittutup >= mMinute){
                                                mStatus.setText("BUKA");
                                                order.setText("Order Now");
                                            }else{
                                                mStatus.setText("TUTUP");
                                                order.setText("Not Available");
                                            }
                                        } else{
                                                mStatus.setText("TUTUP");
                                                order.setText("Not Available");
                                        }

                                        mToko.setText(nama_toko);
                                        //.setText(layanan);
                                        toko_buka.setText(mToko_buka + ":" + nToko_buka + "WIB");
                                        toko_tutup.setText(mToko_tutup + ":" + nToko_tutup+"WIB");
                                        jasa.setText(mJasa);

                                        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        } else {
                                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                });
                return false;
            }
        });

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(map.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void back(View view) {
        Intent intent = new Intent(map.this, menu_user.class);
        startActivity(intent);
    }

}
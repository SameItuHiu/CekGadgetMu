package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class profile_mitra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_mitra);
    }

    public void menu(View view) {
        Intent intent = new Intent(profile_mitra.this, main_admin.class);
        startActivity(intent);
        finish();
    }
}

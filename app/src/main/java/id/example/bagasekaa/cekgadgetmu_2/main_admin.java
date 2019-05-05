package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class main_admin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

    }


    public void status(View view) {

    }

    public void account(View view) {
        Intent intent = new Intent(main_admin.this, profile_mitra.class);
        startActivity(intent);
        finish();
    }

    public void logout(View view) {
        Intent intent = new Intent(main_admin.this, login.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}

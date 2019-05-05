package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class main_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
    }

    public void search(View view) {
        Intent intent = new Intent(main_user.this, cari_tukang.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }

    public void logout(View view) {
        Intent intent = new Intent(main_user.this, login.class);
        startActivity(intent);
        finish();
    }

    public void status(View view) {
        Intent intent = new Intent(main_user.this, Order_Status.class);
        startActivity(intent);
        finish();
    }

    public void account(View view) {
        Intent intent = new Intent(main_user.this, profile_user.class);
        startActivity(intent);
        finish();
    }
}

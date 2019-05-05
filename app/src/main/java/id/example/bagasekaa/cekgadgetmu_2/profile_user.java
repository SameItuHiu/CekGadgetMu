package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class profile_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
    }

    public void menu(View view) {
        Intent intent = new Intent(profile_user.this, main_user.class);
        startActivity(intent);
        finish();
    }
}

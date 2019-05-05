package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class reset_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }
    public void back(View view) {
        Intent intent = new Intent(reset_password.this, login.class);
        startActivity(intent);
        finish();
    }
}

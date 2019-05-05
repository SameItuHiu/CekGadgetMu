package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this,login.class);
        startActivity(intent);
        finish();
    }

    public void create_account(View view) {
        Intent intent = new Intent(MainActivity.this, sign_up.class);
        startActivity(intent);
        finish();
    }

}

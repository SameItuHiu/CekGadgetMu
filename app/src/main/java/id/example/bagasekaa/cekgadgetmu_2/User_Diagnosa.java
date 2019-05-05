package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class User_Diagnosa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_diagnosa);
    }

    public void back(View view) {
        Intent intent = new Intent(User_Diagnosa.this, cari_tukang.class);
        startActivity(intent);
        finish();
    }
}

package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class edit_toko_buka extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_toko_buka);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, user_account_edit.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}

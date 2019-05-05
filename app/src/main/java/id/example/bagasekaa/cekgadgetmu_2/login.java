package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);

    }

    public void reset(View view) {
        Intent intent = new Intent(login.this, reset_password.class);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        if (email.getText().toString().equals("user")){
            Intent intent = new Intent(login.this, main_user.class);
            startActivity(intent);
            finish();
        }if (email.getText().toString().equals("admin")){
            Intent intent = new Intent(login.this, main_admin.class);
            startActivity(intent);
            finish();
        }

    }

    public void back(View view) {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

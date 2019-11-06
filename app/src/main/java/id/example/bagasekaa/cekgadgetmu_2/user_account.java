package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import id.example.bagasekaa.cekgadgetmu_2.account.mitra;
import id.example.bagasekaa.cekgadgetmu_2.account.user;

public class user_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);


        // find views by id
        ViewPager vPager = findViewById(R.id.pager);
        TabLayout tLayout = findViewById(R.id.tablayout);

        // attach tablayout with viewpager
        tLayout.setupWithViewPager(vPager);

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());

        // add your fragments
        adapter.addFrag(new user(), "User");
        adapter.addFrag(new mitra(), "Mitra");

        // set adapter on viewpager
        vPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, menu_user.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void back(View view) {
        Intent intent = new Intent(user_account.this, menu_user.class);
        startActivity(intent);
        finish();
    }

    public void setting(View view) {
        Intent intent = new Intent(user_account.this, user_account_edit.class);
        startActivity(intent);
        finish();
    }
}

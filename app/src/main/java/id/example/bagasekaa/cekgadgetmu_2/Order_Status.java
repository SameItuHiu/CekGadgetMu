package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import id.example.bagasekaa.cekgadgetmu_2.fragment_order.proses_akhir;
import id.example.bagasekaa.cekgadgetmu_2.fragment_order.status_kerusakan;

public class Order_Status extends AppCompatActivity {
    status_kerusakan kerusakan;
    proses_akhir akhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabPageAdapter myPagerAdapter = new TabPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void menu(View view) {
        Intent intent = new Intent(Order_Status.this, main_user.class);
        startActivity(intent);
        finish();
    }
}
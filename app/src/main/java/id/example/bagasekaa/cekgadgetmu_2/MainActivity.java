package id.example.bagasekaa.cekgadgetmu_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import id.example.bagasekaa.cekgadgetmu_2.intro.intro1;
import id.example.bagasekaa.cekgadgetmu_2.intro.intro2;
import id.example.bagasekaa.cekgadgetmu_2.intro.intro3;
import id.example.bagasekaa.cekgadgetmu_2.intro.intro4;

public class MainActivity extends AppCompatActivity {

    SpringDotsIndicator dotsIndicator;
    ViewPager mPager;
    intro1 intro1;
    intro2 intro2;
    intro3 intro3;
    intro4 intro4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        mPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mPager);
        dotsIndicator.setViewPager(mPager);
        dotsIndicator.setDotsClickable(true);

    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this,login.class);
        startActivity(intent);

    }

    public void create_account(View view) {
        Intent intent = new Intent(MainActivity.this, sign_up.class);
        startActivity(intent);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        intro1 = new intro1();
        intro2 = new intro2();
        intro3 = new intro3();
        intro4 = new intro4();
        viewPagerAdapter.addFragment(intro1);
        viewPagerAdapter.addFragment(intro2);
        viewPagerAdapter.addFragment(intro3);
        viewPagerAdapter.addFragment(intro4);
        viewPager.setAdapter(viewPagerAdapter);
    }
}

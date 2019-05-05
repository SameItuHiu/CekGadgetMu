package id.example.bagasekaa.cekgadgetmu_2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.example.bagasekaa.cekgadgetmu_2.fragment_order.proses_akhir;
import id.example.bagasekaa.cekgadgetmu_2.fragment_order.status_kerusakan;

public class TabPageAdapter extends FragmentPagerAdapter {

    public static int PAGE_COUNT = 2;
    private String judulTab[] = new String[]{
            "Kerusakan","Go"
    };

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new status_kerusakan();
            case 1:
                return new proses_akhir();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return judulTab[position];
    }

}
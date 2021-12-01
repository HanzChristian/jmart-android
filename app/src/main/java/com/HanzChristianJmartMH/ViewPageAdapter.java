package com.HanzChristianJmartMH;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//Sebagai controller terhadap fragment dan tabs pada main Activity
public class ViewPageAdapter extends FragmentPagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    //Mengambil posisis antara 2 fragment berbeda
    public Fragment getItem(int positions) {
        switch (positions){
            case 0:
                return ProductsFragment.newInstance();
            case 1:
                return FilterFragment.newInstance();
            default:
                return FilterFragment.newInstance();
        }
    }

    //Menghitung jumlah tab yang ada
    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    //Mengambil posisi antara tab yang diklik
    public CharSequence getPageTitle(int positions) {
        switch (positions){
            case 0:
                return "PRODUCTS";
            case 1:
                return "FILTER";
            default:
                return "DEFAULT";
        }
    }
}

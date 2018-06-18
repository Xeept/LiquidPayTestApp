package com.reuben.liquidpaytestapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.reuben.liquidpaytestapp.fragments.Simple_Introduction_0;
import com.reuben.liquidpaytestapp.fragments.Simple_Introduction_1;
import com.reuben.liquidpaytestapp.fragments.Simple_Introduction_2;

public class SimpleIntroductionPagerAdapter extends FragmentPagerAdapter {

    public SimpleIntroductionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Simple_Introduction_0();
            case 1:
                return new Simple_Introduction_1();
            case 2:
                return new Simple_Introduction_2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

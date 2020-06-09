package com.otta.eventall.Activities.Home.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.otta.eventall.Activities.Home.Fragment.Banner_Four;
import com.otta.eventall.Activities.Home.Fragment.Banner_One;
import com.otta.eventall.Activities.Home.Fragment.Banner_Three;
import com.otta.eventall.Activities.Home.Fragment.Banner_Two;

public class BannerAdapter extends FragmentPagerAdapter {

    public BannerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Banner_One();
            case 1:
                return new Banner_Two();
            case 2:
                return new Banner_Three();
            case 3:
                return new Banner_Four();
            default:
                return new Banner_One();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

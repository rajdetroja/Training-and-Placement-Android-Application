package com.example.tnpcell;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Dashboard homeFragment = new Dashboard(myContext);
                return homeFragment;
            case 1:
                Blog sportFragment = new Blog(myContext);
                return sportFragment;
            case 2:
                Profile movieFragment = new Profile(myContext);
                return movieFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs

    @Override
    public int getCount() {
        return totalTabs;
    }
}

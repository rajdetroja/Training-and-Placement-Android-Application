package com.example.tnpcell;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

public class tnpAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public tnpAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tnpDashboard homeFragment = new tnpDashboard(myContext);
                return homeFragment;
            case 1:
                tnpBlog sportFragment = new tnpBlog(myContext);
                return sportFragment;
            case 2:
                tnpRequest movieFragment = new tnpRequest(myContext);
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

package com.example.tnpcell;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;

public class useractivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractivity);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));
        tabLayout.addTab(tabLayout.newTab().setText("Blog"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
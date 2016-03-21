package com.juztoss.dancemaker.activities;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

/**
 * Created by Kirill on 3/21/2016.
 */
public class MyActionBarDrawerToggle extends ActionBarDrawerToggle {
    private MainActivity mActivity;
    public MyActionBarDrawerToggle(MainActivity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        mActivity = activity;
    }

    public MyActionBarDrawerToggle(MainActivity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        mActivity = activity;
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        super.onDrawerStateChanged(newState);
        mActivity.syncDrawerState();
    }
}

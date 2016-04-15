package com.juztoss.dancemaker;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.juztoss.dancemaker.activities.MainActivity;

import java.util.ArrayList;

/**
 * Created by Kirill on 4/10/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @MediumTest
    public void testNavigation() throws Exception {

        DrawerLayout drawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        View homeBtn = toolbar.getChildAt(1);

        //Greetings screen is visible, the drawer is closed
        assertEquals(false, drawer.isDrawerOpen(GravityCompat.START));

        //Greetengs screen is visible, the drawer is opened
        TouchUtils.clickView(this, homeBtn);
        assertEquals(true, drawer.isDrawerOpen(GravityCompat.START));

        //Greetengs screen is visible, the drawer is closed
        TouchUtils.clickView(this, homeBtn);
        assertEquals(false, drawer.isDrawerOpen(GravityCompat.START));
    }

}

package com.juztoss.dancemaker.activities;

import android.animation.ValueAnimator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Kirill on 3/21/2016.
 */
public class MyActionBarDrawerToggle extends ActionBarDrawerToggle {
    private MainActivity mActivity;
    private View mDrawerLayout;
    public MyActionBarDrawerToggle(MainActivity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        this(activity, drawerLayout, null, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public MyActionBarDrawerToggle(MainActivity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        mActivity = activity;
        mDrawerLayout = drawerLayout;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
    }


    @Override
    public void syncState() {
        if (mActivity.getFragmentManager().getBackStackEntryCount() > 0) {
            animate();
        }
        else
            super.syncState();
    }

    private void animate() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                onDrawerSlide(mDrawerLayout, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(500);
        anim.start();
    }
}

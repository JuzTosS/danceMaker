package com.juztoss.dancemaker.activities;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.fragments.AddNewSequenceFragment;
import com.juztoss.dancemaker.fragments.ElementViewFragment;
import com.juztoss.dancemaker.fragments.ElementsListForSeqFragment;
import com.juztoss.dancemaker.fragments.ElementsListFragment;
import com.juztoss.dancemaker.fragments.SequenceViewFragment;
import com.juztoss.dancemaker.fragments.SequencesListFragment;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceException;
import com.juztoss.dancemaker.model.DanceSequence;
import com.juztoss.dancemaker.model.DanceSpace;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static DanceSpace mDanceSpace;
    DrawerArrowDrawable mHamburger;
    private Toolbar mToolbar;

    public DanceSpace getDanceSpace() {
        return mDanceSpace;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            if (mDanceSpace == null)
                mDanceSpace = new DanceSpace(this);
        } catch (DanceException e) {
            Log.e(getResources().getString(R.string.app_name), "A DanceSpace object already has been created!");
        }
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mHamburger = new DrawerArrowDrawable(mToolbar.getContext());
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(mHamburger);
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                syncDrawerState();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showAllElements();
    }

    protected void syncDrawerState() {
        boolean hasItemsInHistory = getFragmentManager().getBackStackEntryCount() > 0;
        float from = hasItemsInHistory ? 0 : 1;
        float to = hasItemsInHistory ? 1 : 0;

        if(mHamburger.getProgress() == to)
            return;

        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                mHamburger.setProgress(slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(250);
        anim.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        FragmentTransaction t = transaction.replace(R.id.container, fragment);
        t = t.addToBackStack(null);
        t.commit();

        syncDrawerState();
    }

    public void showAllElements() {
        Fragment fragment = new ElementsListFragment();
        showFragment(fragment);
    }

    public void showAllSequences() {
        Fragment fragment = new SequencesListFragment();
        showFragment(fragment);
    }

    public void showSequence(DanceSequence sequence) {
        Fragment fragment = new SequenceViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceSequence.ALIAS, sequence);
        fragment.setArguments(args);
        showFragment(fragment);
    }

    public void showElement(DanceElement element) {
        Fragment fragment = new ElementViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceElement.ALIAS, element);
        fragment.setArguments(args);
        showFragment(fragment);
    }

    public void showAddNewElement() {
        Fragment fragment = new ElementViewFragment();
        showFragment(fragment);
    }

    public void showAddNewSequence() {
        Fragment fragment = new AddNewSequenceFragment();
        showFragment(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            return super.onOptionsItemSelected(item);
        } else if(id == android.R.id.home){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
            else if (!drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.element_list:
                showAllElements();
                break;
            case R.id.element_create_sequence:
                showAllSequences();
                break;
        }

        return false;
    }

    public void showAddNewElementToSequence(DanceSequence sequence) {
        Fragment fragment = new ElementsListForSeqFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceSequence.ALIAS, sequence);
        fragment.setArguments(args);
        showFragment(fragment);
    }
}

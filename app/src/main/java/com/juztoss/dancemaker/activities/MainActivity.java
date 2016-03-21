package com.juztoss.dancemaker.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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
    private ActionBarDrawerToggle mDrawerToggle;
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
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new MyActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        setSupportActionBar(mToolbar);

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                syncDrawerState();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showAllElements(0);
    }

    void syncDrawerState() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            mDrawerToggle.onDrawerOpened(null);
        }
        else
            mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment, int animation) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(animation);

        transaction.replace(R.id.container, fragment).addToBackStack("backFragment").commit();
    }

    public void showAllElements(int animation) {
        Fragment fragment = new ElementsListFragment();
        showFragment(fragment, animation);
    }

    public void showAllSequences(int animation) {
        Fragment fragment = new SequencesListFragment();
        showFragment(fragment, animation);
    }

    public void showSequence(DanceSequence sequence) {
        Fragment fragment = new SequenceViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceSequence.ALIAS, sequence);
        fragment.setArguments(args);
        showFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    public void showElement(DanceElement element) {
        Fragment fragment = new ElementViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceElement.ALIAS, element);
        fragment.setArguments(args);
        showFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    public void showAddNewElement() {
        Fragment fragment = new ElementViewFragment();
        showFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    public void showAddNewSequence() {
        Fragment fragment = new AddNewSequenceFragment();
        showFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int stack = getFragmentManager().getBackStackEntryCount();
        if (stack <= 1) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.element_list:
                showAllElements(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            case R.id.element_create_sequence:
                showAllSequences(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
        }

        return false;
    }

    public void showAddNewElementToSequence(DanceSequence sequence) {
        Fragment fragment = new ElementsListForSeqFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceSequence.ALIAS, sequence);
        fragment.setArguments(args);
        showFragment(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }
}

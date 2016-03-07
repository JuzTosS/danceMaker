package com.juztoss.dancemaker.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.fragments.AddNewElementFragment;
import com.juztoss.dancemaker.fragments.AddNewSequenceFragment;
import com.juztoss.dancemaker.fragments.ElementsListFragment;
import com.juztoss.dancemaker.fragments.SequenceViewFragment;
import com.juztoss.dancemaker.fragments.SequencesListFragment;
import com.juztoss.dancemaker.model.DanceSequence;
import com.juztoss.dancemaker.model.DanceSpace;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DanceSpace mDanceSpace;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    public DanceSpace getDanceSpace() {
        return mDanceSpace;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDanceSpace = new DanceSpace(this);

        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        setSupportActionBar(mToolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showAllElements(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(Fragment fragment, Boolean showBackButton, int animation)
    {
        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(animation);

        transaction.replace(R.id.container, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(showBackButton)
            mDrawerToggle.onDrawerOpened(null);
        else
            mDrawerToggle.syncState();
    }

    public void showAllElements(int animation) {
        Fragment fragment = new ElementsListFragment();
        showFragment(fragment, false, animation);
    }

    public void showAllSequences(int animation) {
        Fragment fragment = new SequencesListFragment();
        showFragment(fragment, false, animation);
    }

    public void showSequence(DanceSequence sequence) {
        Fragment fragment = new SequenceViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(DanceSequence.ALIAS, sequence);
        fragment.setArguments(args);
        showFragment(fragment, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    public void showAddNewElement() {
        Fragment fragment = new AddNewElementFragment();
        showFragment(fragment, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    public void showAddNewSequence() {
        Fragment fragment = new AddNewSequenceFragment();
        showFragment(fragment, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
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
}

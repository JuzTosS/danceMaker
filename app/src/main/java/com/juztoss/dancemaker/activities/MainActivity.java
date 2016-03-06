package com.juztoss.dancemaker.activities;

import android.app.Fragment;
import android.app.FragmentManager;
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

        showAllElements();
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

    public void showAllElements() {
        Fragment fragment = new ElementsListFragment();
        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        mDrawerToggle.syncState();
    }

    public void showAllSequences() {
        Fragment fragment = new SequencesListFragment();
        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        mDrawerToggle.syncState();
    }

    public void showAddNewElement() {
        Fragment fragment = new AddNewElementFragment();
        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle.onDrawerOpened(null);

        drawer.closeDrawer(GravityCompat.START);
    }

    public void showAddNewSequence() {
        Fragment fragment = new AddNewSequenceFragment();
        fragment.setHasOptionsMenu(true);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle.onDrawerOpened(null);
        drawer.closeDrawer(GravityCompat.START);
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
                showAllElements();
                break;
            case R.id.element_create_sequence:
                showAllSequences();
                break;
        }

        return false;
    }

    public void onSequenceSaveClick(View view) {
        TextView nameField = (TextView) findViewById(R.id.element_name);
        TextView lengthField = (TextView) findViewById(R.id.element_length);

        try {
            DanceSequence newSequence = new DanceSequence(nameField.getText().toString(), mDanceSpace.getAllElements());
            mDanceSpace.save(newSequence);
        } catch (Exception e) {
            Toast.makeText(this, "An error occurred while trying to save new sequence!", Toast.LENGTH_SHORT).show();
        }

        nameField.setText("");
        lengthField.setText("");
    }
}

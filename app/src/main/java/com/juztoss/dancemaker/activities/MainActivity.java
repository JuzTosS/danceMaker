package com.juztoss.dancemaker.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.juztoss.dancemaker.AddNewElementFragment;
import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.fragments.ElementsListFragment;
import com.juztoss.dancemaker.fragments.SequenceListFragment;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceSpace;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DanceSpace mDanceSpace;

    public DanceSpace getDanceSpace() {
        return mDanceSpace;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDanceSpace = new DanceSpace(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager(); // For AppCompat use getSupportFragmentManager
        int id = item.getItemId();
        switch (id) {
            case R.id.element_list:
                fragment = new ElementsListFragment();
                break;
            case R.id.element_create_sequence:
                fragment = new SequenceListFragment();
                break;
            default:
                return false;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void onSaveClick(View view) {
        TextView nameField = (TextView) findViewById(R.id.element_name);
        TextView lengthField = (TextView) findViewById(R.id.element_length);

        try {
            DanceElement newElement = new DanceElement(nameField.getText().toString(), Integer.parseInt(lengthField.getText().toString()));
            mDanceSpace.save(newElement);
        } catch (Exception e) {
            Toast.makeText(this, "An error occurred while trying to save new element!", Toast.LENGTH_SHORT).show();
        }

        nameField.setText("");
        lengthField.setText("");
    }
}

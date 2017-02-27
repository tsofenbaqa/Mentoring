package com.example.a2017.mentoring.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.a2017.mentoring.Fragments.MeetingFragment;
import com.example.a2017.mentoring.Fragments.MenteeProfileFragment;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.Utils.Preferences;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private static final int READ_PERMISSION_CODE = 123;
    private boolean isProfileUpdate ;
    private boolean isMentee ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        isProfileUpdate = Preferences.isProfileUpdate(this);
        isMentee = Preferences.isMentee(this);
        actionBarDrawerToggle();
        configureRequestPermissions();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(isMentee)
        {
            if(isProfileUpdate)
            {
                goToMettingFragment();
            }
            else
            {
                goToMenteeProfile();
            }
        }
        else
        {
            if(isProfileUpdate)
            {
                goToMenteeList();
            }
            else
            {
                goToMentorProfile();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (id)
        {
            case R.id.profile :
                MenteeProfileFragment menteeProfileFragment = new MenteeProfileFragment();
                transaction.replace(R.id.fragment_container, menteeProfileFragment,"PROFILE_FRAGMENT");
                transaction.commit();
                break;
            case R.id.logout:
                Intent intent = new Intent(this,WelcomeActivity.class);
                startActivity(intent);
                this.finish();
                Preferences.setLogin(false,this);
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void actionBarDrawerToggle()
    {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void goToMettingFragment()
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MeetingFragment meetingFragment = new MeetingFragment();
        transaction.replace(R.id.fragment_container, meetingFragment,"MEETING_FRAGMENT");
        transaction.commit();
    }
    private void goToMenteeProfile()
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MenteeProfileFragment menteeProfile = new MenteeProfileFragment();
        transaction.replace(R.id.fragment_container, menteeProfile,"MENTEE_PROFILE");
        transaction.commit();
    }
    private void goToMentorProfile()
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MenteeProfileFragment menteeProfile = new MenteeProfileFragment();
        transaction.replace(R.id.fragment_container, menteeProfile,"MENTEE_PROFILE");
        transaction.commit();
    }

    private void goToMenteeList()
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MenteeProfileFragment menteeProfile = new MenteeProfileFragment();
        transaction.replace(R.id.fragment_container, menteeProfile,"MENTEE_PROFILE");
        transaction.commit();
    }

    private void configureRequestPermissions()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Preferences.setFirstRun(false,getApplication());
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_PERMISSION_CODE);
                }
            }
        }

    }
}

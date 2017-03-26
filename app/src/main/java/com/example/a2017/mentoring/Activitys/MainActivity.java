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
import android.view.View;
import android.widget.TextView;

import com.example.a2017.mentoring.Fragments.MeetingFragment;
import com.example.a2017.mentoring.Fragments.MenteeListFragment;
import com.example.a2017.mentoring.Fragments.MenteeProfileFragment;
import com.example.a2017.mentoring.Fragments.MentorProfileFragment;
import com.example.a2017.mentoring.Fragments.RequestFragment;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.BaseUrl;
import com.example.a2017.mentoring.Utils.Preferences;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

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
    private int userid;
    private Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userid= Preferences.myId(this);
        Gson gson = new Gson();
        register = gson.fromJson(Preferences.RegisterObject(this),Register.class);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        isProfileUpdate = Preferences.isProfileUpdate(this);
        isMentee = Preferences.isMentee(this);
        actionBarDrawerToggle();
        configureRequestPermissions();
        whichFragmentToShow();
        backStackFragment();
        setNavigationViewImageAndText();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

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
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        switch (id)
        {
            case R.id.Request:
                if(isMentee)
                {
                    goToMeetingRequest(0);
                }
                else
                {
                    goToMenteeList(2);
                }
                break;
            case R.id.main_page :
                if(isMentee)
                {
                    goToMeettingFragment();
                }
                else
                {
                    goToMenteeList(1);
                }
                break;
            case R.id.logout:
                Intent intent = new Intent(this,WelcomeActivity.class);
                startActivity(intent);
                this.finish();
                Preferences.setLogin(false,this);
                break;
            case R.id.menteeprofile :
                if(isMentee)
                {
                    goToMenteeProfile();
                }
                else
                {
                    goToMenteeList(0);
                }
                break;
            case R.id.mentorprofile :
                goToMentorProfile();
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

    private void goToMeettingFragment()
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
        MentorProfileFragment mentorProfile = new MentorProfileFragment();
        transaction.replace(R.id.fragment_container, mentorProfile,"MENTOR_PROFILE");
        transaction.commit();
    }

    private void goToMenteeList(int flag)
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MenteeListFragment menteeListFragment = new MenteeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag",flag);
        menteeListFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, menteeListFragment,"MENTEE_PROFILE");
        transaction.commit();
    }

    private void goToMeetingRequest(int flag)
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        Bundle bundle= new Bundle();
        bundle.putInt("flag",flag);
        RequestFragment requestFragment = new RequestFragment();
        requestFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, requestFragment,"REQUEST_MEETING");
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

    private void whichFragmentToShow()
    {
        if(isMentee)
        {
            if(isProfileUpdate)
            {
                goToMeettingFragment();
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
                goToMenteeList(1);
            }
            else
            {
                goToMentorProfile();
            }
        }
    }


    private void backStackFragment()
    {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            onBackPressed();
                        }
                    });
                }
                else
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    actionBarDrawerToggle();
                }
            }
        });
    }

    private  void setNavigationViewImageAndText()
    {
        View view =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)view.findViewById(R.id.nav_email);
        SimpleDraweeView myImage = (SimpleDraweeView) view.findViewById(R.id.contactImage);
        myImage.setImageURI(BaseUrl.MENTORING_JPG+userid);
        nav_user.setText(register.getEmail());
    }


}

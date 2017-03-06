package com.example.a2017.mentoring.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2017.mentoring.Model.MentorProfile;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Services.MentorProfileService;
import com.example.a2017.mentoring.Utils.Preferences;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2017 on 13/02/2017.
 */

public class MentorProfileFragment extends Fragment
{
    private static final int SELECT_PICTURE = 100;
    private boolean isFirstRun = false;
    private boolean isMentee ;
    private boolean checkServiceRunning = false;
    private SimpleDraweeView myImage;
    private CoordinatorLayout coordinatorLayout;
    private Button  mentorUpdateProfile;
    private String imageUriString = null ;
    private boolean isProfileUpdate ;
    private Register register;
    private int userid;
    private Spinner gender; // graduation_status;
    private EditText fname , lname  ,phone ,email ,mentor ,major,address,company,notes;
    private String _fname , _lname ,_gender ,_phone ,_email ,_major ,_address,_notes,_company ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isFirstRun = Preferences.isFirstRun(getActivity());
        isProfileUpdate = Preferences.isProfileUpdate(getContext());
        isMentee = Preferences.isMentee(getContext());
        userid= Preferences.myId(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mentor_profile,container,false);
        initialize(view);
        setMyimageOnClick();
        setMentorUpdateProfileOnClick();
        whatToDo();
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MentorProfileService.ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,intentFilter);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            boolean isDone = intent.getExtras().getBoolean("done");
            boolean isRunning = intent.getExtras().getBoolean("running");
            if(isRunning)
            {
            }
            if(isDone)
            {
            }
            else
            {
            }
        }
    };


    private void setMyimageOnClick()
    {
        myImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!isPermissionsDenied())
                {
                    openImageChooser();
                }
            }
        });
    }


    private void openImageChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        // intent.setType("image/jpg");
        startActivityForResult(intent , SELECT_PICTURE);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode==SELECT_PICTURE)
            {
                Uri imageUri = data.getData();
                imageUriString=imageUri.toString();
                myImage.setImageURI(imageUri);
            }
        }

    }



    private void fireMentorProfileService( MentorProfile mentorProfile )
    {
        Intent intent = new Intent(getContext(), MentorProfileService.class);
        intent.putExtra("mentorObject",mentorProfile);
        intent.putExtra("imageUri",imageUriString);
        getContext().startService(intent);
    }

    private boolean isPermissionsDenied()
    {
        boolean denied = false;
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(isFirstRun==false)
            {
                Toast.makeText(getContext(), "go to setting and allow the contact_list permission", Toast.LENGTH_SHORT).show();
                Log.d("firstRun", "false");
            }

            denied = true;
        }
        return denied;
    }

    private void getTextFromEditText()
    {
        _fname = fname.getText().toString();
        _lname = lname.getText().toString();
        _email = email.getText().toString();
        _address = address.getText().toString();
        _phone = phone.getText().toString();
        _major = major.getText().toString();
        _company = company.getText().toString();
        _notes = notes.getText().toString();
        if(gender.getSelectedItemPosition() == 0)
        {
            _gender ="male";
        }
        else
        {
            _gender = "female";
        }

    }

    private void getDataFromServer()
    {

    }

    private void initialize(View view)
    {
        myImage = (SimpleDraweeView) view.findViewById(R.id.myImageView);
        mentorUpdateProfile=(Button)view.findViewById(R.id.mentorUpdateProfile);
        fname = (EditText) view.findViewById(R.id.fname);
        lname = (EditText) view.findViewById(R.id.lname);
        phone = (EditText) view.findViewById(R.id.phone);
        email = (EditText) view.findViewById(R.id.email);
        major = (EditText) view.findViewById(R.id.major);
        address = (EditText) view.findViewById(R.id.address);
        gender = (Spinner) view.findViewById(R.id.gender);
        company = (EditText) view.findViewById(R.id.company);
        notes = (EditText) view.findViewById(R.id.notes);

    }

    private void disableMentorEditing(){
        myImage.setEnabled(false);
        myImage.setClickable(false);
        mentorUpdateProfile.setEnabled(false);
        mentorUpdateProfile.setClickable(false);
        fname.setEnabled(false);
        lname.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        mentor.setEnabled(false);
        major.setEnabled(false);
        address.setEnabled(false);
        gender.setEnabled(false);
        company.setEnabled(false);
        notes.setEnabled(false);
    }

    private void setMentorUpdateProfileOnClick()
    {
        mentorUpdateProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getTextFromEditText();
                MentorProfile mentorProfile = new MentorProfile(userid,_fname,_lname,_gender,_phone,_email,_major,_major,_address,_company,_notes,null);
                fireMentorProfileService(mentorProfile);
            }
        });
    }

    private void getRegister()
    {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<Register> registerCall = retrofit.getRegister(userid);
        registerCall.enqueue(new Callback<Register>()
        {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response)
            {
                if(response.code() == 200 || response.code() ==204)
                {
                    Register register = response.body();
                    updateUifromRegisterObject(register);
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t)
            {

            }
        });
    }

    private void getRegisterObject()
    {
        Gson gson = new Gson();
        String registerJson = Preferences.RegisterObject(getContext());
        register = gson.fromJson(registerJson,Register.class);
        if(register!=null)
        {
            updateUifromRegisterObject(register);
        }
        else
        {
            getRegister();
        }
    }

    private void updateUifromRegisterObject(Register register)
    {
        email.setText(register.getEmail());
        fname.setText(register.getFirstName());
        lname.setText(register.getLastName());
        phone.setText(register.getPhone());
    }

    private void whatToDo()
    {
        if(!isMentee)
        {
            if(isProfileUpdate)
            {
                getDataFromServer();
            }
            else
            {
                getRegisterObject();
            }
        }
        else
        {
            getDataFromServer();
        }
    }
}


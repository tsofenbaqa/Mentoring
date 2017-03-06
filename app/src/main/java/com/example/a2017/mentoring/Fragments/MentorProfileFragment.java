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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.a2017.mentoring.Model.MenteeProfile;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Services.MenteeProfileService;
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
    private Button  menteeUpdateProfile;
    private String imageUriString = null ;
    private boolean isProfileUpdate ;
    private Register register;
    private int userid;
    private Spinner gender; // graduation_status;
    private EditText fname , lname  ,phone ,email ,mentor ,major,address;
    private String _fname , _lname ,_gender ,_phone ,_email ,_major ,_address ;

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
        whatToDo();
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MenteeProfileService.ACTION);
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



    private void fireMentorProfileService( MenteeProfile menteeProfile )
    {
        Intent intent = new Intent(getContext(), MenteeProfileService.class);
        intent.putExtra("mentorObject",menteeProfile);
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
    }

    private void getDataFromServer()
    {

    }

    private void initialize(View view)
    {
        myImage = (SimpleDraweeView) view.findViewById(R.id.myImageView);
        menteeUpdateProfile=(Button)view.findViewById(R.id.menteeUpdateProfile);
        fname = (EditText) view.findViewById(R.id.fname);
        lname = (EditText) view.findViewById(R.id.lname);
        phone = (EditText) view.findViewById(R.id.phone);
        email = (EditText) view.findViewById(R.id.email);
        mentor = (EditText) view.findViewById(R.id.mentor);
        major = (EditText) view.findViewById(R.id.major);
        address = (EditText) view.findViewById(R.id.address);
        gender = (Spinner) view.findViewById(R.id.gender);

    }

    private void disableMentorEditing(){
        myImage.setEnabled(false);
        myImage.setClickable(false);
        menteeUpdateProfile.setEnabled(false);
        menteeUpdateProfile.setClickable(false);
        fname.setEnabled(false);
        lname.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        mentor.setEnabled(false);
        major.setEnabled(false);
        address.setEnabled(false);
        gender.setEnabled(false);
    }

    private void setmenteeUpdateProfileOnClick()
    {
        menteeUpdateProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getTextFromEditText();
                MenteeProfile menteeProfile = new MenteeProfile(userid,_fname,_lname,_gender,_phone,_email,_major,Integer.parseInt(_semster),_graduation_status,_address,_notes,Integer.parseInt(_courseid),_institution,null,null,null,Integer.parseInt(_average));
                fireMentorProfileService(menteeProfile);
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
        if(isMentee)
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


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
import android.widget.TextView;
import android.widget.Toast;
import com.example.a2017.mentoring.Model.MenteeProfile;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.Services.MenteeProfileService;
import com.example.a2017.mentoring.Utils.Preferences;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;

/**
 * Created by 2017 on 13/02/2017.
 */

public class MenteeProfileFragment extends Fragment
{
    private static final int SELECT_PICTURE = 100;
    private static final int SELECT_RESUME = 102;
    private static final int SELECT_GRADE_SHEET = 103;
    private boolean isFirstRun = false;
    private boolean checkServiceRunning = false;
    private SimpleDraweeView myImage;
    private CoordinatorLayout coordinatorLayout;
    private Button  menteeUpdateProfile;
    private String imageUriString = null ;
    private String resumeUriString = null ;
    private String gradeSheetUriString = null;
    private boolean isProfileUpdate ;
    private boolean isMentee ;
    private Register register;
    private TextView chooseResume , resume_review , chooseGradeSheet , gradeSheet_review;
    private Spinner gender, graduation_status;
    private EditText fname , lname ,id ,phone ,email ,major ,semster ,average ,address ,notes ,courseid ,datestart ,institution ;
    private String _fname , _lname ,_id ,_gender ,_phone ,_email  ,_major ,_semster ,_average ,_address ,_notes ,_courseid ,_datestart , _graduation_status ,_institution;
    private int userid;
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
        View view = inflater.inflate(R.layout.fragment_update_mentee_profile,container,false);
        initialize(view);
        setMyimageOnClick();
        setChooseResumeOnClick();
        setChooseGradeSheetOnClick();
        setmenteeUpdateProfileOnClick();
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MenteeProfileService.ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,intentFilter);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(isMentee)
        {
            if(isProfileUpdate)
            {
                getDataFromServer();
            }
            else
            {
                Gson gson = new Gson();
                String registerJson = Preferences.RegisterObject(getContext());
                register = gson.fromJson(registerJson,Register.class);
            }
        }
        else
        {
            getDataFromServer();
        }

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

   private void setmenteeUpdateProfileOnClick()
    {
        menteeUpdateProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getTextFromEditText();
                getType();
                getGraduation_status();
                MenteeProfile menteeProfile = new MenteeProfile(userid,_fname,_lname,Long.parseLong(_id),_gender,_phone,_email,_major,Integer.parseInt(_semster),_graduation_status,_address,_notes,Integer.parseInt(_courseid),_institution,_datestart,null,null,null);
                fireMenteeProfileService(menteeProfile);
            }
        });
    }

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

    private void setChooseResumeOnClick()
    {
        chooseResume.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!isPermissionsDenied())
                {
                    resumeChooser();
                }
            }
        });
    }

    private void setChooseGradeSheetOnClick()
    {
        chooseGradeSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPermissionsDenied())
                {
                    gradeSheetChooser();
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

    private void resumeChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        //intent.setType("application/msword , application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        startActivityForResult(intent , SELECT_RESUME);
    }

    private void gradeSheetChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("application/pdf");
        intent.setType("*/*");
        startActivityForResult(intent , SELECT_GRADE_SHEET);
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
            else if(requestCode==SELECT_RESUME)
            {
                resumeUriString = data.getData().toString();
            }
            else
            {
                gradeSheetUriString = data.getData().toString();
            }
        }

    }



    private void fireMenteeProfileService( MenteeProfile menteeProfile )
    {
        Intent intent = new Intent(getContext(), MenteeProfileService.class);
        intent.putExtra("menteeObject",menteeProfile);
        intent.putExtra("imageUri",imageUriString);
        intent.putExtra("resumeUri",resumeUriString);
        intent.putExtra("gradeSheetUri",gradeSheetUriString);
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
        _id = id.getText().toString();
        _phone = phone.getText().toString();
        _major = major.getText().toString();
        _semster = semster.getText().toString();
        _average = average.getText().toString();
        _notes = notes.getText().toString();
        _courseid = courseid.getText().toString();
        _datestart = datestart.getText().toString();
        _institution = institution.getText().toString();
    }
    private void getType()
    {
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _gender = gender.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getGraduation_status()
    {
        graduation_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                _graduation_status = graduation_status.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
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
        id = (EditText) view.findViewById(R.id.id);
        phone = (EditText) view.findViewById(R.id.phone);
        email = (EditText) view.findViewById(R.id.email);
        major = (EditText) view.findViewById(R.id.major);
        semster = (EditText) view.findViewById(R.id.semster);
        average = (EditText) view.findViewById(R.id.average);
        address = (EditText) view.findViewById(R.id.address);
        notes = (EditText) view.findViewById(R.id.address);
        courseid = (EditText) view.findViewById(R.id.courseid);
        datestart = (EditText) view.findViewById(R.id.datestart);
        institution = (EditText) view.findViewById(R.id.institution);
        graduation_status = (Spinner) view.findViewById(R.id.graduation_status);
        chooseResume = (TextView) view.findViewById(R.id.resume);
        resume_review = (TextView) view.findViewById(R.id.resume_review);
        chooseGradeSheet = (TextView) view.findViewById(R.id.gradeSheet);
        gradeSheet_review = (TextView) view.findViewById(R.id.gradeSheet_review);
        gender = (Spinner) view.findViewById(R.id.gender);

    }
}


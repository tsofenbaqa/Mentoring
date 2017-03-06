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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2017.mentoring.Model.MenteeProfile;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.RetrofitApi.BaseUrl;
import com.example.a2017.mentoring.Services.MenteeProfileService;
import com.example.a2017.mentoring.Utils.Preferences;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private EditText fname , lname ,phone ,email ,major ,semster ,average ,address ,notes ,courseid ,institution ;
    private String _fname , _lname ,_gender ,_phone ,_email  ,_major ,_semster ,_average ,_address ,_notes ,_courseid , _graduation_status ,_institution;
    private int userid;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
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
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        initialize(view);
        setMyimageOnClick();
        setChooseResumeOnClick();
        setChooseGradeSheetOnClick();
        setmenteeUpdateProfileOnClick();
        gradeSheetOnclick();
        resumeOnclick();
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

   private void setmenteeUpdateProfileOnClick()
    {
        menteeUpdateProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getTextFromEditText();
                MenteeProfile menteeProfile = new MenteeProfile(userid,_fname,_lname,_gender,_phone,_email,_major,Integer.parseInt(_semster),_graduation_status,_address,_notes,Integer.parseInt(_courseid),_institution,null,null,null,Integer.parseInt(_average));
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
            File file = new File(data.getData().toString());
            if(requestCode==SELECT_PICTURE)
            {
                Uri imageUri = data.getData();
                imageUriString=imageUri.toString();
                myImage.setImageURI(imageUri);
            }
            else if(requestCode==SELECT_RESUME)
            {
                resumeUriString = data.getData().toString();
                resume_review.setText(file.getName());
            }
            else
            {
                gradeSheetUriString = data.getData().toString();
                gradeSheet_review.setText(file.getName());
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
        _phone = phone.getText().toString();
        _major = major.getText().toString();
        _semster = semster.getText().toString();
        _average = average.getText().toString();
        _notes = notes.getText().toString();
        _courseid = courseid.getText().toString();
        _institution = institution.getText().toString();
        if(gender.getSelectedItemPosition() == 0)
        {
            _gender ="male";
        }
        else
        {
            _gender = "female";
        }

        if(graduation_status.getSelectedItemPosition() == 0 )
        {
            _graduation_status = "student";

        }
        else
        {
            _graduation_status = "graduated";
        }
    }
    private void getDataFromServer()
    {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<MenteeProfile> menteeProfileCall = retrofit.getMenteeProfile(userid);
        menteeProfileCall.enqueue(new Callback<MenteeProfile>()
        {
            @Override
            public void onResponse(Call<MenteeProfile> call, Response<MenteeProfile> response)
            {
                if(response.code() == 200 || response.code() == 204)
                {
                    MenteeProfile menteeProfile = response.body();
                    fname.setText(menteeProfile.getFname());
                    lname.setText(menteeProfile.getLname());
                    phone.setText(menteeProfile.getPhone());
                    email.setText(menteeProfile.getEmail());
                    major.setText(menteeProfile.getMajor());
                    semster.setText(String.valueOf(menteeProfile.getSemesterLeft()));
                    address.setText(menteeProfile.getAddress());
                    average.setText(String.valueOf(menteeProfile.getAvg()));
                    notes.setText(menteeProfile.getNote());
                    institution.setText(menteeProfile.getAcadimicinstitution());
                    courseid.setText(String.valueOf(menteeProfile.getTsofenCourse()));
                    resume_review.setText(menteeProfile.getFname() + "-resume");
                    gradeSheet_review.setText(menteeProfile.getFname() + "-resume");
                    myImage.setImageURI(BaseUrl.MENTORING_JPG+userid);
                    if(menteeProfile.getGender().equals("male"))
                    {
                        gender.setSelection(0);
                    }
                    else
                    {
                        gender.setSelection(1);
                    }
                    if(menteeProfile.getEducationStatus().equals("student"))
                    {
                        graduation_status.setSelection(0);
                    }
                    else
                    {
                        graduation_status.setSelection(1);
                    }

                }

            }

            @Override
            public void onFailure(Call<MenteeProfile> call, Throwable t)
            {

            }
        });
    }

    private void initialize(View view)
    {
        myImage = (SimpleDraweeView) view.findViewById(R.id.myImageView);
        menteeUpdateProfile=(Button)view.findViewById(R.id.menteeUpdateProfile);
        fname = (EditText) view.findViewById(R.id.fname);
        lname = (EditText) view.findViewById(R.id.lname);
        phone = (EditText) view.findViewById(R.id.phone);
        email = (EditText) view.findViewById(R.id.email);
        major = (EditText) view.findViewById(R.id.major);
        semster = (EditText) view.findViewById(R.id.semster);
        average = (EditText) view.findViewById(R.id.average);
        address = (EditText) view.findViewById(R.id.address);
        notes = (EditText) view.findViewById(R.id.address);
        courseid = (EditText) view.findViewById(R.id.courseid);
        institution = (EditText) view.findViewById(R.id.institution);
        graduation_status = (Spinner) view.findViewById(R.id.graduation_status);
        chooseResume = (TextView) view.findViewById(R.id.resume);
        resume_review = (TextView) view.findViewById(R.id.resume_review);
        chooseGradeSheet = (TextView) view.findViewById(R.id.gradeSheet);
        gradeSheet_review = (TextView) view.findViewById(R.id.gradeSheet_review);
        gender = (Spinner) view.findViewById(R.id.gender);
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

    private void gradeSheetOnclick()
    {
        gradeSheet_review.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToFileViwer(setArgumntGradeSheet());
            }
        });
    }

    private void resumeOnclick()
    {
        resume_review.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToFileViwer(setArgumntResume());
            }
        });
    }

    private void goToFileViwer(Bundle bundle)
    {
        FileViewerFragment fileViewerFragment = new FileViewerFragment();
        transaction.addToBackStack(null);
        fileViewerFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, fileViewerFragment,"FILE_VIEWER_FRAGMENT");
        transaction.commit();
    }

    private Bundle setArgumntGradeSheet()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isResume",false);
        bundle.putInt("userid",userid);
        return bundle;
    }

    private Bundle setArgumntResume()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isResume",true);
        bundle.putInt("userid",userid);
        return bundle;
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

    private void updateUifromRegisterObject(Register register)
    {
        email.setText(register.getEmail());
        fname.setText(register.getFirstName());
        lname.setText(register.getLastName());
        phone.setText(register.getPhone());
    }

    private void disableMenteeEditing(){
        myImage.setClickable(false);
        myImage.setEnabled(false);
        menteeUpdateProfile.setClickable(false);
        menteeUpdateProfile.setEnabled(false);
        fname.setEnabled(false);
        lname.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        major.setEnabled(false);
        semster.setEnabled(false);
        average.setEnabled(false);
        address.setEnabled(false);
        notes.setEnabled(false);
        courseid.setEnabled(false);
        institution.setEnabled(false);
        graduation_status.setEnabled(false);
        graduation_status.setClickable(false);
        chooseResume.setEnabled(false);
        resume_review.setEnabled(false);
        chooseGradeSheet.setEnabled(false);
        gradeSheet_review.setEnabled(false);
        gender.setEnabled(false);
        gender.setClickable(false);
    }
}


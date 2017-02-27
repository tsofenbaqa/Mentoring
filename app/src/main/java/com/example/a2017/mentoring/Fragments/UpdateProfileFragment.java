package com.example.a2017.mentoring.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;
import com.example.a2017.mentoring.Model.MenteeProfile;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.Services.MenteeProfileService;
import com.example.a2017.mentoring.Utils.Preferences;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jorgecastilloprz.FABProgressCircle;

/**
 * Created by 2017 on 13/02/2017.
 */

public class UpdateProfileFragment extends Fragment
{
    private static final int SELECT_PICTURE = 100;
    private static final int SELECT_RESUME = 102;
    private static final int SELECT_GRADE_SHEET = 103;
    private boolean isFirstRun = false;
    private boolean checkServiceRunning = false;
    private SimpleDraweeView myImage;
    private CoordinatorLayout coordinatorLayout;
    private FABProgressCircle fabProgressCircle;
    private Button chooseResume, chooseGradeSheet, menteeUpdateProfile;
    private String imageUriString = null ;
    private String resumeUriString = null ;
    private String gradeSheetUriString = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isFirstRun = Preferences.isFirstRun(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_update_profile,container,false);
       // coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.cor);
        myImage = (SimpleDraweeView) view.findViewById(R.id.myImageView);
        chooseResume=(Button)view.findViewById(R.id.upcv_btn);
        chooseGradeSheet=(Button)view.findViewById(R.id.gradeSheetBtn);
        menteeUpdateProfile=(Button)view.findViewById(R.id.menteeUpdateProfile);
        fabProgressCircle = (FABProgressCircle) view.findViewById(R.id.fabProgressCircle);
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

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            boolean isDone = intent.getExtras().getBoolean("done");
            boolean isRunning = intent.getExtras().getBoolean("running");
            if(isRunning)
            {
                fabProgressCircle.show();
            }
            if(isDone)
            {
                fabProgressCircle.beginFinalAnimation();
            }
            else
            {
                fabProgressCircle.hide();
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
                MenteeProfile menteeProfile = new MenteeProfile(1,"S","S",1,"S","S","S",50,"S",50,"S","S","S",50,"S","S",null,null,null);
                fireMenteeProfileService(menteeProfile);
                fabProgressCircle.show();
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
                imageUriString=data.getData().toString();
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

    }
}


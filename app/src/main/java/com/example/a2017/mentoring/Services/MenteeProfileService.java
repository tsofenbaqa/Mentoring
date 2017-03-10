package com.example.a2017.mentoring.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.a2017.mentoring.Activitys.MainActivity;
import com.example.a2017.mentoring.Model.MenteeProfile;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2017 on 26/02/2017.
 */

public class MenteeProfileService extends IntentService
{
    public static final String ACTION ="com.example.a2017.mentoring.Services.MenteeProfileService";
    private String imageUriString;
    private String resumeUriString;
    private String gradeSheetUriString;
    private Uri imageUri = null;
    private Uri resumeUri = null;
    private Uri gradeSheetUri = null;
    private MenteeProfile menteeObject;
    private boolean isRunning = false;
    private boolean checkRunning = false;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public MenteeProfileService()
    {
        super("MenteeProfileService");
    }

    @Override
    public void onDestroy()
    {
        isRunning = false ;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        checkRunning = intent.getExtras().getBoolean("check");
        if (checkRunning)
        {
            Intent intentValue = new Intent(ACTION);

            if (!isRunning)
            {
                intentValue.putExtra("running", false);
            }
            else
            {
                intentValue.putExtra("running", true);
            }
            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intentValue);
            return;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d("onHandleIntent:","accessed");
        try
        {
            imageUriString = intent.getExtras().getString("imageUri");
            resumeUriString = intent.getExtras().getString("resumeUri");
            gradeSheetUriString = intent.getExtras().getString("gradeSheetUri");
            menteeObject = (MenteeProfile) intent.getExtras().getSerializable("menteeObject");
            compressFiles();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void compressFiles() throws IOException
    {
        sendFullNotification(true,getResources().getString(R.string.notify_upload));
        Log.d( "compressFiles: ","accessed");
        isRunning = true;
        byte [] imageByte , resumeByte , gradeSheetByte;
        if(imageUriString!=null) {
            imageUri = Uri.parse(imageUriString);
            // get files stream
            InputStream imageInputStream = getContentResolver().openInputStream(imageUri);
            // convert stream to byte
            imageByte = IOUtils.toByteArray(imageInputStream);
            // set zip and output stream for image
            ByteArrayOutputStream imageZipArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream imagezipOutputStream = new ZipOutputStream(imageZipArrayOutputStream);
            // set files name in zip file container
            ZipEntry imageEntry = new ZipEntry(menteeObject.getUserId() + ".jpg");
            // start compress image
            imagezipOutputStream.putNextEntry(imageEntry);
            imagezipOutputStream.write(imageByte);
            imagezipOutputStream.closeEntry();
            imagezipOutputStream.close();
            // add files to menteeObject
            menteeObject.setImageFile(imageZipArrayOutputStream.toByteArray());
        }
        if(resumeUriString!=null)
        {
            resumeUri = Uri.parse(resumeUriString);
            // get files stream
            InputStream resumeInputStream = getContentResolver().openInputStream(resumeUri);
            // convert stream to byte
            resumeByte = IOUtils.toByteArray(resumeInputStream);
            // set zip and output stream for resume
            ByteArrayOutputStream resumeZipArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream resumeZipOutputStream = new ZipOutputStream(resumeZipArrayOutputStream);
            // set files name in zip file container
            ZipEntry resumeEntry = new ZipEntry(menteeObject.getUserId() + ".docx");
            // start compress resume
            resumeZipOutputStream.putNextEntry(resumeEntry);
            resumeZipOutputStream.write(resumeByte);
            resumeZipOutputStream.closeEntry();
            resumeZipOutputStream.close();
            // add files to menteeObject
            menteeObject.setResumeFile(resumeZipArrayOutputStream.toByteArray());
        }
        if(gradeSheetUriString!=null)
        {
            gradeSheetUri = Uri.parse(gradeSheetUriString);
            // get files stream
            InputStream gradeSheetInputStream = getContentResolver().openInputStream(gradeSheetUri);
            // convert stream to byte
            gradeSheetByte = IOUtils.toByteArray(gradeSheetInputStream);
            // set zip and output stream for gradeSheet
            ByteArrayOutputStream gradeSheetZipArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream gradeSheetZipOutputStream = new ZipOutputStream(gradeSheetZipArrayOutputStream);
            // set files name in zip file container
            ZipEntry gradeSheetEntry = new ZipEntry(menteeObject.getUserId() + ".pdf");
            // start compress gradSheet
            gradeSheetZipOutputStream.putNextEntry(gradeSheetEntry);
            gradeSheetZipOutputStream.write(gradeSheetByte);
            gradeSheetZipOutputStream.closeEntry();
            gradeSheetZipOutputStream.close();
            // add files to menteeObject
            menteeObject.setGradeSheetFile(gradeSheetZipArrayOutputStream.toByteArray());
        }
        // for testing show object in log before send to server
        Gson gson = new Gson();
        Log.d("compressFiles: " ,gson.toJson(menteeObject));
        //send object to server
        updateMenteeProfile();
    }

    private void updateMenteeProfile()
    {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<Void> updateMenteeProfile = retrofit.updateMenteeProfile(menteeObject);
        updateMenteeProfile.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if(response.code()==200 || response.code()==204)
                {
                    Log.d("onResponse: " ,"done");
                    fireIntentToFragment(true);
                    sendFullNotification(false,getResources().getString(R.string.notify_completed));
                    Preferences.setProfileUpdate(true,getBaseContext());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                Log.d("onFailure: " ,"failed");
                sendFullNotification(false,getResources().getString(R.string.notify_failed));
                fireIntentToFragment(false);
            }
        });
    }

    private void fireIntentToFragment(boolean isdone)
    {
        Intent intentValue = new Intent(ACTION);
        intentValue.putExtra("done",isdone);
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intentValue);
    }

    private void sendFullNotification(boolean isStart , String ststus)
    {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Tsofen")
                .setContentText(ststus)
                .setProgress(0,0,isStart);
        Intent intentNotificatio = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intentNotificatio,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification.build());
    }
}

package com.example.a2017.mentoring.Services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.example.a2017.mentoring.Activitys.MainActivity;
import com.example.a2017.mentoring.Model.MenteeProfile;
import com.example.a2017.mentoring.Model.MentorProfile;
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
 * Created by 2017 on 06/03/2017.
 */

public class MentorProfileService extends IntentService
{
    public static final String ACTION ="com.example.a2017.mentoring.Services.MentorProfileService";
    private String imageUriString;
    private Uri imageUri = null;
    private boolean isRunning = false;
    private boolean checkRunning = false;
    private MentorProfile mentorObject;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public MentorProfileService()
    {
        super("MentorProfileService");
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
        try
        {
            imageUriString = intent.getExtras().getString("imageUri");
            mentorObject = (MentorProfile) intent.getExtras().getSerializable("mentorObject");
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
        byte [] imageByte ;
        if(imageUriString!=null)
        {
            imageUri = Uri.parse(imageUriString);
            // get files stream
            InputStream imageInputStream = getContentResolver().openInputStream(imageUri);
            // convert stream to byte
            imageByte = IOUtils.toByteArray(imageInputStream);
            // set zip and output stream for image
            ByteArrayOutputStream imageZipArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream imagezipOutputStream = new ZipOutputStream(imageZipArrayOutputStream);
            // set files name in zip file container
            ZipEntry imageEntry = new ZipEntry(mentorObject.getUserId() + ".jpg");
            // start compress image
            imagezipOutputStream.putNextEntry(imageEntry);
            imagezipOutputStream.write(imageByte);
            imagezipOutputStream.closeEntry();
            imagezipOutputStream.close();
            // add files to mentorObject
            mentorObject.setImageFile(imageZipArrayOutputStream.toByteArray());
        }
        // for testing show object in log before send to server
        Gson gson = new Gson();
        Log.d("compressFiles: " ,gson.toJson(mentorObject));
        //send object to server
        updateMenteeProfile();
    }

    private void updateMenteeProfile()
    {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<Void> updateMenteeProfile = retrofit.updateMentorProfile(mentorObject);
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

package com.example.a2017.mentoring.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.a2017.mentoring.Activitys.MainActivity;
import com.example.a2017.mentoring.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by 2017 on 13/03/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        sendFullNotification();
    }

    private void sendFullNotification()
    {
        String appName = "Tsofen Mentoring";
        String message = "בקשת פגישה חדשה";
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(appName)
                .setSound(notificationSound)
                .setContentText(message);
        Intent intentNotificatio = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intentNotificatio,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification.build());
    }
}

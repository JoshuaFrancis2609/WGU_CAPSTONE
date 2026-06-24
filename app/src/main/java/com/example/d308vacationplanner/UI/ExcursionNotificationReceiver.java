package com.example.d308vacationplanner.UI;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.core.app.NotificationCompat;

import com.example.d308vacationplanner.R;

public class ExcursionNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String excursionTitle = intent.getStringExtra("excursionTitle");

        //Notifications
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "excursionChannel")
                .setSmallIcon(R.drawable.baseline_access_alarm_24)
                .setContentTitle("Excursion Notification")
                .setContentText("Excursion: " + excursionTitle + " is happening today!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(MainActivity.notificationID++, builder.build());
        }
    }
}
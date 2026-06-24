package com.example.d308vacationplanner.UI;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.d308vacationplanner.R;

public class VacationNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String vacationTitle = intent.getStringExtra("vacationTitle");
        String status = intent.getStringExtra("status");

        //Show notification when it is triggered
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "vacationChannel")
                .setSmallIcon(R.drawable.baseline_access_alarm_24)
                .setContentTitle("Vacation Notification")
                .setContentText(vacationTitle + " is " + status + ".")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager != null) {
            notificationManager.notify(MainActivity.notificationID++, builder.build());
        }
    }
}

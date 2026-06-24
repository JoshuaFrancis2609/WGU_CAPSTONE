package com.example.d308vacationplanner.UI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;
    public static int notificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Create a notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel vacationChannel = new NotificationChannel(
                    "vacationChannel",
                    "Vacation Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel excursionChannel = new NotificationChannel(
                    "excursionChannel",
                    "Excursion Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(vacationChannel);
                manager.createNotificationChannel(excursionChannel);
            }
        }

        Button enterButton = findViewById(R.id.enterButton);

        // Enter button to navigate to VactionList
        enterButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VacationList.class);
                intent.putExtra("test","Sending info");
                startActivity(intent);
            }
        });

    }
}
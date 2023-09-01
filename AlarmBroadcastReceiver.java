package com.example.alarmclockdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String TITLE = "TITLE";
    public static Ringtone ringtoneSound;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            String toastText = String.format("Alarm Reboot");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            //startAlarmService(context, intent);
            startRescheduleAlarmsService(context);
        }
        else {
            String toastText = String.format("Alarm Received");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            startAlarmService(context, intent);
            }
        }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE));
        context.startService(intentService);
    }

    private void startRescheduleAlarmsService(Context context) {
        //Intent intentService = new Intent(context, AlarmService.class);
        Intent intentService = new Intent(context, RescheduleAlarmService.class);
        context.startService(intentService);
    }
}

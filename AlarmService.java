package com.example.alarmclockdb;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
public class AlarmService extends Service{
    Ringtone ringtone;
    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        Uri alarmUri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = android.media.RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ringtone.stop();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

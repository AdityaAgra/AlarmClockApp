package com.example.alarmclockdb;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import com.example.alarmclockdb.alarmdata.AlarmDatabase;
import com.example.alarmclockdb.alarmdata.AlarmEntity;

import java.util.List;

public class RescheduleAlarmService extends LifecycleService {
    List<AlarmEntity>alarmEntityList;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        getAllAlarm();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    public void getAllAlarm() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                alarmEntityList = AlarmDatabase.getDatabase(getApplicationContext())
                        .alarmDao()
                        .getAlarms();
                for (AlarmEntity a : alarmEntityList) {
                    if (a.isStarted()) {
                        a.schedule(getApplicationContext());
                    }
                }
            }
        });
        thread.start();
    }
}

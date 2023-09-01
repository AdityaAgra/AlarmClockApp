package com.example.alarmclockdb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import java.util.List;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.util.Base64;
import com.example.alarmclockdb.alarmdata.AlarmDatabase;
import com.google.gson.Gson;

import com.example.alarmclockdb.alarmdata.AlarmEntity;

import org.json.JSONException;

import java.util.Map;
import java.util.Random;

public class MyJavascriptInterface {
    Context mContext;
    WebView webview;
    List<AlarmEntity>alarmEntityList;
    MyJavascriptInterface(Context c, WebView myWebView) {
        mContext = c;
        webview = myWebView;
    }

    @JavascriptInterface
    public void getAlarmFromWebview(String alarm) throws JSONException {
        Gson gson = new Gson();
        Map map = gson.fromJson(alarm, Map.class);
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        String title = (String) map.get("title");
        String time = (String) map.get("time");
        int hour = Integer.parseInt(time.substring(0,2));
        int minutes = Integer.parseInt(time.substring(3));
        AlarmEntity alarmEntity = new AlarmEntity(alarmId, hour, minutes, title, true, System.currentTimeMillis());
        alarmEntity.schedule(mContext);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(alarmEntity);
        getAllAlarm();
    }

    @JavascriptInterface
    public void clearAll() {
        deleteAllAlarm();
        showSetAlarm();
    }

    @JavascriptInterface
    public void dismissAlarm(){
        Intent intentService = new Intent(mContext, AlarmService.class);
        mContext.stopService(intentService);
    }

    @JavascriptInterface
    public void cancelSetAlarm(String alarmId) {
        AlarmEntity alarmEntity = AlarmDatabase.getDatabase(mContext.getApplicationContext())
                .alarmDao()
                .getAlarmById(Integer.parseInt(alarmId));
        alarmEntity.cancelAlarm(mContext);
        deleteOneAlarm(Integer.parseInt(alarmId));
        showSetAlarm();
    }

    private void showSetAlarm() {
        webview.post(new Runnable() {
            @Override
            public void run() {
                String response = alarmEntityList.toString();
                String base64Str = Base64.encodeToString(response.getBytes(), Base64.NO_WRAP);
                String finalData = '"' + base64Str + '"';
                StringBuilder sb = new StringBuilder();
                sb.append("javascript:");
                sb.append("receiveDataFromAndroid");
                sb.append("(");
                sb.append(finalData);
                sb.append(")");
                System.out.println(sb.toString());
                webview.evaluateJavascript(sb.toString(), null);
                Log.d("js", "JS evaluated");
            }
        });
    }

    public void getAllAlarm() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                alarmEntityList = AlarmDatabase.getDatabase(mContext.getApplicationContext())
                        .alarmDao()
                        .getAlarms();
                showSetAlarm();
            }
        });
        thread.start();
    }

    public void deleteAllAlarm() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmDatabase.getDatabase(mContext.getApplicationContext())
                        .alarmDao()
                        .deleteAll();
            }
        });
        thread.start();
    }

    public void deleteOneAlarm(int alarmId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmDatabase.getDatabase(mContext.getApplicationContext())
                        .alarmDao()
                        .deleteByAlarmID(alarmId);
            }
        });
        thread.start();
    }

    public void getOneAlarm(int alarmId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmDatabase.getDatabase(mContext.getApplicationContext())
                        .alarmDao()
                        .getAlarmById(alarmId);
            }
        });
        thread.start();
    }

    class InsertAsyncTask extends AsyncTask<AlarmEntity, Void, Void> {
        @Override
        protected Void doInBackground(AlarmEntity... alarmEntities) {

            AlarmDatabase.getDatabase(mContext.getApplicationContext())
                    .alarmDao()
                    .insert(alarmEntities[0]);
            return null;
        }
    }
}

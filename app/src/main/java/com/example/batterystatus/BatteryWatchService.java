package com.example.batterystatus;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class BatteryWatchService extends Service {
    static String TAG = "BatteryWatchService";
    private Timer timer;

    public BatteryWatchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                IntentFilter intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, intentfilter);

                if (batteryStatus!=null) {
                    int batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    Log.d(TAG, "Level: " + String.valueOf(batteryLevel));
                }
            }
        }, 0, 5000);

        return START_STICKY;
    }

    @Override public void onDestroy() {
        Log.d(TAG, "onDestroy");
        timer.cancel();

        super.onDestroy();
    }
}

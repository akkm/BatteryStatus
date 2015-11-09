package com.example.batterystatus;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;

public class BatteryWatchService extends Service {
    static String TAG = "BatteryWatchService";
    private static final int NOTIFICATION_ID = 1235;
    private Timer timer;
    private MyBatteryReceiver mReceiver;

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
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mReceiver = new MyBatteryReceiver();
        registerReceiver(mReceiver, intentFilter);

        return START_STICKY;
    }

    @Override public void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(mReceiver);

        // TODO 4 : 通知を全て消す

        super.onDestroy();
    }

    public void showNotification(int level, int status) {
        // TODO 3 : 現在のバッテリー残量を通知に常に表示させる
    }

    private class MyBatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            Log.d(TAG, "level:" + level);

            // BatteryManager.EXTRA_STATUSは、充電中なのか放電中なのかなどの情報が入っています。
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            // TODO 5 : call showNotification

        }
    }
}

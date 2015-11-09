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
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                IntentFilter intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//                Intent batteryStatus = registerReceiver(null, intentfilter);
//
//                if (batteryStatus!=null) {
//                    int batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//                    Log.d(TAG, "Level: " + String.valueOf(batteryLevel));
//                }
//            }
//        }, 0, 5000);

        // TODO 6 バッテリー変化の受信を開始する (ACTION_BATTERY_CHANGED)
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mReceiver = new MyBatteryReceiver();
        registerReceiver(mReceiver, intentFilter);

        return START_STICKY;
    }

    @Override public void onDestroy() {
        Log.d(TAG, "onDestroy");
//        timer.cancel();

        // TODO 7 受信を終了する
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }


    // TODO 5 バッテリーの状態が変化した時の動作を書くクラスMyBatteryReceiverを作成する
    private class MyBatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            Log.d(TAG, "level:" + level);
        }
    }
}

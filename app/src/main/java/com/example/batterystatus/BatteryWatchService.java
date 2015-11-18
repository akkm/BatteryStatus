package com.example.batterystatus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();

        super.onDestroy();
    }

    public void showNotification(int level, int status) {
        // TODO 3 : 現在のバッテリー残量を通知に常に表示させる
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.battery_number_icon, level);
        builder.setOngoing(true);
        builder.setContentTitle("残量: " + level + "%");

        String statusText;
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING :
                statusText = "充電中";
                break;
            case BatteryManager.BATTERY_STATUS_FULL :
                statusText = "満充電";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING :
                statusText = "放電中";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING :
                statusText = "未充電";
                break;
            default:
                statusText = "不明";
                break;
        }
        builder.setContentText(statusText);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private class MyBatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            Log.d(TAG, "level:" + level);

            // BatteryManager.EXTRA_STATUSは、充電中なのか放電中なのかなどの情報が入っています。
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            // TODO 5 : call showNotification
            showNotification(level, status);

        }
    }
}

package com.example.batterystatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by atsuto.yamada on 2015/11/09.
 */
public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO 8 BatteryWatchServiceを起動

        Intent serviceIntent = new Intent(context, BatteryWatchService.class);
        context.startService(serviceIntent);

    }
}

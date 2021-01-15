package com.example.lab5;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.sql.BatchUpdateException;

public class PowerConectionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent bateryStatus = context.registerReceiver(null,iFilter);

        int status = bateryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        String statusCharghing;
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        if(isCharging) {
            statusCharghing = "Charging";
        } else {
            statusCharghing = "Not Charghing";
        }


        Intent myIntent = new Intent(context,MainActivity.class);
        myIntent.putExtra("status",statusCharghing);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,myIntent,0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context,MainActivity.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Charging status changed!")
                .setContentText(statusCharghing)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(MainActivity.notificationId,mbuilder.build());

    }
}

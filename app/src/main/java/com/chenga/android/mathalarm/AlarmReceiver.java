package com.chenga.android.mathalarm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import java.util.UUID;


public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, AlarmService.class);
        service.putExtra(Alarm.ALARM_EXTRA, (UUID) intent.getExtras().get(Alarm.ALARM_EXTRA));
        startWakefulService(context, service);
    }

}

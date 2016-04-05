package com.chenga.android.mathalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            List<Alarm> alarms = AlarmLab.get(context).getAlarms();
            for (int i = 0; i < alarms.size(); i++) {
                Alarm alarm = alarms.get(i);
                if (alarm.isOn()) {
                    alarm.scheduleAlarm(context);
                }
            }
        }
    }
}

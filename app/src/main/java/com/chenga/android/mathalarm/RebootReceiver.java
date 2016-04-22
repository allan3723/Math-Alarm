package com.chenga.android.mathalarm;

/*
* The receiver the receives the boot completed intent when a device successfully boots up.
* Reschedules all enabled alarm since AlarmManager does not retain alarms after a device
* turns off.
 */

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

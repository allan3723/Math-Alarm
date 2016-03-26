package com.chenga.android.mathalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.UUID;

public class Alarm {

    private UUID mId;
    private int mHour;
    private int mMinute;
    private boolean mRepeat;
    private String mRepeatDays;
    private boolean mIsOn;

    public Alarm() {
        mId = UUID.randomUUID();
        mRepeatDays = new String("FFFFFFF");
    }

    public Alarm(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    public String getRepeat() {
        return mRepeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        mRepeatDays = repeatDays;
    }

    public boolean isOn() {
        return mIsOn;
    }

    public void setIsOn(boolean isOn) {
        mIsOn = isOn;
    }

    public void setRepeatOff() {
        mRepeatDays = new String("FFFFFFF");
        return;
    }

    public boolean isRepeat() {
        return mRepeat;
    }

    public void setRepeat(boolean repeat) {
        mRepeat = repeat;
    }

    public CharSequence getFormatTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);

        return android.text.format.DateFormat.format("hh:mm a", cal);
    }

    public void scheduleAlarm(Context context) {
        Intent alarm = new Intent(context, AlarmReceiver.class);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);
        cal.set(Calendar.SECOND, 0);

        for (int i = 0; i < 7; i++) {
            if (mRepeatDays.charAt(i) == 'T') {
                StringBuilder stringId = new StringBuilder(i).append(mHour).append(mMinute);
                int intentId = Integer.parseInt(stringId.toString());

                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, intentId, alarm,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                Log.d("Alarm", "Alarm scheduled on " + android.text.format.DateFormat.format("hh:mm a", cal));

                AlarmManager alarmManager = (AlarmManager) context
                        .getSystemService(Context.ALARM_SERVICE);

                if (isRepeat()) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY*7, alarmIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
                }
            }
        }
    }

    public void cancelAlarm(Context context) {
        Intent cancel = new Intent(context, AlarmReceiver.class);

        for (int i = 0; i < 7; i++) { //For each day of the week
            if (mRepeatDays.charAt(i) == 'T') {
                StringBuilder stringId = new StringBuilder(i).append(mHour).append(mMinute);
                int intentId = Integer.parseInt(stringId.toString());

                PendingIntent cancelAlarmPI = PendingIntent.getBroadcast(context, intentId, cancel,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarmManager = (AlarmManager)context
                        .getSystemService(Context.ALARM_SERVICE);

                alarmManager.cancel(cancelAlarmPI);
            }
        }
    }
}

package com.chenga.android.mathalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

public class Alarm {

    private UUID mId;
    private int mHour;
    private int mMinute;
    private boolean mRepeat;
    private String mRepeatDays;
    private boolean mIsOn;
    private int mDifficulty;
    private String mAlarmTone;
    private boolean mVibrate;
    private int mSnooze;

    public static final int SUN = 0;
    public static final int MON = 1;
    public static final int TUE = 2;
    public static final int WED = 3;
    public static final int THU = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    public static final String ALARM_EXTRA = "alarm_extra";

    public Alarm() {
        mId = UUID.randomUUID();
        mRepeatDays = new String("FFFFFFF");
        mDifficulty = EASY;
        mSnooze = 5;
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

    public boolean isRepeat() {
        return mRepeat;
    }

    public void setRepeat(boolean repeat) {
        mRepeat = repeat;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public String getAlarmTone() {
        return mAlarmTone;
    }

    public void setAlarmTone(String alarmTone) {
        mAlarmTone = alarmTone;
    }

    public boolean isVibrate() {
        return mVibrate;
    }

    public void setVibrate(boolean vibrate) {
        mVibrate = vibrate;
    }

    public int getSnooze() {
        return mSnooze;
    }

    public void setSnooze(int snooze) {
        mSnooze = snooze;
    }

    public CharSequence getFormatTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);

        return android.text.format.DateFormat.format("hh:mm a", cal);
    }

    public void scheduleAlarm(Context context) {
        Intent alarm = new Intent(context, AlarmReceiver.class);
        alarm.putExtra(ALARM_EXTRA, mId);

        for (int i = Alarm.SUN; i <= Alarm.SAT; i++) {
            if (mRepeatDays.charAt(i) == 'T') {

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, mHour);
                cal.set(Calendar.MINUTE, mMinute);
                cal.set(Calendar.SECOND, 0);

                int daysUntilAlarm;
                int currentDay = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
                if (currentDay > i) {
                    //days left till end of week(sat) + the day of the week of the alarm;
                    // EX: alarm = i = tues = 2; current = wed = 3; end of week = sat = 6
                    //end - current = 6 - 3 = 3 -> 3 days till saturday/end of week
                    //end of week + 1 (to sunday) + day of week alarm is on = 3 + 1 + 2 = 6
                    daysUntilAlarm = Alarm.SAT - currentDay + 1 + i;
                    cal.add(Calendar.DAY_OF_YEAR, daysUntilAlarm);
                } else {
                    daysUntilAlarm = i - currentDay;
                    cal.add(Calendar.DAY_OF_YEAR, daysUntilAlarm);
                }

                StringBuilder stringId = new StringBuilder(i).append(mHour).append(mMinute);
                int intentId = Integer.parseInt(stringId.toString());

                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, intentId, alarm,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarmManager = (AlarmManager) context
                        .getSystemService(Context.ALARM_SERVICE);

                if (isRepeat()) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7, alarmIntent);
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

    public String getTimeLeftMessage() {
        String message;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);
        cal.set(Calendar.SECOND, 0);

        long alarmTime = cal.getTimeInMillis();
        long remainderTime = alarmTime - System.currentTimeMillis();

        int minutes = (int) ((remainderTime / (1000*60)) % 60);
        int hours   = (int) ((remainderTime / (1000*60*60)) % 24);
        int days = (int) (remainderTime / (1000*60*60*24));

        if (days == 0) {
            if (hours == 0) {
                message = "Alarm scheduled in "+minutes+" minutes.";
            } else {
                message = "Alarm schedule in "+hours+" hours, "+minutes+" minutes.";
            }
        } else {
            message = "Alarm schedule in "+days+" days, "+hours+" hours, "+minutes+" minutes.";
        }

        return message;
    }

    public int getDayOfWeek(int day) {
        int dayOfWeek;
        int errorValue = 8;

        switch(day) {
            case Calendar.SUNDAY:
                dayOfWeek = Alarm.SUN;
                break;
            case Calendar.MONDAY:
                dayOfWeek = Alarm.MON;
                break;
            case Calendar.TUESDAY:
                dayOfWeek = Alarm.TUE;
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = Alarm.WED;
                break;
            case Calendar.THURSDAY:
                dayOfWeek = Alarm.THU;
                break;
            case Calendar.FRIDAY:
                dayOfWeek = Alarm.FRI;
                break;
            case Calendar.SATURDAY:
                dayOfWeek = Alarm.SAT;
                break;
            default:
                return errorValue;
        }

        return dayOfWeek;
    }
}

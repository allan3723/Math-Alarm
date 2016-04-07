package com.chenga.android.mathalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        mRepeat = false;
        mRepeatDays = "FFFFFFF";
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

    public boolean isActive() {
        return (!mRepeatDays.equals("FFFFFFF"));
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

    public boolean scheduleAlarm(Context context) {
        Intent alarm = new Intent(context, AlarmReceiver.class);
        alarm.putExtra(ALARM_EXTRA, mId);
        List<PendingIntent> alarmIntent = new ArrayList<>();
        List<Calendar> time = new ArrayList<>();

        // If there is no days set, set the alarm on the closest possible date
        if (mRepeatDays.equals("FFFFFFF")) {
            Calendar cal = initCalendar();
            int dayOfTheWeek = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));

            if (cal.getTimeInMillis() > System.currentTimeMillis()) { //set it today
                StringBuilder sb = new StringBuilder("FFFFFFF");
                sb.setCharAt(dayOfTheWeek, 'T');
                mRepeatDays = sb.toString();
            } else {    //alarm time already passed for the day so set it tomorrow
                StringBuilder sb = new StringBuilder("FFFFFFF");

                if (dayOfTheWeek == Alarm.SAT) { //if it is saturday
                    dayOfTheWeek = Alarm.SUN;
                } else {
                    dayOfTheWeek++;
                }
                sb.setCharAt(dayOfTheWeek, 'T');
                mRepeatDays = sb.toString();
            }
        }

        for (int i = Alarm.SUN; i <= Alarm.SAT; i++) {
            if (mRepeatDays.charAt(i) == 'T') {
                int daysUntilAlarm;
                Calendar cal = initCalendar();

                int currentDay = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
                if (currentDay > i ||
                        (currentDay == i && cal.getTimeInMillis() < System.currentTimeMillis())) {
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

                StringBuilder stringId = new StringBuilder().append(i)
                        .append(mHour).append(mMinute);
                int intentId = Integer.parseInt(stringId.toString());

                //check if a previous alarm has been set
                if (PendingIntent.getBroadcast(context, intentId, alarm,
                        PendingIntent.FLAG_NO_CREATE) != null) {
                    Toast.makeText(context, context.getString(R.string.alarm_duplicate_toast_text),
                            Toast.LENGTH_SHORT).show();

                    return false;
                }

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intentId, alarm,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                alarmIntent.add(pendingIntent);
                time.add(cal);
            }
        }

        for (int i = 0; i < alarmIntent.size(); i++) {
            PendingIntent pendingIntent = alarmIntent.get(i);
            Calendar cal = time.get(i);
            AlarmManager alarmManager = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);

            if (isRepeat()) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY * 7, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }
        }

        return true;
    }

    public void scheduleSnooze(Context context){
        Intent alarm = new Intent(context, AlarmReceiver.class);
        alarm.putExtra(ALARM_EXTRA, mId);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, mSnooze);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, alarm,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);

        if (mSnooze == 1) {
            Toast.makeText(context, context.getString(R.string.alarm_set_begin_msg)+" "
                            +mSnooze+context.getString(R.string.alarm_minute)+" "+
                            context.getString(R.string.alarm_set_end_msg),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.alarm_set_begin_msg)+" "
                            +mSnooze+context.getString(R.string.alarm_minutes)+" "+
                            context.getString(R.string.alarm_set_end_msg),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelAlarm(Context context) {
        Intent cancel = new Intent(context, AlarmReceiver.class);

        for (int i = 0; i < 7; i++) { //For each day of the week
            if (mRepeatDays.charAt(i) == 'T') {
                StringBuilder stringId = new StringBuilder().append(i)
                        .append(mHour).append(mMinute);
                int intentId = Integer.parseInt(stringId.toString());

                PendingIntent cancelAlarmPI = PendingIntent.getBroadcast(context, intentId, cancel,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarmManager = (AlarmManager)context
                        .getSystemService(Context.ALARM_SERVICE);

                alarmManager.cancel(cancelAlarmPI);
                cancelAlarmPI.cancel();
            }
        }
    }

    public String getTimeLeftMessage(Context context) {
        String message;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);
        cal.set(Calendar.SECOND, 0);
        int today = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));

        int i, lastAlarmDay, nextAlarmDay;

        if (System.currentTimeMillis() > cal.getTimeInMillis()) {
            nextAlarmDay = today + 1;
            lastAlarmDay = today;
            if (nextAlarmDay == 7) {
                nextAlarmDay = 0;
            }

        } else {
            nextAlarmDay = today;
            lastAlarmDay = today - 1;
            if (lastAlarmDay == -1) {
                lastAlarmDay = 6;
            }
        }

        for (i = nextAlarmDay; i != lastAlarmDay; i++) {
            if(i == 7) {
                i = 0;
            }
            if (mRepeatDays.charAt(i) == 'T') {
                break;
            }
        }

        if (i < today || (i == today && cal.getTimeInMillis() < System.currentTimeMillis())) {
            int daysUntilAlarm = Alarm.SAT - today + 1 + i;
            cal.add(Calendar.DAY_OF_YEAR, daysUntilAlarm);
        } else {
            int daysUntilAlarm = i - today;
            cal.add(Calendar.DAY_OF_YEAR, daysUntilAlarm);
        }

        long alarmTime = cal.getTimeInMillis();
        long remainderTime = alarmTime - System.currentTimeMillis();

        int minutes = (int) ((remainderTime / (1000*60)) % 60);
        int hours   = (int) ((remainderTime / (1000*60*60)) % 24);
        int days = (int) (remainderTime / (1000*60*60*24));

        String mString, hString, dString;

        if (minutes == 1) {
            mString = context.getString(R.string.alarm_minute);
        } else {
            mString = context.getString(R.string.alarm_minutes);
        }

        if (hours == 1) {
            hString = context.getString(R.string.alarm_hour);
        } else {
            hString = context.getString(R.string.alarm_hours);
        }

        if (days == 1) {
            dString = context.getString(R.string.alarm_day);
        } else {
            dString = context.getString(R.string.alarm_days);
        }

        if (days == 0) {
            if (hours == 0) {
                message = context.getString(R.string.alarm_set_begin_msg)+" "+minutes+" "
                        +mString+" "+context.getString(R.string.alarm_set_end_msg);
            } else {
                message = context.getString(R.string.alarm_set_begin_msg)+" "
                        +hours+" "+hString+" "+minutes+" "+mString+" "+
                        context.getString(R.string.alarm_set_end_msg);
            }
        } else {
            message = context.getString(R.string.alarm_set_begin_msg)+" "
                    +days+" "+dString+" "+hours+" "+hString+" "+minutes+" "+
                    mString+" "+context.getString(R.string.alarm_set_end_msg);
        }

        return message;
    }

    private Calendar initCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);
        cal.set(Calendar.SECOND, 0);

        return cal;
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

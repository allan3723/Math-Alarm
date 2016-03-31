package com.chenga.android.mathalarm;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import static com.chenga.android.mathalarm.AlarmDbSchema.*;

public class AlarmCursorWrapper extends CursorWrapper {

    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alarm getAlarm() {
        String alarmId = getString(getColumnIndex(AlarmTable.Cols.ALARMID));
        int hour = getInt(getColumnIndex(AlarmTable.Cols.HOUR));
        int minute = getInt(getColumnIndex(AlarmTable.Cols.MINUTE));
        int repeat = getInt(getColumnIndex(AlarmTable.Cols.REPEAT));
        String days = getString(getColumnIndex(AlarmTable.Cols.DAYSOFTHEWEEK));
        int on = getInt(getColumnIndex(AlarmTable.Cols.ON));
        int difficulty = getInt(getColumnIndex(AlarmTable.Cols.DIFFICULTY));
        String tone = getString(getColumnIndex(AlarmTable.Cols.ALARMTONE));
        int snooze = getInt(getColumnIndex(AlarmTable.Cols.SNOOZE));
        int vibrate = getInt(getColumnIndex(AlarmTable.Cols.VIBRATE));

        Alarm alarm = new Alarm(UUID.fromString(alarmId));
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.setRepeat(repeat != 0);
        alarm.setRepeatDays(days);
        alarm.setIsOn(on != 0);
        alarm.setDifficulty(difficulty);
        alarm.setAlarmTone(tone);
        alarm.setSnooze(snooze);
        alarm.setVibrate(vibrate != 0);

        return alarm;
    }
}

package com.chenga.android.mathalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.chenga.android.mathalarm.AlarmDbSchema.*;

public class AlarmLab {
    private static AlarmLab sAlarmLab;

    private SQLiteDatabase mDatabase;
    private Context mContext;

    private List<Alarm> mAlarms;

    public static AlarmLab get(Context context) {
        if (sAlarmLab == null) {
            sAlarmLab = new AlarmLab(context);
        }

        return sAlarmLab;
    }

    private AlarmLab(Context context) {
        mContext = context;
        mDatabase = new AlarmBaseHelper(mContext).getWritableDatabase();
    }

    public List<Alarm> getAlarms() {
        List<Alarm> alarms = new ArrayList<>();
        AlarmCursorWrapper cursor = queryAlarms(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alarms.add(cursor.getAlarm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return alarms;
    }

    public Alarm getAlarm(UUID alarmId) {
        AlarmCursorWrapper cursor = queryAlarms(
                AlarmTable.Cols.ALARMID + " = ?",
                new String[]{alarmId.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getAlarm();
        } finally {
            cursor.close();
        }
    }

    public void updateAlarm(Alarm alarm) {
        String alarmIdString = alarm.getId().toString();
        ContentValues values = getContentValues(alarm);

        mDatabase.update(AlarmTable.NAME, values,
                AlarmTable.Cols.ALARMID + " = ?", new String[]{alarmIdString});
    }

    public void addAlarm(Alarm s) {
        ContentValues values = getContentValues(s);

        mDatabase.insert(AlarmTable.NAME, null, values);
    }

    public void deleteAlarm(UUID alarmId) {
        String widgetIdString = alarmId.toString();
        mDatabase.delete(AlarmTable.NAME, AlarmTable.Cols.ALARMID + " = ?",
                new String[]{widgetIdString});
    }

    private static ContentValues getContentValues(Alarm alarm) {
        ContentValues values = new ContentValues();

        values.put(AlarmTable.Cols.ALARMID, alarm.getId().toString());
        values.put(AlarmTable.Cols.HOUR, alarm.getHour());
        values.put(AlarmTable.Cols.MINUTE, alarm.getMinute());
        values.put(AlarmTable.Cols.DAYSOFTHEWEEK, alarm.getRepeat());
        values.put(AlarmTable.Cols.REPEAT, alarm.isRepeat() ? 1 : 0);
        values.put(AlarmTable.Cols.ON, alarm.isOn() ? 1 : 0);
        values.put(AlarmTable.Cols.DIFFICULTY, alarm.getDifficulty());
        values.put(AlarmTable.Cols.ALARMTONE, alarm.getAlarmTone());
        values.put(AlarmTable.Cols.SNOOZE, alarm.getSnooze());
        values.put(AlarmTable.Cols.VIBRATE, alarm.isVibrate() ? 1 : 0);

        return values;
    }

    private AlarmCursorWrapper queryAlarms(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AlarmTable.NAME,
                null,   //Columns: null = all
                whereClause,
                whereArgs,
                null,   //Group By
                null,   // Having
                null    // Order By
        );

        return new AlarmCursorWrapper(cursor);
    }

    public int getSize() { return mAlarms.size(); }
}

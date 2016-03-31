package com.chenga.android.mathalarm;

public class AlarmDbSchema {
    public static final class AlarmTable {
        public static final String NAME = "alarms";

        public static final class Cols {
            public static final String ALARMID = "alarmid";
            public static final String HOUR = "hour";
            public static final String MINUTE = "minute";
            public static final String REPEAT = "repeat";
            public static final String DAYSOFTHEWEEK = "daysoftheweek";
            public static final String ON = "ison";
            public static final String DIFFICULTY = "difficulty";
            public static final String ALARMTONE = "tone";
            public static final String SNOOZE = "snooze";
            public static final String VIBRATE = "vibrate";
        }
    }
}

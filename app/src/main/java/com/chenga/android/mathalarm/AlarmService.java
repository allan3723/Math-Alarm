package com.chenga.android.mathalarm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Intent mathActivity = new Intent(this, AlarmMathActivity.class);
        mathActivity.putExtra(Alarm.ALARM_EXTRA, (UUID) intent.getExtras().get(Alarm.ALARM_EXTRA));
        mathActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mathActivity);
        AlarmReceiver.completeWakefulIntent(intent);
    }

}

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

public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Intent mathActivity = new Intent(this, AlarmMathActivity.class);
        mathActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mathActivity);

        for (int i=0; i<5; i++) {
            Log.i("SimpleWakefulReceiver", "Running service " + (i+1)
                    + "/5 @ " + SystemClock.elapsedRealtime());

            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            //v.vibrate(1000);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

        AlarmReceiver.completeWakefulIntent(intent);
    }

}

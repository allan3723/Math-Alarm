package com.chenga.android.mathalarm;

/*
* Gets called by wakefulbroadcastreceiver and completes the wakelock.
* This class in turn calls the math activity.
 */
import android.app.IntentService;
import android.content.Intent;
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

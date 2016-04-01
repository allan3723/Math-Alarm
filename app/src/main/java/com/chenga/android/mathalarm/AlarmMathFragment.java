package com.chenga.android.mathalarm;

import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class AlarmMathFragment extends Fragment {

    TextView mAlarmTime;
    TextView mQuestion;
    TextView mAnswer;
    Button mOneButton;
    Button mTwoButton;
    Button mThreeButton;
    Button mFourButton;
    Button mFiveButton;
    Button mSixButton;
    Button mSevenButton;
    Button mEightButton;
    Button mNineButton;
    Button mZeroButton;
    Button mSetButton;
    Button mDelButton;

    private StringBuilder sb;
    private int op, num1, num2, ans;

    private static final int ADD = 0;
    private static final int SUBTRACT = 1;
    private static final int TIMES = 2;
    private static final int DIVIDE = 3;
    private MediaPlayer mp = new MediaPlayer();

    public static AlarmMathFragment newInstance() {
        return new AlarmMathFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        Bundle extra = getActivity().getIntent().getExtras();


        UUID alarmId = (UUID) extra.get(Alarm.ALARM_EXTRA);
        Alarm alarm = AlarmLab.get(getActivity()).getAlarm(alarmId);

        View v = inflater.inflate(R.layout.fragment_alarm_math, parent, false);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        //--------------------
        RingtoneManager ringtoneMgr = new RingtoneManager(getActivity());
        ringtoneMgr.setType(RingtoneManager.TYPE_ALL);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            return null;
        }
        Uri[] alarms = new Uri[alarmsCount];
        String[] alarmTitle = new String[alarmsCount];
        while(!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
            int currentPosition = alarmsCursor.getPosition();
            alarms[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);
            alarmTitle[currentPosition] = ringtoneMgr.getRingtone(currentPosition)
                    .getTitle(getActivity());

        }
        alarmsCursor.close();

        for (int i = 0; i < alarms.length; i++) {
            Log.d("AlarmMathFragment", alarmTitle[i].toString() + "\n");
        }
        //--------------------

        try {
            mp.setDataSource(getContext(), alarmUri);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepare();
            mp.setLooping(true);
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getMathProblem(alarm.getDifficulty());
        Log.d("Math Fragment", "Difficulty = "+ alarm.getDifficulty());
        sb = new StringBuilder("");
        Calendar cal = Calendar.getInstance();

        //mAlarmTime = (TextView) v.findViewById(R.id.math_alarm_time);
        //mAlarmTime.setText(android.text.format.DateFormat.format("hh:mm a", cal));
        mQuestion = (TextView) v.findViewById(R.id.math_question);
        mQuestion.setText(getMathString());
        mAnswer = (TextView) v.findViewById(R.id.math_answer);
        mOneButton = (Button) v.findViewById(R.id.math_btn_1);
        mOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(1);
                mAnswer.setText(sb);
            }
        });
        mTwoButton = (Button) v.findViewById(R.id.math_btn_2);
        mTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(2);
                mAnswer.setText(sb);
            }
        });
        mThreeButton = (Button) v.findViewById(R.id.math_btn_3);
        mThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(3);
                mAnswer.setText(sb);
            }
        });
        mFourButton = (Button) v.findViewById(R.id.math_btn_4);
        mFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(4);
                mAnswer.setText(sb);
            }
        });
        mFiveButton = (Button) v.findViewById(R.id.math_btn_5);
        mFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(5);
                mAnswer.setText(sb);
            }
        });
        mSixButton = (Button) v.findViewById(R.id.math_btn_6);
        mSixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(6);
                mAnswer.setText(sb);
            }
        });
        mSevenButton = (Button) v.findViewById(R.id.math_btn_7);
        mSevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(7);
                mAnswer.setText(sb);
            }
        });
        mEightButton = (Button) v.findViewById(R.id.math_btn_8);
        mEightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(8);
                mAnswer.setText(sb);
            }
        });
        mNineButton = (Button) v.findViewById(R.id.math_btn_9);
        mNineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(9);
                mAnswer.setText(sb);
            }
        });
        mZeroButton = (Button) v.findViewById(R.id.math_btn_0);
        mZeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.append(0);
                mAnswer.setText(sb);
            }
        });
        mSetButton = (Button) v.findViewById(R.id.math_btn_set);
        mSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(sb.toString()) != ans) {
                    Toast.makeText(getActivity(), "Incorrect!", Toast.LENGTH_SHORT).show();
                    sb.setLength(0);
                    mAnswer.setText("");
                } else {
                    mp.stop();
                    getActivity().finish();
                }
            }
        });
        mDelButton = (Button) v.findViewById(R.id.math_btn_del);
        mDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sb.length() != 0) {
                    sb.deleteCharAt(sb.length() - 1);
                    mAnswer.setText(sb);
                }
            }
        });

        return v;
    }

    private void getMathProblem(int difficulty) {
        Random random = new Random();

        op = random.nextInt(4);
        int add1, add2, mult1, mult2;

        switch(difficulty) {
            case Alarm.EASY:
                add1 = 90;
                add2 = 10;
                mult1 = 10;
                mult2 = 3;
                break;
            case Alarm.HARD:
                add1 = 9000;
                add2 = 1000;
                mult1 = 14;
                mult2 = 12;
                break;
            default:
                add1 = 900;
                add2 = 100;
                mult1 = 13;
                mult2 = 3;
                break;
        }

        switch (op) {
            case ADD:
                num1 = random.nextInt(add1) + add2;
                num2 = random.nextInt(add1) + add2;
                ans = num1 + num2;
                break;
            case SUBTRACT:
                num1 = random.nextInt(add1) + add2;
                num2 = random.nextInt(add1) + add2;

                if (num1 < num2) {
                    int temp = num1;
                    num1 = num2;
                    num2 = temp;
                }
                ans = num1 - num2;
                break;
            case TIMES:
                num1 = random.nextInt(mult1) + mult2;
                num2 = random.nextInt(mult1) + mult2;
                ans = num1 * num2;
                break;
            case DIVIDE:
                num1 = random.nextInt(mult1) + mult2;
                num2 = random.nextInt(mult1) + mult2;
                ans = num1 * num2;

                int tmp = ans;
                ans = num1;
                num1 = tmp;
                break;
            default:
                break;
        }
    }

    private String getMathString() {
        switch(op) {
            case ADD:
                return new String(num1 + " + " + num2 + " = ");
            case SUBTRACT:
                return new String(num1 + " - " + num2 + " = ");
            case TIMES:
                return new String(num1 + " x " + num2 + " = ");
            case DIVIDE:
                return new String(num1 + " / " + num2 + " = ");
            default:
                return null;
        }
    }

}

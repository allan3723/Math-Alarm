package com.chenga.android.mathalarm;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AlarmSettingsFragment extends Fragment {

    TextView mTime;
    ToggleButton mRSunTButton;
    ToggleButton mRMonTButton;
    ToggleButton mRTueTButton;
    ToggleButton mRWedTButton;
    ToggleButton mRThuTButton;
    ToggleButton mRFriTButton;
    ToggleButton mRSatTButton;
    Switch mRepeatSwitch;
    Spinner mDifficultySpinner;
    Spinner mToneSpinner;
    EditText mSnoozeText;
    Switch mVibrateSwitch;
    Button mTestButton;

    AlarmLab mAlarmLab;
    Alarm mAlarm;
    Alarm mTestAlarm;
    String mId, mRepeat;
    boolean mAdd = false;

    Uri[] mAlarmTones;

    private static final int REQUEST_TIME = 0;
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_TEST = 1;


    private static final String TAG = "AlarmSettings";

    public static AlarmSettingsFragment newInstance() {
        return new AlarmSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm_settings, parent, false);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            mId = extras.getString(AlarmFragment.GET_ALARM);
        }

        mAlarmLab = AlarmLab.get(getActivity());

        if (!mId.isEmpty()) {
            mAlarm = mAlarmLab.getAlarm(UUID.fromString(mId));
        } else {
            mAlarm = new Alarm();
            Calendar cal = Calendar.getInstance();

            mAlarm.setHour(cal.get(Calendar.HOUR_OF_DAY));
            mAlarm.setMinute(cal.get(Calendar.MINUTE));
            mAdd = true;
        }

        if (savedInstanceState != null) {
            mAlarm.setHour(savedInstanceState.getInt("hour", 0));
            mAlarm.setMinute(savedInstanceState.getInt("minute", 0));
            mAlarm.setRepeatDays(savedInstanceState.getString("repeat"));
            mAlarm.setRepeat(savedInstanceState.getBoolean("repeatweekly"));
        }

        mRepeat = mAlarm.getRepeat();

        mTime = (TextView) v.findViewById(R.id.settings_time);
        mTime.setText(mAlarm.getFormatTime());
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mAlarm.getHour(), mAlarm.getMinute());
                dialog.setTargetFragment(AlarmSettingsFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mRSunTButton = (ToggleButton) v.findViewById(R.id.set_repeat_sun);
        mRSunTButton.setChecked(mRepeat.charAt(Alarm.SUN) == 'T');
        mRSunTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRSunTButton.setChecked(mRepeat.charAt(Alarm.SUN) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.SUN) == 'T') {
                    sb.setCharAt(Alarm.SUN, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.SUN, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRMonTButton = (ToggleButton) v.findViewById(R.id.set_repeat_mon);
        mRMonTButton.setChecked(mRepeat.charAt(Alarm.MON) == 'T');
        mRMonTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRMonTButton.setChecked(mRepeat.charAt(Alarm.MON) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.MON) == 'T') {
                    sb.setCharAt(Alarm.MON, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.MON, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRTueTButton = (ToggleButton) v.findViewById(R.id.set_repeat_tue);
        mRTueTButton.setChecked(mRepeat.charAt(Alarm.TUE) == 'T');
        mRTueTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRTueTButton.setChecked(mRepeat.charAt(Alarm.TUE) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.TUE) == 'T') {
                    sb.setCharAt(Alarm.TUE, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.TUE, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRWedTButton = (ToggleButton) v.findViewById(R.id.set_repeat_wed);
        mRWedTButton.setChecked(mRepeat.charAt(Alarm.WED) == 'T');
        mRWedTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRWedTButton.setChecked(mRepeat.charAt(Alarm.WED) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.WED) == 'T') {
                    sb.setCharAt(Alarm.WED, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.WED, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRThuTButton = (ToggleButton) v.findViewById(R.id.set_repeat_thu);
        mRThuTButton.setChecked(mRepeat.charAt(Alarm.THU) == 'T');
        mRThuTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRThuTButton.setChecked(mRepeat.charAt(Alarm.THU) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.THU) == 'T') {
                    sb.setCharAt(Alarm.THU, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.THU, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRFriTButton = (ToggleButton) v.findViewById(R.id.set_repeat_fri);
        mRFriTButton.setChecked(mRepeat.charAt(Alarm.FRI) == 'T');
        mRFriTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRFriTButton.setChecked(mRepeat.charAt(Alarm.FRI) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.FRI) == 'T') {
                    sb.setCharAt(Alarm.FRI, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.FRI, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRSatTButton = (ToggleButton) v.findViewById(R.id.set_repeat_sat);
        mRSatTButton.setChecked(mRepeat.charAt(Alarm.SAT) == 'T');
        mRSatTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRSatTButton.setChecked(mRepeat.charAt(Alarm.SAT) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(Alarm.SAT) == 'T') {
                    sb.setCharAt(Alarm.SAT, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(Alarm.SAT, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRepeatSwitch = (Switch) v.findViewById(R.id.settings_repeat_switch);
        mRepeatSwitch.setChecked(mAlarm.isOn());
        mRepeatSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepeatSwitch.setChecked(!mAlarm.isRepeat());
                mAlarm.setIsOn(!mAlarm.isRepeat());
            }
        });


        mToneSpinner = (Spinner) v.findViewById(R.id.settings_tone_spinner);
        List<String> toneItems = new ArrayList<>();
        RingtoneManager ringtoneMgr = new RingtoneManager(getActivity());
        ringtoneMgr.setType(RingtoneManager.TYPE_ALARM);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();

        if (alarmsCount == 0) { //if there are no alarms, use notification sounds
            ringtoneMgr.setType(RingtoneManager.TYPE_NOTIFICATION);
            alarmsCursor = ringtoneMgr.getCursor();
            alarmsCount = alarmsCursor.getCount();

            if (alarmsCount == 0) { //if no alarms and notification sounds, finally use ringtones
                ringtoneMgr.setType(RingtoneManager.TYPE_RINGTONE);
                alarmsCursor = ringtoneMgr.getCursor();
                alarmsCount = alarmsCursor.getCount();
            }
        }

        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            Toast.makeText(getActivity(), "No sound files available", Toast.LENGTH_SHORT).show();
        }

        mAlarmTones = new Uri[alarmsCount];

        int previousPosition = 0;
        String currentTone = mAlarm.getAlarmTone();

        while(!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
            int currentPosition = alarmsCursor.getPosition();
            mAlarmTones[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);
            toneItems.add(ringtoneMgr.getRingtone(currentPosition)
                    .getTitle(getActivity()));

            if (currentTone != null &&
                    currentTone.equals(mAlarmTones[currentPosition].toString())) {
                previousPosition = currentPosition;
            }
        }


        ArrayAdapter<String> toneAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, toneItems);
        mToneSpinner.setAdapter(toneAdapter);
        mToneSpinner.setSelection(previousPosition);

        mDifficultySpinner = (Spinner) v.findViewById(R.id.settings_math_difficulty_spinner);
        String[] difficultyItems = new String[]{"Easy", "Medium", "Hard"};
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, difficultyItems);
        mDifficultySpinner.setAdapter(difficultyAdapter);
        mDifficultySpinner.setSelection(mAlarm.getDifficulty());

        mSnoozeText = (EditText) v.findViewById(R.id.settings_snooze_text);
        mSnoozeText.setText(Integer.toString(mAlarm.getSnooze()));
        mSnoozeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    mAlarm.setSnooze(Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mVibrateSwitch = (Switch) v.findViewById(R.id.settings_vibrate_switch);
        mVibrateSwitch.setChecked(mAlarm.isVibrate());
        mVibrateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrateSwitch.setChecked(!mAlarm.isVibrate());
                mAlarm.setVibrate(!mAlarm.isVibrate());
            }
        });

        mTestButton = (Button) v.findViewById(R.id.settings_test_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestAlarm = new Alarm();
                mTestAlarm.setDifficulty(mDifficultySpinner.getSelectedItemPosition());
                if (mAlarmTones.length != 0) {
                    mTestAlarm.setAlarmTone(mAlarmTones[mToneSpinner
                            .getSelectedItemPosition()].toString());
                }
                mTestAlarm.setVibrate(mVibrateSwitch.isChecked());
                mTestAlarm.setSnooze(0);
                AlarmLab.get(getActivity()).addAlarm(mTestAlarm);
                Intent test = new Intent(getActivity(), AlarmMathActivity.class);
                test.putExtra(Alarm.ALARM_EXTRA, mTestAlarm.getId());
                startActivityForResult(test, REQUEST_TEST);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.alarm_settings_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fragment_settings_done:
                mAlarm.setIsOn(true);

                // If there is no days set, set the alarm on the closest possible date
                if (mAlarm.getRepeat().equals("FFFFFFF")) {
                    Calendar cal = Calendar.getInstance();
                    int dayOfTheWeek = mAlarm.getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));

                    Log.d(TAG, "mAlarm hour = "+mAlarm.getHour()+", hour = "+cal.get(Calendar.HOUR_OF_DAY));

                    if (mAlarm.getHour() > cal.get(Calendar.HOUR_OF_DAY) ||
                            (mAlarm.getHour() == cal.get(Calendar.HOUR_OF_DAY) &&
                                    (mAlarm.getMinute() > cal.get(Calendar.MINUTE)))) { //set it today
                        StringBuilder sb = new StringBuilder("FFFFFFF");
                        sb.setCharAt(dayOfTheWeek, 'T');
                        mAlarm.setRepeatDays(sb.toString());
                    } else {    //alarm time already passed for the day so set it tomorrow
                        StringBuilder sb = new StringBuilder("FFFFFFF");

                        if (dayOfTheWeek == Alarm.SAT) { //if it is saturday
                            dayOfTheWeek = Alarm.SUN;
                        } else {
                            dayOfTheWeek++;
                        }
                        sb.setCharAt(dayOfTheWeek, 'T');
                        mAlarm.setRepeatDays(sb.toString());
                    }
                }

                mAlarm.setDifficulty(mDifficultySpinner.getSelectedItemPosition());
                if (mAlarmTones.length != 0) {
                    mAlarm.setAlarmTone(mAlarmTones[mToneSpinner
                            .getSelectedItemPosition()].toString());
                }

                //update to database
                if (mAdd) {
                    AlarmLab.get(getActivity()).addAlarm(mAlarm);
                } else {
                    Alarm oldAlarm = AlarmLab.get(getActivity()).getAlarm(mAlarm.getId());
                    oldAlarm.cancelAlarm(getContext());
                    AlarmLab.get(getActivity()).updateAlarm(mAlarm);
                }

                //schedule it and close settings
                mAlarm.scheduleAlarm(getContext());
                getActivity().finish();
                return true;
            case R.id.fragment_settings_delete:
                if (!mAdd) {
                    //if the settings was not reached by the add button,
                    //then it needs to be deleted off the database
                    AlarmLab.get(getActivity()).deleteAlarm(mAlarm.getId());
                }

                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_TIME) {
            int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, 0);
            int min = data.getIntExtra(TimePickerFragment.EXTRA_MIN, 0);
            mAlarm.setHour(hour);
            mAlarm.setMinute(min);
            mTime.setText(mAlarm.getFormatTime());
        } else {
            if (requestCode == REQUEST_TEST) {
                AlarmLab.get(getActivity()).deleteAlarm(mTestAlarm.getId());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("hour", mAlarm.getHour());
        savedInstanceState.putInt("minute", mAlarm.getMinute());
        savedInstanceState.putString("repeat", mAlarm.getRepeat());
        savedInstanceState.putBoolean("repeatweekly", mAlarm.isRepeat());
    }
}
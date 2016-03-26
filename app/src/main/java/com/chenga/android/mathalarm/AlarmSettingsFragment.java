package com.chenga.android.mathalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
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

    AlarmLab mAlarmLab;
    Alarm mAlarm;
    String mId, mRepeat;
    boolean mAdd = false;

    private static final int REQUEST_TIME = 0;
    private static final String DIALOG_TIME = "DialogTime";

    private static final int SUN = 0;
    private static final int MON = 1;
    private static final int TUE = 2;
    private static final int WED = 3;
    private static final int THU = 4;
    private static final int FRI = 5;
    private static final int SAT = 6;
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
        mRSunTButton.setChecked(mRepeat.charAt(SUN) == 'T');
        mRSunTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRSunTButton.setChecked(mRepeat.charAt(SUN) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(SUN) == 'T') {
                    sb.setCharAt(SUN, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(SUN, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRMonTButton = (ToggleButton) v.findViewById(R.id.set_repeat_mon);
        mRMonTButton.setChecked(mRepeat.charAt(MON) == 'T');
        mRMonTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRMonTButton.setChecked(mRepeat.charAt(MON) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(MON) == 'T') {
                    sb.setCharAt(MON, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(MON, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRTueTButton = (ToggleButton) v.findViewById(R.id.set_repeat_tue);
        mRTueTButton.setChecked(mRepeat.charAt(TUE) == 'T');
        mRTueTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRTueTButton.setChecked(mRepeat.charAt(TUE) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(TUE) == 'T') {
                    sb.setCharAt(TUE, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(TUE, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRWedTButton = (ToggleButton) v.findViewById(R.id.set_repeat_wed);
        mRWedTButton.setChecked(mRepeat.charAt(WED) == 'T');
        mRWedTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRWedTButton.setChecked(mRepeat.charAt(WED) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(WED) == 'T') {
                    sb.setCharAt(WED, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(WED, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRThuTButton = (ToggleButton) v.findViewById(R.id.set_repeat_thu);
        mRThuTButton.setChecked(mRepeat.charAt(THU) == 'T');
        mRThuTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRThuTButton.setChecked(mRepeat.charAt(THU) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(THU) == 'T') {
                    sb.setCharAt(THU, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(THU, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRFriTButton = (ToggleButton) v.findViewById(R.id.set_repeat_fri);
        mRFriTButton.setChecked(mRepeat.charAt(FRI) == 'T');
        mRFriTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRFriTButton.setChecked(mRepeat.charAt(FRI) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(FRI) == 'T') {
                    sb.setCharAt(FRI, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(FRI, 'T');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                }
            }
        });

        mRSatTButton = (ToggleButton) v.findViewById(R.id.set_repeat_sat);
        mRSatTButton.setChecked(mRepeat.charAt(SAT) == 'T');
        mRSatTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRSatTButton.setChecked(mRepeat.charAt(SAT) != 'T');

                StringBuilder sb = new StringBuilder(mRepeat);
                if (mRepeat.charAt(SAT) == 'T') {
                    sb.setCharAt(SAT, 'F');
                    mRepeat = sb.toString();
                    mAlarm.setRepeatDays(mRepeat);
                } else {
                    sb.setCharAt(SAT, 'T');
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
                if (mAlarm.getRepeat().equals("FFFFFF")) {
                    Calendar cal = Calendar.getInstance();

                    if (mAlarm.getHour() > cal.get(Calendar.HOUR_OF_DAY)) { //set it today
                        StringBuilder sb = new StringBuilder("FFFFFFF");
                        sb.setCharAt(cal.get(Calendar.DAY_OF_WEEK), 'T');
                        mAlarm.setRepeatDays(sb.toString());
                    } else {    //alarm time already passed for the day so set it tomorrow
                        StringBuilder sb = new StringBuilder("FFFFFFF");
                        int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);

                        if (dayOfTheWeek == 6) { //if it is saturday
                            dayOfTheWeek = 0;
                        } else {
                            dayOfTheWeek++;
                        }
                        sb.setCharAt(dayOfTheWeek, 'T');
                        mAlarm.setRepeatDays(sb.toString());
                    }
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

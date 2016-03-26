package com.chenga.android.mathalarm;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AlarmSettingsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AlarmSettingsFragment.newInstance();
    }
}
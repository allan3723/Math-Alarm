package com.chenga.android.mathalarm;

import android.support.v4.app.Fragment;

public class AlarmActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AlarmFragment.newInstance();
    }
}


package com.chenga.android.mathalarm;

import android.support.v4.app.Fragment;

public class AlarmMathActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AlarmMathFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }
}

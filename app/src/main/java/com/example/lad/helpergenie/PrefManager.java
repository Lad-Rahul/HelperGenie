package com.example.lad.helpergenie;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LAD on 08-Feb-18.
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences pref2;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "HelperGenie-welcome";
    private static final String PREF_NAME2 = "HelperGenie-welcome2";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref2 = _context.getSharedPreferences(PREF_NAME2, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

    public void setFirstTimeLaunch2(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch2() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

}

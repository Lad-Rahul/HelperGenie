package com.example.lad.helpergenie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;

/**
 * Created by Pro on 2/8/2018.
 */

public class firsttime extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "HelperGenie-welcome2";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    int PRIVATE_MODE = 0;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.firsttime,container,false);
        getActivity().setTitle("Help");
        //PrefManager pref = new PrefManager(getActivity());

//        pref.setFirstTimeLaunch(true);
//        if(pref.isFirstTimeLaunch() == false){
//
//        }

        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        if(pref.getBoolean(IS_FIRST_TIME_LAUNCH,false) == true){
            Log.d("FIRSTTIME LAUNCH","is there");
        }else{
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
            editor.commit();
        }
        return mView;

    }

}

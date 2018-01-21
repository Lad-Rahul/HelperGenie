package com.example.lad.helpergenie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Pro on 1/19/2018.
 */

public class homeActivity extends Fragment {

    View mView;
    Spinner searchSP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_home,container,false);

        String[] listSP = {"Plumber" , "Electician" , "Carpenter" };
        searchSP = (Spinner)mView.findViewById(R.id.search_sp);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,listSP);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSP.setAdapter(arrayAdapter);

        return mView;
    }
}

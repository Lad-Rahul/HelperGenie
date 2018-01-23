package com.example.lad.helpergenie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Pro on 1/19/2018.
 */

public class homeActivity extends Fragment {

    View mView;
    Spinner searchSP;
    Spinner searchPin;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_home,container,false);

        String[] listSP = {"Plumber" , "Electician" , "Carpenter" };
        searchSP = (Spinner)mView.findViewById(R.id.search_sp);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,listSP);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSP.setAdapter(arrayAdapter);

        searchPin = (Spinner)mView.findViewById(R.id.search_PIN);

        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference().child("pincode");
        //final ArrayList<String> pincodeList = new ArrayList<>();

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    collectPincode((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mView;
    }

    private void collectPincode(Map<String,Object> users){

        ArrayList<Map> pinObjectList = new ArrayList<>();
        ArrayList<String> pinList = new ArrayList<>();

        for(Map.Entry<String,Object> entry : users.entrySet()){

            String SinglePin = (String)entry.getKey();
            Map SingleUser = (Map)entry.getValue();

            pinList.add(SinglePin);
            pinObjectList.add(SingleUser);
        }

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,pinList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchPin.setAdapter(arrayAdapter2);

    }
}

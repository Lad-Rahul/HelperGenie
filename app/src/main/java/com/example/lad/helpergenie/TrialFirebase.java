package com.example.lad.helpergenie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class TrialFirebase extends AppCompatActivity {

    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private ChildEventListener mChildevent;
    private TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_firebase);

        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference().child("users").child("user1");
        txt1 = (TextView)findViewById(R.id.trialtext1);


        ValueEventListener val = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FireUser fireUser = dataSnapshot.getValue(FireUser.class);
                String name = fireUser.getName();
                String email = fireUser.getEmail();
                String pincode = fireUser.getPincode();
                String address1 = fireUser.getAddress();
                String mobile = fireUser.getMobile();

                Log.e("Debugging : ","name is "+name);
                Log.e("Debugging : ","Email is "+email);
                Log.e("Debugging : ","Pincode is "+pincode);
                Log.e("Debugging : ","Address is "+address1);
                Log.e("Debugging : ","Mobile is "+mobile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addValueEventListener(val);
        /*mChildevent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){
                //String fireUserArr[] = new String [5];
                //for(int i = 0; i < 5; i++){
                //    fireUserArr[i] = dataSnapshot.getValue(String.class);
                //    Log.e("qqqqqqqqqqqqqqqqqqqqqqq",i+"th string is "+fireUserArr[i]);
                //}

                ArrayList<DataSnapshot> dataSnapshots = new ArrayList<DataSnapshot>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot x : children){
                    dataSnapshots.add(x);
                }
                Log.e("kjhkhjh","Count in 1st datasnapshots is "+dataSnapshots.get(0).getChildrenCount());
                Log.e("kjhkhjh","Count in 2nd datasnapshots is "+dataSnapshots.get(1).getChildrenCount());
                for (int y = 0; y < dataSnapshots.size() ; y++){
                    Log.e("jhjkuhh","Size is now "+y);
                }

                ArrayList<String> strs = new ArrayList<String>();
                for(DataSnapshot datas : dataSnapshots){
                    strs.add(datas.getValue(String.class));
                }

                if(!strs.isEmpty()) {
                    for (int p = 0; p < strs.size(); p++) {
                        Log.e("Debugging ", p + "th String is " + strs.get(p));
                    }
                }else{
                    Log.e("Sorry Bitch","Size is zero");
                }
                Log.e("Address is ",""+strs.get(0));
                //String str = dataSnapshot.getKey();
                //String name = fireUser.getName();
                //String email = fireUser.getEmail();
                //String pincode = fireUser.getPincode();
                //String address = fireUser.getAddress();
                //String mobile = fireUser.getMobile();

                //Log.e("Debugging : ","name is "+name);
                //Log.e("Debugging : ","Email is "+email);
                //Log.e("Debugging : ","Pincode is "+pincode);
                //Log.e("Debugging : ","Address is "+address);
                //Log.e("Debugging : ","Mobile is "+mobile);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mRef.addChildEventListener(mChildevent);*/
    }

    private int noOfSP(String str) {
        int count = 0;
        String strnew = str.replace("{","");
        count = str.length() - strnew.length();
        return count;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}

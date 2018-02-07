package com.example.lad.helpergenie;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class displaySP extends AppCompatActivity {


    String recName[],recID[],recEmail[],recMobile[],recRating[];
    int recordercomplete[];
    ArrayList<String> rec2Name = new ArrayList<>();
    ArrayList<String> rec2ID = new ArrayList<>();
    ArrayList<String> rec2Email = new ArrayList<>();
    ArrayList<String> rec2Mobile = new ArrayList<>();
    ArrayList<String> rec2Rating = new ArrayList<>();
    ArrayList<Integer> rec2ordercomplete = new ArrayList<>();
    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_display_sp);

        Bundle extra=getIntent().getExtras();
        ArrayList<String> ListSelSP=new ArrayList<>();
        ListSelSP =extra.getStringArrayList("Service providers selected");

        for(int i=0;i<ListSelSP.size();i++){
            Log.d("aa",ListSelSP.get(i).toString());
        }
        int temp2=ListSelSP.size();
        Log.d("hh",""+temp2);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        textView = (TextView)findViewById(R.id.Loading2);
        if(ListSelSP.size() == 0){
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText("Service unavailable");
        }
        else {

            mData = FirebaseDatabase.getInstance();

            for (int i = 0; i < ListSelSP.size(); i++) {

                final String temp = ListSelSP.get(i);
                mRef = mData.getReference().child("service-provider").child(temp);

                final int finalI = i;
                final ArrayList<String> finalListSelSP = ListSelSP;
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FireSP fireSP = dataSnapshot.getValue(FireSP.class);
                        rec2Name.add(fireSP.getName());
                        rec2Email.add(fireSP.getEmail());
                        rec2Mobile.add(fireSP.getMobile());
                        rec2Rating.add(fireSP.getRating());
                        rec2ID.add(temp);
                        rec2ordercomplete.add(fireSP.getOrdercomplete());

                        Log.d("test", "" + finalI + " " + rec2Name.get(finalI));

                        if (finalI == finalListSelSP.size() - 1) {
                            recName = new String[rec2Name.size()];
                            recMobile = new String[rec2Mobile.size()];
                            recEmail = new String[rec2Email.size()];
                            recRating = new String[rec2Email.size()];
                            recID = new String[rec2Name.size()];
                            recordercomplete = new int[rec2ordercomplete.size()];

                            Log.d("test", "" + "rec2name " + rec2Name.size());
                            for (int j = 0; j < rec2Name.size(); j++) {
                                recName[j] = rec2Name.get(j);
                                recEmail[j] = rec2Email.get(j);
                                recMobile[j] = rec2Mobile.get(j);
                                recRating[j] = rec2Rating.get(j);
                                recID[j] = rec2ID.get(j);
                                recordercomplete[j] = rec2ordercomplete.get(j);
                            }
                            Intent go = new Intent(displaySP.this, displaySPmain.class);
                            go.putExtra("names", recName);
                            go.putExtra("emails", recEmail);
                            go.putExtra("mobiles", recMobile);
                            go.putExtra("ratings", recRating);
                            go.putExtra("IDs", recID);
                            go.putExtra("ordercomplete", recordercomplete);
                            startActivity(go);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mRef.addValueEventListener(valueEventListener);
            }
        }
    }
}

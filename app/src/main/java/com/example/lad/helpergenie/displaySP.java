package com.example.lad.helpergenie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class displaySP extends AppCompatActivity {


    String recName[],recID[],recEmail[],recMobile[];
    ArrayList<String> rec2Name = new ArrayList<>();
    ArrayList<String> rec2ID = new ArrayList<>();
    ArrayList<String> rec2Email = new ArrayList<>();
    ArrayList<String> rec2Mobile = new ArrayList<>();
    private FirebaseDatabase mData;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sp);
        Bundle extra=getIntent().getExtras();
        ArrayList<String> ListSelSP=new ArrayList<>();
        ListSelSP =extra.getStringArrayList("Service providers selected");
        for(int i=0;i<ListSelSP.size();i++){
            Log.d("aa",ListSelSP.get(i).toString());
            //Toast.makeText(displaySP.this,ListSelSP.get(i).toString(),Toast.LENGTH_SHORT).show();
        }
        int temp2=ListSelSP.size();
        Log.d("hh",""+temp2);

        mData = FirebaseDatabase.getInstance();

        for(int i = 0; i<ListSelSP.size(); i++){

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
                    rec2ID.add(temp);

                    Log.d("test",""+ finalI + " " + rec2Name.get(finalI));

                    if(finalI == finalListSelSP.size() - 1){
                        recName = new String[rec2Name.size()];
                        recMobile = new String[rec2Mobile.size()];
                        recEmail = new String[rec2Email.size()];
                        recID = new String[rec2Name.size()];
                        Log.d("test",""+  "rec2name " + rec2Name.size());
                        for(int j=0;j < rec2Name.size();j++) {
                            recName[j] = rec2Name.get(j);
                            recEmail[j] = rec2Email.get(j);
                            recMobile[j] = rec2Mobile.get(j);
                            recID[j] = rec2ID.get(j);
                        }
                        Intent go = new Intent(displaySP.this,displaySPmain.class);
                        go.putExtra("names",recName);
                        go.putExtra("emails",recEmail);
                        go.putExtra("mobiles",recMobile);
                        go.putExtra("IDs",recID);
                        startActivity(go);
                }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRef.addValueEventListener(valueEventListener);
        }


//        recEmail = new String[]{"Email 1", "Email 2"};
//        recID = new String[]{"ID1", "ID2"};
//        recMobile = new String[]{"Mobile 1", "Mobile 2"};
//        recName = new String[]{"Namem 1", "Name 2"};
//        //String data[] = {"dsd","dwdw","dwd"};




    }



}

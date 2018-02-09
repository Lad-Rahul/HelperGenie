package com.example.lad.helpergenie;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class get_sp extends AppCompatActivity {

    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private String name,proffesion,email,mobile,SPpro;
    private int ordercomplete;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_get_sp);



        final ArrayList<String> ListSP2 = new ArrayList<>();
        ArrayList<String> ListSP = new ArrayList<>();
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                ListSP = extras.getStringArrayList("Service Provider at this Location");
                SPpro = extras.getString("Service Provider Proffesion").toString();
                //Toast.makeText(get_sp.this,SPpro,Toast.LENGTH_SHORT).show();
            }
        }

        for(int i=0;i<ListSP.size();i++){
            //Toast.makeText(get_sp.this,ListSP.get(i).toString(),Toast.LENGTH_SHORT).show();
        }


        mData = FirebaseDatabase.getInstance();

        for (int i = 0; i < ListSP.size(); i++) {
            final String tempSP = ListSP.get(i);
            mRef = mData.getReference().child("service-provider").child(tempSP);
            //Log.d("hello1", mData.getReference().child("service-provider").child(tempSP).child("name").toString());
            final int finalI = i;
            final ArrayList<String> finalListSP = ListSP;
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FireSP fireSP = dataSnapshot.getValue(FireSP.class);
                    name = fireSP.getName();
                    email = fireSP.getEmail();
                    mobile = fireSP.getMobile();
                    proffesion = fireSP.getProffesion();
                    ordercomplete = fireSP.getOrdercomplete();

//                    Log.d("hello1","name :" + name);
//                    Log.d("hello1","email :" + email);
//                    Log.d("hello1","mobile :" + mobile);
//                    Log.d("hello1","proffesion :" + proffesion);

                    if(proffesion.equals(SPpro)){
                        Log.d("hello1","name :" + name);
                        ListSP2.add(tempSP);
                        //ListSP2.add(fireSP);
                    }

                    if(finalI == finalListSP.size()-1){
                        Intent intent=new Intent(get_sp.this,displaySP.class);
                        intent.putExtra("Service providers selected",ListSP2);
                        startActivity(intent);
                        finish();
                    }
                    //FireSP i = new FireSP(email,mobile,name,proffesion);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRef.addValueEventListener(valueEventListener);
        }
    }
}

package com.example.lad.helpergenie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sp);

        ArrayList<String> ListSP = new ArrayList<>();
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                ListSP = extras.getStringArrayList("Service Provider at this Location");
                SPpro = extras.getString("Service Provider Proffesion");
                Toast.makeText(get_sp.this,SPpro,Toast.LENGTH_SHORT).show();
            }
        }

        for(int i=0;i<ListSP.size();i++){
            Toast.makeText(get_sp.this,ListSP.get(i).toString(),Toast.LENGTH_SHORT).show();
        }

        mData = FirebaseDatabase.getInstance();

        for (int i = 0; i < ListSP.size(); i++) {
            String tempSP = ListSP.get(i);
            mRef = mData.getReference().child("service-provider").child(tempSP);
            //Log.d("hello1", mData.getReference().child("service-provider").child(tempSP).child("name").toString());
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FireSP fireSP = dataSnapshot.getValue(FireSP.class);
                    name = fireSP.getName();
                    email = fireSP.getEmail();
                    mobile = fireSP.getMobile();
                    proffesion = fireSP.getProffesion();
//
//                    Log.d("hello1","name :" + name);
//                    Log.d("hello1","email :" + email);
//                    Log.d("hello1","mobile :" + mobile);
//                    Log.d("hello1","proffesion :" + proffesion);

                    FireSP i = new FireSP(email,mobile,name,proffesion);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRef.addValueEventListener(valueEventListener);
        }
    }
}

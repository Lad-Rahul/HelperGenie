package com.example.lad.helpergenie;

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
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sp);

        textView = (TextView)findViewById(R.id.textView2);

        final ArrayList<FireSP> ListSP2 = new ArrayList<>();
        ArrayList<String> ListSP = new ArrayList<>();
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                ListSP = extras.getStringArrayList("Service Provider at this Location");
                SPpro = extras.getString("Service Provider Proffesion").toString();
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

//                    Log.d("hello1","name :" + name);
//                    Log.d("hello1","email :" + email);
//                    Log.d("hello1","mobile :" + mobile);
//                    Log.d("hello1","proffesion :" + proffesion);

                    if(proffesion.equals(SPpro)){
                        ListSP2.add(fireSP);
                    }
                    //FireSP i = new FireSP(email,mobile,name,proffesion);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRef.addValueEventListener(valueEventListener);
        }
        FireSP temp;
        for(int i=0;i<ListSP2.size();i++){
            temp = ListSP2.get(i);
            name = temp.getName();
            email = temp.getMobile();
            mobile = temp.getMobile();
            proffesion = temp.getProffesion();

            textView.append(name + email + mobile + proffesion);
            Log.d("hello1",name + email + mobile + proffesion);
        }

    }
}

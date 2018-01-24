package com.example.lad.helpergenie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class get_sp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sp);

        ArrayList<String> ListSP = new ArrayList<>();
        String newString;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                newString = null;
            }else{
                ListSP = extras.getStringArrayList("Service Provider at this Location");
            }
        }

        for(int i=0;i<ListSP.size();i++){
            Log.d("patel",ListSP.get(i).toString());
            //Toast.makeText(get_sp.this,ListSP.get(i).toString(),Toast.LENGTH_SHORT).show();
        }

    }
}

package com.example.lad.helpergenie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class displaySP extends AppCompatActivity {

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
    }
}

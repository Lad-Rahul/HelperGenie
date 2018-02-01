package com.example.lad.helpergenie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class displaySPmain extends AppCompatActivity {

    RecyclerView rv;
    recyclerSP ra;

    String rec3Name[],rec3ID[],rec3Email[],rec3Mobile[],rec3Rating[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_spmain);

        Bundle extra=getIntent().getExtras();
        rec3ID = extra.getStringArray("IDs");
        rec3Mobile = extra.getStringArray("mobiles");
        rec3Email = extra.getStringArray("emails");
        rec3Name = extra.getStringArray("names");
        rec3Rating = extra.getStringArray("ratings");


        Log.e("Size","ID  "+rec3ID.length);
        Log.e("Size","EmailID "+rec3Email.length);
        Log.e("Size","Mobile "+rec3Mobile.length);
        Log.e("Size","Name  "+rec3Name.length);


        rv = (RecyclerView) findViewById(R.id.recyclerViewSP);

        ra = new recyclerSP(this,rec3Name,rec3Email,rec3Mobile,rec3ID);
        rv.setAdapter(ra);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}

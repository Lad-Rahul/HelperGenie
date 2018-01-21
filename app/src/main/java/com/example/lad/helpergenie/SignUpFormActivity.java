package com.example.lad.helpergenie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        EditText Fname = (EditText)findViewById(R.id.fname);
        EditText Fmobile = (EditText)findViewById(R.id.fmobile);
        EditText Fadd1 = (EditText)findViewById(R.id.fadd1);
        EditText Fadd2 = (EditText)findViewById(R.id.fadd2);
        EditText Fpin = (EditText)findViewById(R.id.fpin);

    }
}

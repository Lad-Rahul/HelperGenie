package com.example.lad.helpergenie;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUpFormActivity extends AppCompatActivity {

    private EditText Fmobile,Fadd1,Fadd2,Fpin;
    private TextView FnameText;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Please Fill This Form")
                .setMessage("In Order to Get your details you have to fill this details in given area")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog ad = builder.create();
        ad.show();

        mData = FirebaseDatabase.getInstance();
        //EditText Fname = (EditText)findViewById(R.id.fname);
        FnameText = (TextView)findViewById(R.id.fnameText);
        Fmobile = (EditText)findViewById(R.id.fmobile);
        Fadd1 = (EditText)findViewById(R.id.fadd1);
        Fadd2 = (EditText)findViewById(R.id.fadd2);
        Fpin = (EditText)findViewById(R.id.fpin);


        FnameText.setText("Current User : "+MainActivity.CurrUser);
    }

    public void sendData(View view) {
        String userEmail = MainActivity.MainCurrUserEmail;
        String userKey = userEmail.replace(".","");
        String userName = MainActivity.CurrUser;
        String mobile = Fmobile.getText().toString();
        String add1 = Fadd1.getText().toString();
        String add2 = Fadd2.getText().toString();
        String pin = Fpin.getText().toString();

        mRef = mData.getReference().child("users").child(userKey).child("address");
        mRef.setValue(add1);
        Toast.makeText(SignUpFormActivity.this,"Sending "+add1,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("address2");
        mRef.setValue(add2);
        Toast.makeText(SignUpFormActivity.this,"Sending "+add2,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("email");
        mRef.setValue(userEmail);
        Toast.makeText(SignUpFormActivity.this,"Sending "+userEmail,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("mobile");
        mRef.setValue(mobile);
        Toast.makeText(SignUpFormActivity.this,"Sending "+mobile,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("name");
        mRef.setValue(userName);
        Toast.makeText(SignUpFormActivity.this,"Sending "+userName,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("pincode");
        mRef.setValue(pin);
        Toast.makeText(SignUpFormActivity.this,"Sending "+pin,Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        AuthUI.getInstance().signOut(SignUpFormActivity.this);
        super.onBackPressed();
    }
}

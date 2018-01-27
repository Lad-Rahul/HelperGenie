package com.example.lad.helpergenie;


import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by LAD on 20-Jan-18.
 */

public class ProfileActivity extends Fragment {
    private TextView mAddressTextView3,mAddressTextView2;
    private TextView mEmailTextView;
    private TextView mMobileTextView;
    private TextView mPincodeTextView;
    private TextView mName;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private String name,email,pincode,address1,address2,mobile;
    private ImageView mProfileImage;
    private final int SELECTING_IMAGE = 100;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private Uri downloadPic;

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_profile,container,false);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Please Wait...");
        pd.setMessage("Fetching Your Profile...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();
        mAddressTextView3=(TextView)mView.findViewById(R.id.write_address3);
        mAddressTextView2=(TextView)mView.findViewById(R.id.write_address2);
        mEmailTextView=(TextView)mView.findViewById(R.id.write_email);
        mMobileTextView=(TextView)mView.findViewById(R.id.write_mobile);
        mPincodeTextView=(TextView)mView.findViewById(R.id.write_pincode);
        mName = (TextView)mView.findViewById(R.id.name);
        mProfileImage = (ImageView)mView.findViewById(R.id.edit);

        mData = FirebaseDatabase.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String currUserEmail = "";
        if (user != null) {
            // signed In
            currUserEmail = user.getEmail().toString().replace(".","").trim();
            Toast.makeText(getActivity(),"Current email is "+ currUserEmail +".",Toast.LENGTH_LONG).show();
        } else {
            // not signed in
        }
        //String strrrr =  "user1@usrcom";
        mRef = mData.getReference().child("users").child(currUserEmail);
        //mRef = mData.getReference().child("users").child(currUserEmail);

        ValueEventListener val = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FireUser fireUser = dataSnapshot.getValue(FireUser.class);
                name = fireUser.getName();
                Toast.makeText(getActivity(), "Username is "+name, Toast.LENGTH_SHORT).show();
                email = fireUser.getEmail();
                Toast.makeText(getActivity(), "Email is "+email, Toast.LENGTH_SHORT).show();
                pincode = fireUser.getPincode();
                Toast.makeText(getActivity(), "Pincode is "+pincode, Toast.LENGTH_SHORT).show();
                address1 = fireUser.getAddress();
                Toast.makeText(getActivity(), "Address1 is "+address1, Toast.LENGTH_SHORT).show();
                address2 = fireUser.getAddress2();
                Toast.makeText(getActivity(), "Address2 is "+address2, Toast.LENGTH_SHORT).show();
                mobile = fireUser.getMobile();
                Toast.makeText(getActivity(), "Mobile is "+mobile, Toast.LENGTH_SHORT).show();


                mEmailTextView.setText(email);
                mMobileTextView.setText(mobile);
                mPincodeTextView.setText(pincode);
                mName.setText(name);
                mAddressTextView3.setText(address1);
                mAddressTextView2.setText(address2);

                pd.cancel();

                Log.d("Debugging : ","name is "+name);
                Log.d("Debugging : ","Email is "+email);
                Log.d("Debugging : ","Pincode is "+pincode);
                Log.d("Debugging : ","Address1 is "+address1);
                Log.d("Debugging : ","Address2 is "+address2);
                Log.d("Debugging : ","Mobile is "+mobile);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addValueEventListener(val);

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECTING_IMAGE);
            }
        });
        return mView;
    }
}

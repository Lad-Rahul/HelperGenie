package com.example.lad.helpergenie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by LAD on 20-Jan-18.
 */

public class ProfileActivity extends Fragment {
    private TextView mAddressTextView;
    private TextView mEmailTextView;
    private TextView mMobileTextView;
    private TextView mPincodeTextView;

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_profile,container,false);
        mAddressTextView=(TextView)mView.findViewById(R.id.write_address);
        mEmailTextView=(TextView)mView.findViewById(R.id.write_email);
        mMobileTextView=(TextView)mView.findViewById(R.id.write_mobile);
        mPincodeTextView=(TextView)mView.findViewById(R.id.write_pincode);
        return mView;
    }
}

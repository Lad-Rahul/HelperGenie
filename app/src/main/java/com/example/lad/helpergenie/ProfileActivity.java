package com.example.lad.helpergenie;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


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
    public static CircleImageView mProfilePicture;
    //public static ImageView mProfilePicture;
    public final int SELECTING_IMAGE = 100;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public static Uri downloadPic;

    FirebaseStorage mStorageRef;
    StorageReference mPictures;

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_profile,container,false);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Please Wait...");
        getActivity().setTitle("Profile");
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
        mStorageRef = FirebaseStorage.getInstance();
        mPictures = mStorageRef.getReference().child("users_pic");

        mProfilePicture = (CircleImageView) mView.findViewById(R.id.profile_picture);

        //mProfilePicture = (ImageView) mView.findViewById(R.id.profile_picture);

        FirebaseUser user = auth.getCurrentUser();
        String currUserEmail = "";
        if (user != null) {
            // signed In
            currUserEmail = user.getEmail().toString().replace(".","").trim();
            //Toast.makeText(getActivity(),"Current email is "+ currUserEmail +".",Toast.LENGTH_LONG).show();
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
                //Toast.makeText(getActivity(), "Username is "+name, Toast.LENGTH_SHORT).show();
                email = fireUser.getEmail();
                //Toast.makeText(getActivity(), "Email is "+email, Toast.LENGTH_SHORT).show();
                pincode = fireUser.getPincode();
                //Toast.makeText(getActivity(), "Pincode is "+pincode, Toast.LENGTH_SHORT).show();
                address1 = fireUser.getAddress();
                //Toast.makeText(getActivity(), "Address1 is "+address1, Toast.LENGTH_SHORT).show();
                address2 = fireUser.getAddress2();
                //Toast.makeText(getActivity(), "Address2 is "+address2, Toast.LENGTH_SHORT).show();
                mobile = fireUser.getMobile();
                //Toast.makeText(getActivity(), "Mobile is "+mobile, Toast.LENGTH_SHORT).show();


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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECTING_IMAGE);
            }
        });

        mPictures.child(MainActivity.MainCurrUserEmail.replace(".","")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(mProfilePicture);
            }
        });
        mPictures.child(MainActivity.MainCurrUserEmail.replace(".","")).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Select Photo First", Toast.LENGTH_SHORT).show();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECTING_IMAGE);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getActivity(), "No Worry , You can upload it Later", Toast.LENGTH_SHORT).show();                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to add Profile Pictue?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });

        Toast.makeText(getActivity(), "Please Wait till Profile Photo is Being Loaded", Toast.LENGTH_LONG).show();
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        mStorageRef = FirebaseStorage.getInstance();
        mPictures = mStorageRef.getReference().child("users_pic");

        if (requestCode == SELECTING_IMAGE && resultCode == RESULT_OK){
            Log.d("Image Retrive","Selected Image");
            //Toast.makeText(getActivity(), " Succesfully Selected Image", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            final ProgressDialog pdd = new ProgressDialog(getActivity());
            pdd.setTitle("Uploading Image");
            pdd.setMessage("Please Wait....");
            pdd.show();
            StorageReference profilePic = mPictures.child(MainActivity.MainCurrUserEmail.replace(".",""));
            profilePic.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Image Retrive","Uploading Image");
                    pdd.dismiss();
                    //Toast.makeText(getActivity(), "Uploaded Image", Toast.LENGTH_SHORT).show();
                    Uri downloadPic = taskSnapshot.getDownloadUrl();
                    Log.d("Image Retrive","Uri is "+downloadPic);
                    //Glide.with(Fr).load(downloadPic).into(mProfilePicture);
                    Glide.with(getActivity()).load(downloadPic).into(mProfilePicture);
                    //Toast.makeText(getActivity(), "Sucessfully set Picture", Toast.LENGTH_SHORT).show();
                    Log.d("Image Retrive","Set Image Sucessfull");

                    ////////////////////Wprk in progress///////////////////////////////////
                    //////////////////////////////////////////////////////////////////////
                    ///////////////////ProfileActivity pf = new ProfileActivity(downloadPic);
                }

            });
        } else if (requestCode == SELECTING_IMAGE && resultCode == RESULT_CANCELED){
            //Toast.makeText(getActivity(), "Error Selecting Image", Toast.LENGTH_SHORT).show();
        }

    }
}

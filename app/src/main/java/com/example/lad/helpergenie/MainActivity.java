package com.example.lad.helpergenie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 1234;
    private ProgressDialog pd2;

    PrefManager prefManager;

    FragmentManager fragmentManager;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public static String CurrUser;
    public static String MainCurrUserEmail;
    public ImageView mProfileImage;
    NavigationView navigationView;
    public boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(MainActivity.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProfileImage = (ImageView)findViewById(R.id.edit);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "helpergenie@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                startActivity(emailIntent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager =  getSupportFragmentManager();


        if (!prefManager.isFirstTimeLaunch()) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new homeActivity()).commit();
            flag=false;
            Log.d("FirstTimePro","Is not Started");
        }else{
            prefManager.setFirstTimeLaunch(false);
            Log.d("FirstTimePro","Is Started");
            flag=true;
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new firsttime()).commit();
        }

        //navigationView.setCheckedItem(0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK){
            //Toast.makeText(MainActivity.this, " Succesfully Signed in", Toast.LENGTH_SHORT).show();
        }else if (requestCode == RC_SIGN_IN && resultCode == RESULT_CANCELED) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pd2 = new ProgressDialog(MainActivity.this);
        pd2.setCancelable(false);
        pd2.setMessage("Veryfying Your Data....\nMake sure you have active connection");
        pd2.setTitle("Veryfying");
        pd2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //pd2.show();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // signed In
            //Toast.makeText(MainActivity.this, "User EmailId is "+user.getEmail().toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, user.getDisplayName()+" is Logged in !", Toast.LENGTH_SHORT).show();
            CurrUser = user.getDisplayName();
            MainCurrUserEmail = user.getEmail().toString();
            //pd2.dismiss();
            checkFormAndSaveDetails(user.getEmail().toString().replace(".",""),user.getDisplayName());
        } else {
            // not signed in
            pd2.dismiss();
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);

        }
        if(!flag) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        //Only For DEVELOPMENT OF RETRIVEING DATA:::::::::::::::::::::::
        //Intent i1 = new Intent(this,TrialFirebase.class);
        //startActivity(i1);
    }

    private void checkFormAndSaveDetails(String userEmail,String userName) {
        FirebaseDatabase mData;
        DatabaseReference mRef;

        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference().child("users").child(userEmail);

        ValueEventListener val = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FireUser fireUser = dataSnapshot.getValue(FireUser.class);
                if (fireUser == null){
                    Intent i1 =  new Intent(MainActivity.this,SignUpFormActivity.class);
                    startActivity(i1);
                }else{

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addValueEventListener(val);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            if(navigationView.getMenu().getItem(0).isChecked() == false){
                fragmentManager =  getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new homeActivity()).commit();
                //fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new firsttime()).commit();
                navigationView.getMenu().getItem(0).setChecked(true);
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AuthUI.getInstance().signOut(this);
            //Toast.makeText(MainActivity.this, "Sucessfully LoggedOut", Toast.LENGTH_SHORT).show();
            onResume();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager =  getSupportFragmentManager();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new homeActivity()).commit();
        } else if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new ProfileActivity()).commit();
        } else if (id == R.id.nav_history) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new HistoryActivity()).commit();
        } else if (id == R.id.nav_help) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new HelpActivity()).commit();
        } else if (id == R.id.nav_logout) {
            AuthUI.getInstance().signOut(MainActivity.this);
            //Toast.makeText(MainActivity.this, "Sucessfully LoggedOut", Toast.LENGTH_SHORT).show();
            //finish();
            onResume();
        } else if (id == R.id.nav_share) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkPath = api.sourceDir;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("application/vnd.android.package-archive");
                Uri shareURI = Uri.fromFile(new File(apkPath));
                String str = shareURI.toString().replace("file","content");
                share.putExtra(Intent.EXTRA_STREAM,Uri.parse(str));
                startActivity(Intent.createChooser(share,"Share Using..."));
            }
            else {
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkPath = api.sourceDir;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("application/vnd.android.package-archive");
                share.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(apkPath)));
                startActivity(Intent.createChooser(share,"Share Using..."));
            }


            //Uri shareURI = new Uri(""+Uri.fromFile(new File(apkPath)).toString().replace("file","content"));
            //Uri shareURI = FileProvider.getUriForFile(this, "provyas.my.package.name.provider", new File(apkPath));
            //Uri shareURI = Uri.parse("content://"+apkPath);
            //share.putExtra(Intent.EXTRA_STREAM, shareURI);
            //share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            //share.putExtra(Intent.EXTRA_STREAM, shareURI);
            //startActivity(Intent.createChooser(share,"Share Using..."));

        }else if (id == R.id.nav_aboutus) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new about_us()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void copy(File src, File dst){
        try {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dst);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        }catch (IOException e){
            Log.d("Copying Your File", "Can not Copy");
        }

    }

    public boolean IsFirstTimeLaunced(){
        if (!prefManager.isFirstTimeLaunch2()) {
            return false;
        }
        else{
            prefManager.setFirstTimeLaunch2(false);
            return true;
        }
    }


}

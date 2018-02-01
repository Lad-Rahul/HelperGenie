package com.example.lad.helpergenie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 1234;
    private ProgressDialog pd2;

    FragmentManager fragmentManager;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public static String CurrUser;
    public static String MainCurrUserEmail;
    public ImageView mProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProfileImage = (ImageView)findViewById(R.id.edit);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new homeActivity()).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK){
            Toast.makeText(MainActivity.this, " Succesfully Signed in", Toast.LENGTH_SHORT).show();
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
        pd2.show();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // signed In
            Toast.makeText(MainActivity.this, "User EmailId is "+user.getEmail().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, user.getDisplayName()+" is Logged in !", Toast.LENGTH_SHORT).show();
            CurrUser = user.getDisplayName();
            MainCurrUserEmail = user.getEmail().toString();
            pd2.dismiss();
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
            super.onBackPressed();
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
            Toast.makeText(MainActivity.this, "Sucessfully LoggedOut", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(MainActivity.this, "Sucessfully LoggedOut", Toast.LENGTH_SHORT).show();
            onResume();
        } else if (id == R.id.nav_share) {
            ApplicationInfo api = getApplicationContext().getApplicationInfo();
            String apkPath = api.sourceDir;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("application/vnd.android.package-archive");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            startActivity(Intent.createChooser(share,"Start Using..."));

        }else if (id == R.id.nav_aboutus) {
            fragmentManager.beginTransaction().replace(R.id.alternatingLayout,new about_us()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

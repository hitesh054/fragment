package com.example.hitesh054.fragment.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hitesh054.fragment.FCM.Config;
import com.example.hitesh054.fragment.Fragment.HomeFragment;
import com.example.hitesh054.fragment.Fragment.user_myprofile;
import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.Models.Route;
import com.example.hitesh054.fragment.Session.SessionManagement;
import com.example.hitesh054.fragment.Fragment.fragment_route;
import com.example.hitesh054.fragment.Fragment.edit_myprofile_fragment;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;


public class user_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener   {
    public static final String Home_fragment = "home_fragment";
    public static final String user_myprofile = "my_profile_fragment";
    public static final String edit_myprofile_fragment = "edit_myprofile_fragment";
    public static final String RouteFragment = "Route_Fragment";

    public static  String fragmenttag;
    private Button logout;
    SessionManagement session;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private List<Route> RouteList = new ArrayList<>();

    LinearLayout linearLayout;
    private static FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        linearLayout=(LinearLayout)findViewById(R.id.clicked);
        fragmentManager = getSupportFragmentManager();



        session = new SessionManagement(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmenttag=Home_fragment;
        loadFragment(new HomeFragment());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);



        // row click listener
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) throws IOException {
//                Route route = RouteList.get(position);
             //   String topic=session.getTopic();
//                if(session.getTopic()!=null){
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic(session.getTopic());
//                }

//                for (Route r:RouteList) {
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic(r.getName());
//                }
//
//                    FirebaseMessaging.getInstance().subscribeToTopic(route.getName());
//                    session.setTopic(route.getName());


//                Toast.makeText(getApplicationContext(), route.getName() + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));



        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.profile_fragment);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(user_activity.this, userMapsActivity.class);
                startActivity(i);

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                session.logoutUser();
                finish();
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

//                Toast.makeText(context, "in broadcast receiver", Toast.LENGTH_SHORT).show();
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {



                    String message = intent.getStringExtra("message");
                   // try {
                     //   JSONObject jsonObject=new JSONObject(message);
                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_LONG).show();


//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

//                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
        FirebaseMessaging.getInstance().subscribeToTopic("busstand");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment HomeFragment = fragmentManager.findFragmentByTag(Home_fragment);
        Fragment myprofilefragment = fragmentManager.findFragmentByTag(user_myprofile);
        Fragment EditMyProfileFragment = fragmentManager.findFragmentByTag(edit_myprofile_fragment);
        Fragment Route_Fragment = fragmentManager.findFragmentByTag(RouteFragment);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(myprofilefragment != null)
        {
            ToHomeFragment();
        } else if(Route_Fragment != null)
        {
            ToHomeFragment();
        } else if(EditMyProfileFragment != null)
        {
            fragmentManager
                    .beginTransaction()

                    .replace(R.id.content_frame, new user_myprofile(), user_myprofile).commit();
        }
        else if(HomeFragment != null)
        {
            finish();
        }

        else {
            super.onBackPressed();
        }


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()

                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter,R.anim.slideup)
                    .replace(R.id.content_frame, fragment,fragmenttag)
                    .commit();
            return true;
        }

        return false;
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("token id", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.d("Firebase Reg Id: " , regId);
        else
            Log.d("firebase else","Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));



    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object

        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.profile:
                fragment = new user_myprofile();
                fragmenttag=user_myprofile;
                break;
            case R.id.route:
                fragment = new fragment_route();
                fragmenttag=RouteFragment;
                break;

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment,fragmenttag);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }


    // Handle navigation view item clicks here.

//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }


    public void ToHomeFragment()
    {
        fragmentManager
                .beginTransaction()

                .replace(R.id.content_frame, new HomeFragment(),
                        Home_fragment).commit();

    }
}



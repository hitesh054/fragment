package com.example.hitesh054.fragment.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.hitesh054.fragment.Fragment.DriverFragment;
import com.example.hitesh054.fragment.Fragment.GuestFragment;
import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.Session.SessionManagement;
import com.example.hitesh054.fragment.Fragment.UserFragment;
import com.example.hitesh054.fragment.Fragment.fragment_first;

//implement the interface OnNavigationItemSelectedListener in your activity class
public class MainActivity extends RuntimePermissionsActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static FragmentManager fragmentManager;
    public static final String GuestFragment = "guest_Fragement";
    public static final String fragmentfirst = "fragment_first";
    public static final String userFragment = "user_Fragement";
    public static final String driverFragment = "driver_Fragement";
    public static final String SignUpFragment = "Signup_Fragement";
    public static final String forgotpasswordfragment= "forgotpassword_fragment";


    BottomNavigationView navigation;
    public static  String fragmenttag;
    static  String temp="";
 public static final int REQUEST_PERMISSIONS=20;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        startPermission();

        SessionManagement sessionManagement=new SessionManagement(getApplicationContext());
        if(sessionManagement.isLoggedIn())
        {
            Intent i=null;

            if(sessionManagement.checkUserType().equals("user")) {
                i = new Intent(this, user_activity.class);
            }
            else if(sessionManagement.checkUserType().equals("guest")) {
                i = new Intent(this, GuestActivity.class);
            }
            else if(sessionManagement.checkUserType().equals("driver1")) {
                i = new Intent(this, driver_Activity.class);
            }

            startActivity(i);
            finish();
        }

        //loading the default fragment
        fragmenttag=fragmentfirst;
        loadFragment(new fragment_first());

        //getting bottom navigation view and attaching the listener
         navigation= findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }
    public void startPermission()
    {
        MainActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION},
                        R.string.Permission, REQUEST_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_guest:
                fragment = new GuestFragment();
                fragmenttag=GuestFragment;
                break;

            case R.id.navigation_user:
                fragment = new UserFragment();
                fragmenttag=userFragment;
                break;

            case R.id.navigation_driver:
                fragment = new DriverFragment();
                fragmenttag=driverFragment;
                break;


        }
        hidenavigationbar();

        return loadFragment(fragment);

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()

                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter,R.anim.slideup)
                    .replace(R.id.fragment_container, fragment,fragmenttag)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        Fragment GuestRegis_Fragment= fragmentManager.findFragmentByTag(GuestFragment);
        Fragment user_Fragment= fragmentManager.findFragmentByTag(userFragment);
        Fragment driver_Fragment= fragmentManager.findFragmentByTag(driverFragment);
        Fragment Signup_Fragment= fragmentManager.findFragmentByTag(SignUpFragment);
        Fragment forgotpassword_Fragment= fragmentManager.findFragmentByTag(forgotpasswordfragment);

        if (GuestRegis_Fragment != null)
        {
            replaceGuestRegistratiomFragment();
        }
        else if(user_Fragment!=null)
        {
            replaceGuestRegistratiomFragment();
        }
        else if(driver_Fragment!=null)
        {
            replaceGuestRegistratiomFragment();
        }
        else if (Signup_Fragment != null)
        {
            replaceGuestRegistratiomFragment();
        }
        else if ( forgotpassword_Fragment!= null)
        {
            if(fragmenttag.equals(driverFragment))
            {
                backtoDriverFragment();
            }
            if(fragmenttag.equals(userFragment))
            {
                backtoUserFragment();
            }
            if(fragmenttag.equals(GuestFragment))
            {
                backtoGuestFragment();
            }
        }

        else
            super.onBackPressed();

    }







    public void replaceGuestRegistratiomFragment()
    {
        fragmentManager
                .beginTransaction()

                .replace(R.id.fragment_container, new fragment_first(),
                        fragmentfirst).commit();
        shownavigationbar();
    }
    public void hidenavigationbar(){

        navigation.setVisibility(navigation.INVISIBLE);
    }
    public void shownavigationbar(){
        navigation.setVisibility(View.VISIBLE);
    }
//    public void replaceUserStartupFragment()
//    {
//        fragmentManager
//                .beginTransaction()
//
//                .replace(R.id.fragment_container, new fragment_first(),
//                        fragmentfirst).commit();
//    }
//    public void replaceDriverStartupFragment()
//    {
//        fragmentManager
//                .beginTransaction()
//
//                .replace(R.id.fragment_container, new fragment_first(),
//                        fragmentfirst).commit();
//    }



public void backtoDriverFragment()
{
    fragmentManager
            .beginTransaction()

            .replace(R.id.fragment_container, new DriverFragment(),
                    driverFragment).commit();
}
    public void backtoUserFragment()
    {
        fragmentManager
                .beginTransaction()

                .replace(R.id.fragment_container, new UserFragment(),
                        userFragment).commit();
    }
    public void backtoGuestFragment()
    {
        fragmentManager
                .beginTransaction()

                .replace(R.id.fragment_container, new GuestFragment(),
                        GuestFragment).commit();
    }

}

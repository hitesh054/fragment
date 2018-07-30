package com.example.hitesh054.fragment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hitesh054.fragment.R;
import com.example.hitesh054.fragment.Session.SessionManagement;

public class GuestActivity extends AppCompatActivity {

    private Button logout;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        session = new SessionManagement(this);



    }
    public void logoutButtonClicked(View v) {
        session.logoutUser();
               finish();
    }

}

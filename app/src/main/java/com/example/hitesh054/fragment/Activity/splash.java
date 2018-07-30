package com.example.hitesh054.fragment.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hitesh054.fragment.R;

public class splash extends AppCompatActivity {
    ImageView im;
    TextView tx1,tx2;
    Animation frombottom, fromtop;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        im = (ImageView)findViewById(R.id.imageView);
        tx1 = (TextView)findViewById(R.id.tx);
        tx2 = (TextView)findViewById(R.id.txv);
        frombottom=AnimationUtils.loadAnimation(this,R.anim.fromtop);
        tx1.setAnimation(frombottom);
        tx2.setAnimation(frombottom);
         fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
         im.setAnimation(fromtop);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(splash.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }



}


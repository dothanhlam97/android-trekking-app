package com.app.trekking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.trekking.R;

public class SplashActivity extends AppCompatActivity {

    ImageView imgSplash;
    TextView txtSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        imgSplash = (ImageView) findViewById(R.id.imgSplash);
        txtSplash = (TextView) findViewById(R.id.txtSplash);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);

        imgSplash.startAnimation(anim);
        txtSplash.startAnimation(anim);


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        };

        myThread.start();
    }
}
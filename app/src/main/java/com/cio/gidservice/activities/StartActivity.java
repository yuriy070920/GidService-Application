package com.cio.gidservice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cio.gidservice.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler h = new Handler();
        h.post(() -> getSupportActionBar().hide());
        setContentView(R.layout.splash_screen_layout);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.main_animation);
        findViewById(R.id.splash_logo).startAnimation(animation);


        Intent intent = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}

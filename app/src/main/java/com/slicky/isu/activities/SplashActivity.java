package com.slicky.isu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.slicky.isu.ActivityUtils;
import com.slicky.isu.activities.R;

public class SplashActivity extends AppCompatActivity {
    private ActivityUtils utils = ActivityUtils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        utils.removeActionBar(this);
        utils.removeToolBar(this);
    }

    public void continueClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.slicky.isu.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import com.slicky.isu.ActivityUtils;
import com.slicky.isu.R;

/**
 * Created by slicky on 9.1.2017
 */
public class MoreActivity extends AppCompatActivity {

    private TextView tvMore;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        tvMore = (TextView) findViewById(R.id.tvMore);
        utils = ActivityUtils.getInstance();

        setTitle();
        displayData();
    }

    private void setTitle() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String title = extras.getString("title");
        setTitle(title);
    }

    private void displayData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String text = extras.getString("text");
        utils.setHtmlText(tvMore, text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return true;
    }
}
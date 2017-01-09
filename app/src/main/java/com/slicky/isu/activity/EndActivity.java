package com.slicky.isu.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import com.slicky.decisiontree.End;
import com.slicky.isu.ActivityUtils;
import com.slicky.isu.R;

public class EndActivity extends AppCompatActivity {

    private TextView tvEnd;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        tvEnd = (TextView) findViewById(R.id.tvEnd);
        utils = ActivityUtils.getInstance();

        displayData();
    }

    private void displayData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        End end = (End) extras.get("end");
        Object[] flags = (Object[]) extras.get("flags");

        String text = end.getText();

        utils.setHtmlText(tvEnd, text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return true;
    }
}

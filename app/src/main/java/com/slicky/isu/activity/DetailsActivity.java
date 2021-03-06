package com.slicky.isu.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.slicky.isu.ActivityUtils;
import com.slicky.isu.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvDetails;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        tvDetails = (TextView) findViewById(R.id.tvDetails);
        utils = ActivityUtils.getInstance();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int typeID = extras.getInt("type_id");

        initTitle(typeID);
        initText(typeID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return true;
    }

    private void initTitle(int typeID) {
        Resources resources = getResources();
        String detailsForString = resources.getString(R.string.details_for);
        String typeString = resources.getString(typeID);
        String formattedString = String.format("%s %s", detailsForString, typeString);
        setTitle(formattedString);
    }

    private void initText(int typeID) {
        switch (typeID) {
            case R.string.sp:
                utils.setHtmlText(tvDetails, getString(R.string.sp_details));
                break;
            case R.string.doo:
                utils.setHtmlText(tvDetails, getString(R.string.doo_details));
                break;
            case R.string.dno:
                utils.setHtmlText(tvDetails, getString(R.string.dno_full));
                break;
            case R.string.dd:
                utils.setHtmlText(tvDetails, getString(R.string.dd_full));
                break;
        }
    }
}

package isu.test.slicky.com.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {

    ActivityUtils utils = ActivityUtils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        utils.removeActionBar(this);
    }
}
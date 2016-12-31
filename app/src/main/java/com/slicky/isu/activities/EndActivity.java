package com.slicky.isu.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.slicky.decisiontree.End;
import com.slicky.isu.R;

public class EndActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        tv = (TextView) findViewById(R.id.description_text);
        displayData();
    }

    private void displayData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        End end = (End) extras.get("end");
        Object[] flags = (Object[]) extras.get("flags");

        tv.setText(end.getText() /*+ "\nYou failed " + flags.length + " times!"*/);
        for (Object o : flags) {
            String flag = (String) o;
            System.out.println(flag);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else {
            return false;
        }
    }
}

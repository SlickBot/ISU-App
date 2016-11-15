package isu.test.slicky.com.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewView extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_view);

        tv = (TextView) findViewById(R.id.description_text);
        displayData();
    }

    private void displayData() {
        switch (getIntent().getStringExtra("type")) {
            case "sp":
                tv.setText(getResources().getString(R.string.sp_text));
                break;

            case "doo":
//                tv.setText(getResources().getString(R.string.doo_text));
                break;

            case "dd":
//                tv.setText(getResources().getString(R.string.dd_text));
                break;
        }
    }
}

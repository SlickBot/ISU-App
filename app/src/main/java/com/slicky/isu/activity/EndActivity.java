package com.slicky.isu.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.slicky.decisiontree.End;
import com.slicky.isu.ActivityUtils;
import com.slicky.isu.R;

import java.util.HashSet;
import java.util.Set;

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
        Set<String> flagSet = new HashSet<>();

        for (Object o : flags) {
            String flag = (String) o;
            flagSet.add(flag);
        }

        String text = end.getText() + "<br><br>";
        if (flagSet.contains("polni_sp"))
            text += getString(R.string.sp_polni) + "<br><br>";
        if (flagSet.contains("popoldanski_sp"))
            text += getString(R.string.sp_popoldanski) + "<br><br>";
        if (flagSet.contains("dig_potrdilo"))
            text += getString(R.string.sp_dig_potrdilo) + "<br><br>";
        if (flagSet.contains("opreme_sp"))
            text += getString(R.string.sp_oprema) + "<br><br>";
        if (flagSet.contains("ime_sp"))
            text += getString(R.string.sp_ime) + "<br><br>";
        if (flagSet.contains("sedez_sp"))
            text += getString(R.string.sp_sedez) + "<br><br>";
        if (flagSet.contains("kontaktni_sp"))
            text += getString(R.string.sp_kontaktni) + "<br><br>";
        if (flagSet.contains("soglasje_lastnika_sp"))
            text += getString(R.string.sp_soglasje_lastnika) + "<br><br>";
        if (flagSet.contains("dejavnosti_sp"))
            text += getString(R.string.sp_dejavnosti) + "<br><br>";
        if (flagSet.contains("dovoljenje_za_dejavnost_sp"))
            text += getString(R.string.sp_dovoljenje_za_dejavnost) + "<br><br>";
        if (flagSet.contains("zastopnik_sp"))
            text += getString(R.string.sp_zastopnik) + "<br><br>";
        if (flagSet.contains("prijava_v_davcni_register"))
            text += getString(R.string.sp_prijava_v_davcni_register) + "<br><br>";

                utils.setHtmlText(tvEnd, text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return true;
    }
}

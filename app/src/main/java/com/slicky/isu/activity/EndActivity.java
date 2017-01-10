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
        if (flagSet.contains("e_vem"))
            text += getString(R.string.e_vem) + "<br><br>";
        if (flagSet.contains("doo_enoosebni"))
            text += getString(R.string.doo_enoosebni) + "<br><br>";
        if (flagSet.contains("doo_vecosebni"))
            text += getString(R.string.doo_vecosebni) + "<br><br>";
        if (flagSet.contains("sp_polni"))
            text += getString(R.string.sp_polni) + "<br><br>";
        if (flagSet.contains("sp_popoldanski"))
            text += getString(R.string.sp_popoldanski) + "<br><br>";
        if (flagSet.contains("dig_potrdilo"))
            text += getString(R.string.dig_potrdilo) + "<br><br>";
        if (flagSet.contains("oprema"))
            text += getString(R.string.oprema) + "<br><br>";
        if (flagSet.contains("ime"))
            text += getString(R.string.ime) + "<br><br>";
        if (flagSet.contains("sedez"))
            text += getString(R.string.sedez) + "<br><br>";
        if (flagSet.contains("kontaktni"))
            text += getString(R.string.kontaktni) + "<br><br>";
        if (flagSet.contains("soglasje_lastnika"))
            text += getString(R.string.soglasje_lastnika) + "<br><br>";
        if (flagSet.contains("dejavnosti"))
            text += getString(R.string.dejavnosti) + "<br><br>";
        if (flagSet.contains("dovoljenje_za_dejavnost"))
            text += getString(R.string.dovoljenje_za_dejavnost) + "<br><br>";
        if (flagSet.contains("sp_zastopnik"))
            text += getString(R.string.sp_zastopnik) + "<br><br>";
        if (flagSet.contains("sp_prijava_v_davcni_register"))
            text += getString(R.string.sp_prijava_v_davcni_register) + "<br><br>";
        if (flagSet.contains("doo_izberi_zastopnika"))
            text += getString(R.string.doo_izberi_zastopnika) + "<br><br>";
        if (flagSet.contains("doo_zberi_podatke_zastopnika"))
            text += getString(R.string.doo_zberi_podatke_zastopnika) + "<br><br>";
        if (flagSet.contains("doo_pridobi_podatke"))
            text += getString(R.string.doo_pridobi_podatke) + "<br><br>";
        if (flagSet.contains("doo_pripravi_napoved"))
            text += getString(R.string.doo_pripravi_napoved) + "<br><br>";

                utils.setHtmlText(tvEnd, text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return true;
    }
}

package com.slicky.isu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.slicky.isu.R;
import com.slicky.isu.service.MyService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean shouldShowNotification = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String title = getResources().getString(R.string.welcome_screen_title2);
        setTitle(title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            // TIP DRUŽBE
            case R.id.sp:
                startDetails(R.string.sp);
                break;
            case R.id.doo:
                startDetails(R.string.doo);
                break;
            case R.id.dno:
                startDetails(R.string.dno);
                break;
            case R.id.dd:
                startDetails(R.string.dd);
                break;

            // OSTALO
            case R.id.share:
                break;

            case R.id.about:
                startAbout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void startQuestions(int resourceID) {
        Intent intent = new Intent(this, ChooseActivity.class);
        intent.putExtra("resource_id", resourceID);
        startActivity(intent);
    }

    private void startDetails(int typeID) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("type_id", typeID);
        startActivity(intent);
    }

    private void startAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void spClick(View view) {
        startQuestions(R.raw.sp_decision_tree);
    }

    public void dooClick(View view) {
        startQuestions(R.raw.example_decision_tree);
    }

    public void dnoClick(View view) {
        startQuestions(R.raw.example_decision_tree);
    }

    public void ddClick(View view) {
        startQuestions(R.raw.example_decision_tree);
    }

    public void toggleNotification(View view) {
        Intent intent = new Intent(this, MyService.class);
        if (shouldShowNotification) {
            stopService(intent);
            shouldShowNotification = false;
        } else {
            startService(intent);
            shouldShowNotification = true;
        }
    }
}

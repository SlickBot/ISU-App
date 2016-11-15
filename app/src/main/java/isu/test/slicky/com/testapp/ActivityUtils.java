package isu.test.slicky.com.testapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by SlickyPC on 6.11.2016
 */
class ActivityUtils {
    private static ActivityUtils instance = new ActivityUtils();

    static ActivityUtils getInstance() {
        return instance;
    }

    private ActivityUtils() {}

    void removeActionBar(AppCompatActivity activity) {
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null)
            bar.hide();
    }

    void removeToolBar(AppCompatActivity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}

package com.slicky.isu;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.slicky.isu.activity.ChooseActivity;

/**
 * Created by SlickyPC on 6.11.2016
 */
public class ActivityUtils {
    private static ActivityUtils instance = new ActivityUtils();

    private ActivityUtils() { }

    public static ActivityUtils getInstance() {
        return instance;
    }

    public void removeActionBar(AppCompatActivity activity) {
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null)
            bar.hide();
    }

    public void removeToolBar(AppCompatActivity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public int getColor(AppCompatActivity activity, int color_id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return activity.getResources().getColor(color_id, null);
        else
            return activity.getResources().getColor(color_id);
    }
}

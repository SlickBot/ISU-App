package com.slicky.isu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import com.slicky.isu.activity.MainActivity;

/**
 * Created by slicky on 3.1.2017
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    private NotifyingThread notifier = null;

    @Override
    public void onCreate() {
        Toast.makeText(this, TAG + " onCreate", Toast.LENGTH_SHORT).show();
        startNotifier();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, TAG + " onDestroy", Toast.LENGTH_SHORT).show();
        interruptNotifier();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,
                TAG + " onStartCommand, flags=" + flags + ", startID=" + startId,
                Toast.LENGTH_SHORT
        ).show();

        try {
            Bundle extras = intent.getExtras();
            boolean shouldRun = extras.getBoolean("shouldRun");
            // if this is end call
            if (!shouldRun) {
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                stopSelf();
            }
        } catch (NullPointerException ignored) { }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startNotifier() {
        interruptNotifier();
        notifier = new NotifyingThread(this);
        notifier.start();
    }

    private void interruptNotifier() {
        if (notifier != null)
            notifier.interrupt();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, TAG + " onBind", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, TAG + " onUnbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this, TAG + " onRebind", Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }
}

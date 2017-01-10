package com.slicky.isu.service;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.slicky.isu.R;
import com.slicky.isu.activity.SplashActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by slicky on 3.1.2017
 */
public class NotifyingThread extends Thread {
    private final MyService service;
    private final NotificationManager manager;
    private final int id = 784235748;
    private boolean shouldRun;

    NotifyingThread(MyService service) {
        this.service = service;
        manager = (NotificationManager) service.getSystemService(NOTIFICATION_SERVICE);
        shouldRun = true;
    }

    @Override
    public void run() {
        while (shouldRun) {
            createNotification();
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                shouldRun = false;
            }
        }
    }

    private void createNotification() {
        Intent resultIntent = new Intent(service.getApplicationContext(), MyService.class);
        resultIntent.putExtra("shouldRun", false);
        PendingIntent pendingIntent = PendingIntent.getService(
                service.getApplicationContext(), 0,
                resultIntent, 0);

        Notification.Builder builder = new Notification.Builder(service);
        builder.setSmallIcon(R.drawable.man_with_tie)
                .setContentIntent(pendingIntent)
                .setContentTitle(service.getString(R.string.app_name))
                .setContentText("Odpri")
                .setSubText("Klikni da odpreš!");

        Notification notification = builder.build();
        manager.notify(id, notification);
    }

    @Override
    public void interrupt() {
        shouldRun = false;
        manager.cancel(id);
    }
}

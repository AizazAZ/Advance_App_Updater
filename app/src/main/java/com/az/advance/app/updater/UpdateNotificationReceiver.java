package com.az.advance.app.updater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateNotificationReceiver extends BroadcastReceiver {
    private UpdateCheck instance = null;

    public UpdateNotificationReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if (instance == null) instance = UpdateCheck.getInstance();
        try {
            String action = intent.getAction();
            if (action.equals(UpdateCheck.ACTION_DOWNLOAD_CANCELLED)) {

                try {
                    instance.cancelDownload();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (action.equalsIgnoreCase(UpdateCheck.ACTION_NOTIFICATION_REMOVED)) {

                instance.cancelNotificationAndUpdateRunnable();

            } else if (action.equals(UpdateCheck.ACTION_DOWNLOAD_UPDATE)) {

                instance.cancelNotification();
                instance.createDownloadingUpdateNotification();
                instance.downloadUpdate();

            } else if (action.equals(UpdateCheck.ACTION_UPDATE_DOWNLOADED)) {
                instance.cancelNotification();
                instance.installUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

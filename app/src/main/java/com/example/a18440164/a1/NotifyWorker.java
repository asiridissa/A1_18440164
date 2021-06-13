package com.example.a18440164.a1;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Date;

//Workder for reminder notification
public class NotifyWorker extends Worker {
    //Notification channel id
    private final String CHANNEL_ID = String.valueOf(R.string.channnel_id);

    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = this.getApplicationContext();
        //Get values from passed parameters
        int id = getInputData().getInt(context.getString(R.string.notification_id), 0);
        String title = getInputData().getString(context.getString(R.string.notification_title));
        String detail = getInputData().getString(context.getString(R.string.notification_detail));
        long time = getInputData().getLong(context.getString(R.string.notification_time), 0);

        //build and show notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
                .setContentTitle(title + "@" + new Date(time).toString())
                .setContentText(detail)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(new Date(time).toLocaleString()).setSummaryText(detail))
                .setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, builder.build());
        return Result.success();
    }
}

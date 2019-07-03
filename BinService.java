package com.example.priti.smartbin;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import java.util.concurrent.TimeUnit;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class BinService extends IntentService {
    private static final String TAG = "BinService";
    private static final String CHANNEL_ID= "my_channel_01";
    private static final int NOTIFICATION_ID= 0;
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);
    public static final String SERVER_ID="0";

    public static void setServiceAlarm(Context context, boolean isOn) {
       Intent i=BinService.newIntent(context);
        PendingIntent toastAlarmIntent = PendingIntent.getService(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i(TAG,"INSIDE set repeating");
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime()+POLL_INTERVAL_MS,toastAlarmIntent);
            }


        } else {
            alarmManager.cancel(toastAlarmIntent);
            toastAlarmIntent.cancel();
            Log.i(TAG,"Alarm cancelled");
        }


    }

    public static Intent newIntent(Context context) {
        return new Intent(context, BinService.class);
    }

    public BinService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        int garbageLevel = repeatingTask();

        Log.i(TAG, "Received an intent " + intent);
        if(garbageLevel < QueryPreferences.getGarbageLevelLimit(this)) {
            Log.i(TAG,String.valueOf("query"+QueryPreferences.getGarbageLevelLimit(this)));


            Resources resources = getResources();
            Intent i = Main2Activity.newIntent(this);
            PendingIntent pi = PendingIntent
                    .getActivity(this, 0, i, 0);
            createNotificationChannel();
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notify_warning)
                    .setContentTitle(resources.getString(R.string.new_content_title))
                    .setContentText(resources.getString(R.string.new_content_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setVisibility(VISIBILITY_PUBLIC)
                    .build();

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
        setServiceAlarm(this,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroyed an service ");
    }

    private static int repeatingTask() {
        SensorParsingValues sensorValue = new SensorParsingValues();
        JsonParsingSB jp = new JsonParsingSB();
        sensorValue = jp.fetchItems( SERVER_ID);
        Log.i(TAG, sensorValue.getGarbageLevel());
        return Integer.parseInt(sensorValue.getGarbageLevel());

    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}

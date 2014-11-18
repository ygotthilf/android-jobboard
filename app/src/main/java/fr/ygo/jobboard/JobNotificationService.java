package fr.ygo.jobboard;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import fr.ygo.jobboard.data.Job;
import fr.ygo.jobboard.data.JobApi;

/**
 * Created by Yoann on 17/11/2014.
 */
public class JobNotificationService extends IntentService {

    private final static String TAG = "JobNotificationService";

    public JobNotificationService() {
        super("JobNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.getBoolean("pref_key_background", true)) {

            Log.d(TAG, "Service is running...");

            int count = 0;

            try {
                List<Job> jobs = JobApi.getJobs(this);
                count = jobs.size();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (count > 0) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("New jobs found for you")
                        .setContentText(count + " jobs found")
                        .setAutoCancel(true);

                // Creates an explicit intent for the main Activity
                Intent resultIntent = new Intent(this, JobListActivity.class);
                builder.setContentIntent(PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT));

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // Id allows you to update the notification later on.
                notificationManager.notify(12, builder.build());
            } else {
                Log.d(TAG, "No job found");
            }

            Intent serviceIntent = new Intent(this, JobNotificationService.class);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 10 * 1000,
                    PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }
}

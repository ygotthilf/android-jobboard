package fr.ygo.jobboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Yoann on 18/11/2014.
 */
public class JobNotificationReceiver extends BroadcastReceiver {

    private final static String TAG = "JobNotificationReceiver";

    @Override
    public void onReceive(Context context, Intent receive) {
        Log.d(TAG, "Event received");
        Intent intent=new Intent(context, JobNotificationService.class);
        context.startService(intent);
    }
}

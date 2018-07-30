package com.example.hitesh054.fragment.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.hitesh054.fragment.Activity.driver_Activity;
import com.example.hitesh054.fragment.R;

import static com.example.hitesh054.fragment.FCM.Config.NOTIFICATION_ID;

/**
 * Created by hitesh054 on 26-05-2018.
 */

public class DisplayNotification implements Runnable {

    Context mContext;
    NotificationManager mNotificationManager;


    public DisplayNotification(Context mContext) {
        this.mContext = mContext;
        mNotificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void run() {
        makeNotification(mContext);
    }

    private void makeNotification(Context context) {
        Intent intent = new Intent(context, driver_Activity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Route id=")
                .setContentText("Sample Notification Content")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_navigation_black_24dp)

                ;
        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else {
            n = builder.getNotification();
        }
        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(NOTIFICATION_ID, n);
    }
}

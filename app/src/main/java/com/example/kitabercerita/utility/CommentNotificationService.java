package com.example.kitabercerita.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.kitabercerita.HomeActivity;
import com.example.kitabercerita.PostDetailActivity;
import com.example.kitabercerita.R;
import com.example.kitabercerita.model.Comment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class CommentNotificationService {

    public static void makeCommentNotification(String description, String postId, Context context){
        Log.d("NotificationService", "successfully land in method");
            String chId = "commentNotif";
            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, chId);
            notifBuilder.setSmallIcon(R.drawable.logo);
            notifBuilder.setContentTitle("Your post has received a new comment!");
            notifBuilder.setContentText(description);
            notifBuilder.setAutoCancel(true);
            notifBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

            //TODO: Rusak, dunno why
//            Intent i = new Intent(context, PostDetailActivity.class);
//            i.putExtra("postId", postId);
//            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_MUTABLE);
//            notifBuilder.setContentIntent(pi);

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel nc = nm.getNotificationChannel(chId);
                if(nc == null){
                    nc = new NotificationChannel(chId, "commentNotification", NotificationManager.IMPORTANCE_HIGH);
                    nm.createNotificationChannel(nc);
                }
            }
            //TODO: Can only display latest comments
//            Integer randomId = new Random().nextInt();
            nm.notify(0, notifBuilder.build());
    }
}

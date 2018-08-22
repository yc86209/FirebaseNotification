package com.company.mynotificantion;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.Set;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("message", Objects.requireNonNull(remoteMessage.getNotification()).getBody());
        Set<String> keys = remoteMessage.getData().keySet();
        for(String s:keys) {
            Log.e("fcm data:", remoteMessage.getData().get(s));
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("FCM", "onNewToken token:" + s);
    }
}

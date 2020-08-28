package com.example.firstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Push", "Test");
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent("fcm");
        broadcastManager.sendBroadcast(intent);
        /*((Activity)getBaseContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyFunction obj = new MyFunction();
                obj.showCustomDialog(getApplicationContext());
            }
        });*/
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("Token", String.format("onNewToken: %s", s));
    }
}

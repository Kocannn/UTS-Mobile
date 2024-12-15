package com.example.uts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskDeadlineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String task = intent.getStringExtra("task");
        NotificationHelper.showNotification(context, "To-Do APP", "Deadline dari \"" + task + "\" sudah dekat uyyy.");
    }
}
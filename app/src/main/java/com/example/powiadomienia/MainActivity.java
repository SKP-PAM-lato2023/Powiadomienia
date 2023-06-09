package com.example.powiadomienia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "1";
    protected static int id = 1;
    protected static String KLUCZ = "JAkiś klucz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tworze kanal
        createNotificationChannel();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity2.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                        android.R.drawable.sym_action_chat, "Otwórz", pendingIntent
                ).build();

                // powiadomienie
                Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Powiadomienie")
                        .setContentText("Treść powiadomienia")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .addAction(action)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(id, notification);

            }

        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                String[] zdarzenia = {"Zdarzenie 1", "Zdarzenie 2", "Zdarzenie 3"};
                for(String s: zdarzenia)
                    inboxStyle.addLine(s);

                Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Powiadomienie")
                        .setContentText("Wiadmość powiadomień")
                        .setStyle(inboxStyle)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(2, notification);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String odpowiedz = "tutaj wpisz swoja odpowiedz";
                RemoteInput remoteInput = new RemoteInput.Builder(KLUCZ)
                        .setLabel(odpowiedz)
                        .build();
                Intent intent = new Intent(getBaseContext(), MainActivity2.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Action replayAction = new NotificationCompat.Action.Builder(
                        android.R.drawable.ic_dialog_info, "Odpowiedz", pendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

                Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Powiadomienie")
                        .setContentText("Treść powiadomienia")
                        .addAction(replayAction)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, notification);
            }
        });
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        String description = "kanal notyfikacji";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
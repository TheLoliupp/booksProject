package com.example.books;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    Button opisButton, dodajButton, przypomnijButton;
    TextView addedToListTextView;
    boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }


        opisButton = findViewById(R.id.opis);
        dodajButton = findViewById(R.id.message);
        przypomnijButton = findViewById(R.id.message1);
        addedToListTextView = findViewById(R.id.addedToListTextView);

        opisButton.setOnClickListener(v -> showNotification("Moja Książka", "Krótki opis: Ekscytująca historia pełna zwrotów akcji."));

        dodajButton.setOnClickListener(v -> {
            isAdded = !isAdded;
            if (isAdded) {
                dodajButton.setText("USUŃ Z CHCĘ PRZECZYTAĆ");
                addedToListTextView.setVisibility(View.VISIBLE);
            } else {
                dodajButton.setText("DODAJ DO CHCĘ PRZECZYTAĆ");
                addedToListTextView.setVisibility(View.GONE);
            }
        });


        przypomnijButton.setOnClickListener(v -> showNotification("Moja Książka", "Pamiętaj, aby znaleźć czas na lekturę!"));
    }


    private void showNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("book_channel", "Book Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, "book_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .build();

        manager.notify((int) System.currentTimeMillis(), notification);
    }
}

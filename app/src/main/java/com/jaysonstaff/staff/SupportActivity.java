package com.jaysonstaff.staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        ImageView imageViewFacebook = findViewById(R.id.imageViewFacebook);
        imageViewFacebook.setOnClickListener(v -> {
            String facebookUrl = "https://www.facebook.com/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
            startActivity(intent);
        });

        ImageView imageViewZalo = findViewById(R.id.imageViewZalo);
        imageViewZalo.setOnClickListener(v -> {
            String zaloUrl = "https://chat.zalo.me/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(zaloUrl));
            startActivity(intent);
        });

        ImageView imageViewTelegram = findViewById(R.id.imageViewTelegram);
        imageViewTelegram.setOnClickListener(v -> {
            String telegramUrl = "https://telegram.me/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl));
            startActivity(intent);
        });

        ImageView imageViewMessenger = findViewById(R.id.imageViewMessenger);
        imageViewMessenger.setOnClickListener(v -> {
            String messengerUrl = "https://www.messenger.com/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(messengerUrl));
            startActivity(intent);
        });

    }
}

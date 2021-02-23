package com.example.scratch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.*;


public class start_activity extends AppCompatActivity {

    Button quit, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_layout);

        quit = findViewById(R.id.quit_button);
        start = findViewById(R.id.start_button);

        start.setOnClickListener(v -> {
            startActivity(new Intent(this, question_activity.class));
            soundStart("start");
        });
        quit.setOnClickListener(v -> {
            soundStart("quit");
            finishAffinity();
            System.exit(0);
        });
    }

    private void soundStart(String file){
        MediaPlayer mp = new MediaPlayer();
        if (file.equals("start")) {
            mp = MediaPlayer.create(this, R.raw.start_sound);
        }else if(file.equals("quit")){
            mp = MediaPlayer.create(this, R.raw.quit_sound);
        }else {
            mp = MediaPlayer.create(this, R.raw.clicksounds);
        }
        mp.start();
    }
}
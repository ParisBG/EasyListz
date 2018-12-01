package com.figurativefootball.silentmodetoggle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.media.AudioManager;
import android.widget.FrameLayout;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    AudioManager audioManager;
    FrameLayout content;
    ImageView imageView;
    TextView textViewToggle;

    public final static String TAG = "Silent Mode Toggle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Silent Toggle App", "THIS IS A SEX!");
        Log.d(TAG, "STILL FUCKING TESTING");

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        content = findViewById(R.id.content);

        content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RingerHelper.performToggle(audioManager);

                updateUI();
            }
        });
    }

    private void updateUI() {
        imageView = findViewById(R.id.on_icon);
        textViewToggle = findViewById(R.id.textViewToggle);

        int phoneImage = RingerHelper.isPhoneSilent(audioManager)
               ? R.drawable.sound_off
               :R.drawable.sound_on;

        if (phoneImage == R.drawable.sound_off) {
            textViewToggle.setTextColor(Color.RED);
            textViewToggle.setText("YOUR PHONE'S RINGER IS TURNED OFF!");
    } else {
            textViewToggle.setTextColor(Color.GREEN);
            textViewToggle.setText("YOUR PHONE'S RINGER IS TURNED ON!");
        }

        imageView.setImageResource(phoneImage);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}

package com.example.nordcurrentgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int startBallSpeed;
    public static int score;
    public static int lives;
    public static int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBallSpeed = 8;
        score = 0;
        lives = 3;
        level = 1;

        TextView textView = findViewById(R.id.helloTextView);
        textView.setTypeface(null, Typeface.BOLD);
    }

    public void buttonStartGameClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}

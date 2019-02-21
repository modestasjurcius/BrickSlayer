package com.example.nordcurrentgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import com.example.nordcurrentgame.Classes.BallClass;
import com.example.nordcurrentgame.Classes.BrickClass;
import com.example.nordcurrentgame.Classes.SettingsClass;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.log;

public class GameActivity extends AppCompatActivity {

    //Imageviews
    public ImageView playerPaddle;
    public ImageView lifeImageView1;
    public ImageView lifeImageView2;
    public ImageView lifeImageView3;

    //TextViews
    public TextView scoreTextView;

    //layouts
    public ConstraintLayout container;
    public GridLayout brickField;
    public GridLayout livesLayout;

    //timers
    private Timer ballTimer;

    //class objects
    public BrickClass brick;
    public BallClass playerBall;
    public SettingsClass settings;

    //booleans
    public boolean isGameOver;

    //ints
    public int score;
    int lives;
    //public Handler mHandler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        brickField = findViewById(R.id.brickField);

        lives = 3;
        isGameOver = false;

        lifeImageView1 = new ImageView(this);
        lifeImageView1.setImageResource(R.drawable.ball);
        lifeImageView1.setScaleType(ImageView.ScaleType.FIT_XY);

        lifeImageView2 = new ImageView(this);
        lifeImageView2.setImageResource(R.drawable.ball);
        lifeImageView2.setScaleType(ImageView.ScaleType.FIT_XY);

        lifeImageView3 = new ImageView(this);
        lifeImageView3.setImageResource(R.drawable.ball);
        lifeImageView3.setScaleType(ImageView.ScaleType.FIT_XY);

        livesLayout = findViewById(R.id.livesLayout);
        livesLayout.removeAllViews();

        lifeImageView1.setLayoutParams(new android.view.ViewGroup.LayoutParams(15,15));
        lifeImageView2.setLayoutParams(new android.view.ViewGroup.LayoutParams(15,15));
        lifeImageView3.setLayoutParams(new android.view.ViewGroup.LayoutParams(15,15));

        livesLayout.addView(lifeImageView1, 0);
        livesLayout.addView(lifeImageView2, 1);
        livesLayout.addView(lifeImageView3, 2);

        brick = new BrickClass(this);
        playerPaddle = (ImageView) findViewById(R.id.paddle);
        container = (ConstraintLayout) findViewById(R.id.container);

        score = 0;
        playerPaddle.setX(0);

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    break;
                    case MotionEvent.ACTION_MOVE: {
                        playerPaddle.setX(event.getX() - playerPaddle.getWidth()/2);
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                    break;
                    default:
                        break;
                }
                return true;
            }
        });

        playerBall = new BallClass(this);
        settings = new SettingsClass();
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score : " + score);

        //Start ball timer
        ballTimer = new Timer();
        ballTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();


            }
        },0,20);
    }


    private void TimerMethod()
    {
        int x = playerBall.moveBall();
        if(x >= 0) {
            score += settings.gamePoints;
            runOnUiThread(ChangeScoreText);
        }
    }


    private Runnable ChangeScoreText = new Runnable() {
        @Override
        public void run() {

            //score += settings.gamePoints;
            scoreTextView.setText("Score : " + score);
            scoreTextView.invalidate();
            if(playerBall.isRemoveLife){
                if(lives == 3) {
                    livesLayout.removeViewInLayout(lifeImageView3);
                    //((ViewManager)livesLayout.getParent()).removeView(lifeImageView);
                    lives -= 1;
                }

                else if(lives == 2) {
                    livesLayout.removeViewInLayout(lifeImageView2);
                    lives -= 1;
                }
                else isGameOver = true;
                playerBall.isRemoveLife=false;
            }
            //View brick = brickField.getChildAt(x);
            //tempImg.setImageResource(R.drawable.ball);
            //brickField.removeViewInLayout(brick);
            //((ViewManager)brick.getParent()).view;
            //brickField.addView(tempImg, x);
            //brick.setBackgroundColor(Color.TRANSPARENT);
            //brick.setSystemUiVisibility(View.GONE);
            //brick.setScaleX(0);
            //brick.setScaleY(0);
        }
    };







        public void log()
        {
            String msg = "kon to-----apaciuo playerPaddle";
            Logger log = Logger.getLogger("{Zdarowa}");
            log.log(Level.INFO,msg);
        }
    public void log(int x)
    {
        String msg = "kon to == " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
    public void log(float x)
    {
        String msg = "kon to = " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
    public void log(String x)
    {
        String msg = "kon to == " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
}

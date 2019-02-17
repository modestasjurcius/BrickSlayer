package com.example.nordcurrentgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.nordcurrentgame.Classes.BallClass;
import com.example.nordcurrentgame.Classes.BrickClass;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.log;

public class GameActivity extends AppCompatActivity {

    //Imageviews
    public ImageView playerPaddle;

    //layouts
    public ConstraintLayout container;

    //timers
    private Timer ballTimer;

    //class objects
    public BrickClass brick;
    public BallClass playerBall;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        brick = new BrickClass(this);
        playerPaddle = (ImageView) findViewById(R.id.paddle);
        container = (ConstraintLayout) findViewById(R.id.container);


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

        //Start ball timer
        ballTimer = new Timer();
        ballTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                playerBall.moveBall();
            }
        },0,20);
    }






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
}

package com.example.nordcurrentgame;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nordcurrentgame.Classes.BallClass;
import com.example.nordcurrentgame.Classes.BrickClass;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameActivity extends AppCompatActivity {

    public ImageView playerPaddle;
    public ConstraintLayout container;

    @SuppressLint("ClickableViewAccessibility")
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BrickClass brick = new BrickClass(this);

        playerPaddle = (ImageView) findViewById(R.id.paddle);
        container = (ConstraintLayout) findViewById(R.id.container);

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x, dx;

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

        BallClass playerBall = new BallClass(this);

    }

        public void log()
        {
            String msg = "kon to";
            Logger log = Logger.getLogger("{Zdarowa}");
            log.log(Level.INFO,msg);
        }

        public void addBricks()
        {
            //for(int i)
        }
}

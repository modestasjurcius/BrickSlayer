package com.example.nordcurrentgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.graphics.Color;
import android.widget.TextView;

import com.example.nordcurrentgame.Classes.BallClass;
import com.example.nordcurrentgame.Classes.BrickClass;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    //Imageviews
    public ImageView playerPaddle;
    public ImageView lifeImageView1;
    public ImageView lifeImageView2;
    public ImageView lifeImageView3;

    //TextViews
    public TextView scoreTextView;
    public TextView gameOverText;
    public TextView levelTextView;

    //layouts
    public ConstraintLayout container;
    public GridLayout brickField;
    public GridLayout livesLayout;

    //timers
    private Timer ballTimer;

    //class objects
    public BrickClass brick;
    public BallClass playerBall;

    //booleans
    public boolean isGameOver;

    //ints
    int ballSpeed;

    //buttons
    public Button buttonRetry;
    public Button buttonExit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //booleans sets
        isGameOver = false;

        //class objects and sets
        playerBall = new BallClass(this);
        playerBall.startSpeed = ballSpeed;
        brick = new BrickClass(this);

        //ImageView sets
        lifeImageView1 = new ImageView(this);
        lifeImageView1.setImageResource(R.drawable.ball);
        lifeImageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        lifeImageView1.setPadding(5,0,0,0);

        lifeImageView2 = new ImageView(this);
        lifeImageView2.setImageResource(R.drawable.ball);
        lifeImageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        lifeImageView2.setPadding(5,0,0,0);

        lifeImageView3 = new ImageView(this);
        lifeImageView3.setImageResource(R.drawable.ball);
        lifeImageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        lifeImageView3.setPadding(5,0,0,0);

        lifeImageView1.setLayoutParams(new android.view.ViewGroup.LayoutParams(20,20));
        lifeImageView2.setLayoutParams(new android.view.ViewGroup.LayoutParams(20,20));
        lifeImageView3.setLayoutParams(new android.view.ViewGroup.LayoutParams(20,20));

        playerPaddle = findViewById(R.id.paddle);
        playerPaddle.setX(0);

        //textviews and sets
        gameOverText = findViewById(R.id.gameOverText);
        gameOverText.setTextSize(53);
        gameOverText.setTextColor(Color.RED);

        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score : " + MainActivity.score);

        levelTextView = findViewById(R.id.levelTextView);
        levelTextView.setText("Level : " + MainActivity.level);

        //buttons
        buttonRetry=findViewById(R.id.buttonRetry);
        buttonRetry.setVisibility(View.INVISIBLE);

        buttonExit=findViewById(R.id.buttonExit);
        buttonExit.setVisibility(View.INVISIBLE);

        //layouts
        brickField = findViewById(R.id.brickField);
        container = findViewById(R.id.container);
        livesLayout = findViewById(R.id.livesLayout);
        livesLayout.removeAllViews();

        if(MainActivity.lives > 0)
        livesLayout.addView(lifeImageView1, 0);
        if(MainActivity.lives > 1)
        livesLayout.addView(lifeImageView2, 1);
        if(MainActivity.lives > 2)
        livesLayout.addView(lifeImageView3, 2);

        //touch movement
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

        //Start ball timer
        ballTimer = new Timer();
        ballTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                TimerMethod();
            }
        },500,30);
    }


    private void TimerMethod()
    {
        playerBall.moveBall();

        if(playerBall.isRemoveLife){

            if(MainActivity.lives == 3) {
                livesLayout.removeViewInLayout(lifeImageView3);
                MainActivity.lives -= 1;
            }

            else if(MainActivity.lives == 2) {
                livesLayout.removeViewInLayout(lifeImageView2);
                MainActivity.lives -= 1;
            }
            else {
                isGameOver = true;
                livesLayout.removeViewInLayout(lifeImageView1);
            }

            playerBall.isRemoveLife=false;
        }

        if(playerBall.isBrickCollision) {
            MainActivity.score += 10;
            playerBall.isBrickCollision = false;
            runOnUiThread(ChangeScoreText);
        }

        if (playerBall.isOtherLevel) {
            ballTimer.cancel();
            buttonRetryGameClick(container);
        }

        if(isGameOver) {
            ballTimer.cancel();
            runOnUiThread(gameOver);
        }
    }


    private Runnable ChangeScoreText = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                scoreTextView.setText("Score : " + MainActivity.score);
                scoreTextView.invalidate();
                Thread.currentThread().interrupt();
            });
        }
    };

    private Runnable gameOver = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                gameOverText.setText("GAME OVER");
                buttonRetry.setVisibility(View.VISIBLE);
                buttonExit.setVisibility(View.VISIBLE);
                return;
            });
        }
    };

    public void buttonRetryGameClick(View view) {

        Intent intent = new Intent(this, GameActivity.class);

        if(playerBall.isOtherLevel) {
            MainActivity.startBallSpeed += 10;
            MainActivity.level++;
        }
        else {
            MainActivity.startBallSpeed = 8;
            MainActivity.score = 0;
            MainActivity.lives = 3;
            MainActivity.level = 1;
        }


        startActivity(intent);
        this.finish();
    }

    public void buttonExitGameClick(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

package com.example.nordcurrentgame.Classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.nordcurrentgame.GameActivity;
import com.example.nordcurrentgame.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BallClass {
    //ImageViews
    private ImageView ballPicture;
    private ImageView paddlePicture;

    //floats
    private float ballXFloat;
    private float ballYFloat;

    //doubles
    private double locationVectorX;
    private double locationVectorY;

    private double ballX;
    private double ballY;

    private double speed;

    public BallClass(GameActivity activity)
    {
        initializeGameBall(activity);
    }

    public Boolean initializeGameBall(GameActivity activity)
    {
        paddlePicture = (ImageView) activity.findViewById(R.id.paddle);
        paddlePicture.measure(paddlePicture.getMeasuredWidth(),paddlePicture.getMeasuredHeight());

        Display display = activity.getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        ballPicture = (ImageView) activity.findViewById(R.id.gameBall);
        ballPicture.setVisibility(View.VISIBLE);
        ballPicture.setX(width / 2);
        ballPicture.setY(height / 2);

        ballX = ballPicture.getX();
        ballY = ballPicture.getY();

        log(width/2);
        log(height/2);

        speed = 10;
        //create new random vector to begin game
        Random r = new Random();

        locationVectorX = 0 + r.nextDouble() * (size.x - 0);
        locationVectorY = 0 + r.nextDouble() * (size.y -  0);



        calcVector();

        log(locationVectorX);
        log(locationVectorY);

        return true;
    }

    public void moveBall()
    {
        BigDecimal tempBallX;
        BigDecimal tempBallY;

        //if(ballX != 0 && ballY != 0) {
            double vectorFunction = (locationVectorY / locationVectorX);
            ballX = (double) (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
            ballY = (double) vectorFunction * ballX;
            log(ballX);
            log(ballY);

            tempBallX =  new BigDecimal(ballX);
            tempBallY = new BigDecimal(ballY);
            log("-----------");
            //log(tempBallX);
            //log(tempBallY);

            ballXFloat = tempBallX.floatValue();
            ballYFloat = tempBallY.floatValue();
            log("-----------");
            log(ballXFloat);
            log(ballXFloat);

            ballPicture.setX(ballXFloat);
            ballPicture.setY(ballYFloat);
        //}
        //log(ballXFloat);
        //log(ballYFloat);
    }

    public void calcVector()
    {
        locationVectorX = (locationVectorX - ballX);
        locationVectorY = (locationVectorY - ballY);
        //log(ballX);
        //log(ballY);
    }

    public void log(String x)
    {
        String msg = "kon to = " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
    public void log(float x)
    {
        String msg = "kon to = " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }

    public void log(double x)
    {
        String msg = "kon to = " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
}

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

    //booleans
    private boolean _isBallMovementUp;
    private boolean _isBallMovementLeft;

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

        log(ballX, "ballX");
        log(ballY, "ballY");

        speed = 5;
        //create new random vector to begin game
        Random r = new Random();

        locationVectorX = 0 + r.nextDouble() * (size.x - 0);
        locationVectorY = 0 + r.nextDouble() * (size.y - 0);
        //locationVectorY = 0;



        calcVector();

        log(locationVectorX, "location vector X");
        log(locationVectorY,"location vector Y");

        return true;
    }

    public void moveBall()
    {
        BigDecimal tempBallX;
        BigDecimal tempBallY;

        //if(ballX != 0 && ballY != 0) {
            double vectorFunction = (locationVectorY / locationVectorX);
            double deltax, deltay;
            deltax = (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
            deltay = speed / deltax;

            if(_isBallMovementUp)
                ballY = (double)ballY - deltay;
            else
                ballY = (double)ballY + deltay;

            if(_isBallMovementLeft)
                ballX = (double)ballX - deltax;
            else
                ballX = (double)ballX + deltax;

            log(ballX, "ballX");
            log(ballY,  "ballY");

            tempBallX =  new BigDecimal(ballX);
            tempBallY = new BigDecimal(ballY);
            //log("-----------");
            //log(tempBallX);
            //log(tempBallY);

            ballXFloat = tempBallX.floatValue();
            ballYFloat = tempBallY.floatValue();
            log("-----------");
            log(ballXFloat, "ballX float");
            log(ballYFloat, "ballY float");

            ballPicture.setX(ballXFloat);
            ballPicture.setY(ballYFloat);
        //}
        //log(ballXFloat);
        //log(ballYFloat);
    }

    public void calcVector()
    {
        if(locationVectorX<ballX)
            _isBallMovementLeft=true;
        else
            _isBallMovementLeft=false;

        if(locationVectorY<ballY)
            _isBallMovementUp=true;
        else
            _isBallMovementUp=false;

        locationVectorX = (double) (locationVectorX - ballX);
        locationVectorY = (double) (locationVectorY - ballY);
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

    public void log(double x, String s)
    {
        String msg = "kon to = " + x + " " + s;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
}

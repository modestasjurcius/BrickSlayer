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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
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
    private boolean isBrickCollision;

    //ints
    private int displayWidth;
    private int displayHeight;

    //layouts
    private GridLayout brickField;

    //params
    public ViewGroup.MarginLayoutParams brickFieldParams;

    public BallClass(GameActivity activity)
    {
        initializeGameBall(activity);
    }

    public Boolean initializeGameBall(GameActivity activity)
    {
        paddlePicture = (ImageView) activity.findViewById(R.id.paddle);
        paddlePicture.measure(paddlePicture.getMeasuredWidth(),paddlePicture.getMeasuredHeight());

        isBrickCollision = false;

        Display display = activity.getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        displayWidth = size.x;
        displayHeight = size.y;

        ballPicture = (ImageView) activity.findViewById(R.id.gameBall);
        ballPicture.setVisibility(View.VISIBLE);
        ballPicture.setX(displayWidth / 2);
        ballPicture.setY(displayHeight / 2);

        ballX = ballPicture.getX();
        ballY = ballPicture.getY();

        //log(ballX, "ballX");
        //log(ballY, "ballY");

        speed = 10;
        //create new random vector to begin game
        Random r = new Random();

        locationVectorX = 0 + r.nextDouble() * (size.x - 0);
        //locationVectorY = 0 + r.nextDouble() * (size.y - 0);//-----------------------------------
        locationVectorY = 0;

        brickField = activity.findViewById(R.id.brickField);

        calcVector();

        //log(locationVectorX, "location vector X");
        //log(locationVectorY,"location vector Y");

        return true;
    }

    public void moveBall()
    {
        BigDecimal tempBallX;
        BigDecimal tempBallY;


        if(ballX > 0 && ballX+ballPicture.getWidth()<displayWidth && ballY > 0 && ballY+5*ballPicture.getHeight() < displayHeight) {
            double vectorFunction = (locationVectorY / locationVectorX);
            double deltax, deltay;
            deltax = (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
            deltay = Math.sqrt(speed*speed - deltax*deltax);

            if(_isBallMovementUp)
                ballY = (double)ballY - deltay;
            else
                ballY = (double)ballY + deltay;

            if(_isBallMovementLeft)
                ballX = (double)ballX - deltax;
            else
                ballX = (double)ballX + deltax;

            tempBallX =  new BigDecimal(ballX);
            tempBallY = new BigDecimal(ballY);

            ballXFloat = tempBallX.floatValue();
            ballYFloat = tempBallY.floatValue();

            checkBrickCollision();
            if(!isBrickCollision) {
                ballPicture.setX(ballXFloat);
                ballPicture.setY(ballYFloat);
            }
            else log("true");
        }
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

    public void checkBrickCollision()
    {
        brickFieldParams = (ViewGroup.MarginLayoutParams) brickField.getLayoutParams();
        int brickCount = brickField.getChildCount();

        int brickX, brickY, brickWidth, brickHeight;

        for(int i=0; i < brickCount; i++) {
            View brick = brickField.getChildAt(i);

            brickX = (int) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin
            brickY = (int) brick.getY() + brickFieldParams.topMargin; //50 - gridlayout top margin
            brickWidth = brick.getWidth();
            brickHeight = brick.getHeight();

            //check if ball collides with top
            if(ballX>=brickX && ballX<=brickX + brickWidth && ballY>=brickY && ballY<=brickY + brickHeight ) {
                isBrickCollision = true;
                log("crash 1");
                //brickField.removeView(brick);
                brickField.removeViewInLayout(brick);
                log("crash 2");
                //log(brickX, "brick " + i + " x = ");
                //log(brickY, "brick " + i + " y = ");
                break;
            }

        }
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
        String msg = s + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }
}

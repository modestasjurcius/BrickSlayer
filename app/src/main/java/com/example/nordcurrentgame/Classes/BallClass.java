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

import static java.lang.Math.PI;

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
    private double vectorFunction;
    private double ballX;
    private double ballY;
    private double speed;
    private double previousBallX;
    private double previousBallY;
    private double deltax, deltay;

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

        speed = 10;
        //create new random vector to begin game
        calcVector();

        brickField = activity.findViewById(R.id.brickField);

        return true;
    }

    public void moveBall()
    {
            calcBallXY();

            checkBrickCollision();
            checkPaddleCollision();

            if(!isBrickCollision) {
                setBallXY();
            }
            else {
                    calcBallXY();
                    setBallXY();

                isBrickCollision=false;
            }


    }

    public void setBallXY ()
    {
        BigDecimal tempBallX;
        BigDecimal tempBallY;

        tempBallX = new BigDecimal(ballX);
        tempBallY = new BigDecimal(ballY);

        ballXFloat = tempBallX.floatValue();
        ballYFloat = tempBallY.floatValue();

        ballPicture.setX(ballXFloat);
        ballPicture.setY(ballYFloat);
    }

    public void calcBallXY()
    {
        ballX = ballPicture.getX();
        ballY = ballPicture.getY();

        previousBallX = ballX;
        previousBallX = ballY;

        if(ballX <= 0)
        {
            _isBallMovementLeft=false;
        }
        if (ballY <= 0)
        {
            _isBallMovementUp=false;
        }
        if (ballX+ballPicture.getWidth() >= displayWidth)
        {
            _isBallMovementLeft=true;
        }
        if(ballY+ballPicture.getHeight() >= displayHeight - 140)
        {
            _isBallMovementUp=true;
        }

            previousBallX = ballPicture.getX();
            previousBallY = ballPicture.getY();

            if (_isBallMovementUp)
                ballY = (double) ballY - deltay;
            else
                ballY = (double) ballY + deltay;
            if (_isBallMovementLeft)
                ballX = (double) ballX - deltax;
            else
                ballX = (double) ballX + deltax;
    }

    public void calcBallXYNearBrick(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickX, brickY, brickWidth, brickHeight;


        brickWidth = brick.getWidth();
        brickHeight = brick.getHeight();

        brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin
        brickY = (double) brick.getY() + brickFieldParams.topMargin; //50 - gridlayout top margin

        //interpoliaciniu daugianario metodu :
        ballY = brickY + brickHeight;
        ballX = (ballY-(vectorFunction*-1*ballX+previousBallY))/vectorFunction;



        setBallXY();
    }

    public void calcVector()
    {
        Random r = new Random();
        locationVectorX = 0 + r.nextDouble() * (displayWidth - 0);
        locationVectorY = 0;

        if(locationVectorX>displayWidth)
            locationVectorX = displayWidth;
        else if(locationVectorX < 0)
            locationVectorX = 0;

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
        vectorFunction = (locationVectorY / locationVectorX);

        deltax = (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
        deltay = Math.sqrt(speed * speed - deltax * deltax);
        //log(ballX);
        //log(ballY);
    }

    public void calcVector(double x, double y)
    {
        locationVectorX = x;
        locationVectorY = y;

        locationVectorX = (double) (locationVectorX - ballX);
        locationVectorY = (double) (locationVectorY - ballY);

        vectorFunction = (locationVectorY / locationVectorX);

        deltax = (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
        deltay = Math.sqrt(speed * speed - deltax * deltax);
    }

    public void checkBrickCollision()
    {
        brickFieldParams = (ViewGroup.MarginLayoutParams) brickField.getLayoutParams();
        int brickCount = brickField.getChildCount();

        double brickX, brickY, brickWidth, brickHeight;
        double ballWidth, ballHeight;

        ballWidth = ballPicture.getWidth();
        ballHeight = ballPicture.getHeight();

        for(int i=0; i < brickCount; i++) {
            View brick = brickField.getChildAt(i);

            brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin
            brickY = (double) brick.getY() + brickFieldParams.topMargin; //50 - gridlayout top margin

            brickWidth = brick.getWidth();
            brickHeight = brick.getHeight();

            //check if brick collides with top left ball corner
            if(_isBallMovementUp) {
                if (ballX >= brickX && ballX <= brickX + brickWidth && ballY >= brickY && ballY <= brickY + brickHeight) {
                    isBrickCollision = true;
                    _isBallMovementUp=false;
                    calcBallXYNearBrick(i);
                    brickField.removeViewInLayout(brick);
                    //log(ballX, "COLLISION TIME ball X = ");
                    //log(ballY, "COLLISION TIME ball Y = ");
                    //log("-----------------------");
                    //log(brickX, "COLLISION TIME brick X = ");
                    //log(brickY, "COLLISION TIME brick Y = ");
                    break;
                }

                if (ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + brickWidth && ballY >= brickY && ballY <= brickY + brickHeight) {
                    isBrickCollision = true;
                    _isBallMovementUp=false;
                    calcBallXYNearBrick(i);
                    brickField.removeViewInLayout(brick);
                    break;
                }
            }
                else // BALL MOVEMENT IS DOWN
                {
                    if (ballX >= brickX && ballX <= brickX + brickWidth && ballY + brickHeight >= brickY && ballY + brickHeight <= brickY + brickHeight){
                        isBrickCollision = true;
                        _isBallMovementUp=true;
                        calcBallXYNearBrick(i);
                        brickField.removeViewInLayout(brick);
                        break;
                    }
                    if (ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + brickWidth && ballY + brickHeight >= brickY && ballY + brickHeight <= brickY + brickHeight) {
                        isBrickCollision = true;
                        _isBallMovementUp=true;
                        calcBallXYNearBrick(i);
                        brickField.removeViewInLayout(brick);
                        break;
                    }
                }

                if(_isBallMovementLeft) {
                    if(ballX >= brickX + brickWidth - ballWidth && ballX <= brickX + brickWidth && ballY >= brickY && ballY <= brickY + brickHeight) {
                        isBrickCollision = true;
                        _isBallMovementLeft=false;
                        calcBallXYNearBrick(i);
                        brickField.removeViewInLayout(brick);
                        break;
                    }
                    if(ballX >= brickX + brickWidth - ballWidth && ballX <= brickX + brickWidth && ballY + ballHeight >= brickY && ballY + ballHeight <= brickY + brickHeight) {
                        isBrickCollision = true;
                        _isBallMovementLeft=false;
                        calcBallXYNearBrick(i);
                        brickField.removeViewInLayout(brick);
                        break;
                    }
                }
                else {
                    if(ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + ballWidth && ballY >= brickY && ballY <= brickY + brickHeight) {
                        isBrickCollision = true;
                        _isBallMovementLeft=true;
                        calcBallXYNearBrick(i);
                        brickField.removeViewInLayout(brick);
                        break;
                    }
                    if(ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + ballWidth && ballY + ballHeight >= brickY && ballY + ballHeight <= brickY + brickHeight) {
                        isBrickCollision = true;
                        _isBallMovementLeft=true;
                        calcBallXYNearBrick(i);
                        brickField.removeViewInLayout(brick);
                        break;
                    }
                }

        }
    }

    public void checkPaddleCollision ()
    {
        double paddleX, paddleY, paddleWidth, paddleHeight;
        double MAXBOUNCEANGLE = 5*PI/12;

        paddleX = paddlePicture.getX();
        paddleY = paddlePicture.getY();
        paddleWidth = paddlePicture.getWidth();
        paddleHeight = paddlePicture.getHeight();

                if(ballX >= paddleX && ballX + ballPicture.getWidth() <= paddleX + paddleWidth && ballY+ballPicture.getWidth() >= paddleY && !_isBallMovementUp) {

                    ballY = paddleY - ballPicture.getHeight();
                    ballX = (ballY-(vectorFunction*-1*previousBallX+previousBallY))/vectorFunction;

                    setBallXY();

                    double relativeIntersectX = (paddleX + (paddleWidth/2)) - (ballX + (ballPicture.getWidth()/2));
                    double normalizedRelativeIntersectionX = relativeIntersectX / (paddleWidth/2);
                    double bounceAngle = normalizedRelativeIntersectionX * MAXBOUNCEANGLE;

                    locationVectorX = speed * -Math.sin(bounceAngle);
                    locationVectorY = speed * Math.cos(bounceAngle);

                    deltax = ballX + locationVectorX;
                    deltay = ballY - locationVectorY;

                    if(normalizedRelativeIntersectionX > 0)
                        _isBallMovementLeft=true;
                    else
                        _isBallMovementLeft=false;

                    calcVector(ballX + locationVectorX, ballY + locationVectorY);
                    log(locationVectorX,"location vector X == ");
                    log(locationVectorY,"location vector Y == ");
                    log("------------");

                    _isBallMovementUp=true;
                }
    }

    public void calcAngle(double p1x, double p1y, double p2x, double p2y, double fpx, double fpy)
    {
        double angle1 = Math.atan2(p1y - fpy, p1x - fpx);
        double angle2 = Math.atan2(p2y - fpy, p2x - p2x);

        double realAngle = Math.toDegrees(angle1 - angle2);
        log(realAngle, "DA ANGLE BETWEEN POINTZZZZZ ======= ");
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

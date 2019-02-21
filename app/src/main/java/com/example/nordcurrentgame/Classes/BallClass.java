package com.example.nordcurrentgame.Classes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nordcurrentgame.GameActivity;
import com.example.nordcurrentgame.MainActivity;
import com.example.nordcurrentgame.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
    public double startSpeed;
    public double speed;
    private double previousBallX;
    private double previousBallY;
    private double deltax, deltay;

    //booleans
    private boolean _isBallMovementUp;
    private boolean _isBallMovementLeft;
    public boolean isBrickCollision;
    public boolean isRemoveLife;
    public boolean isOtherLevel;

    //ints
    private int displayWidth;
    private int displayHeight;
    private int ballCollisionCount;
    public int displayBarHeight;

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
        paddlePicture = activity.findViewById(R.id.paddle);
        paddlePicture.measure(paddlePicture.getMeasuredWidth(),paddlePicture.getMeasuredHeight());

        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            displayBarHeight = resources.getDimensionPixelSize(resourceId);
        }

        //booleans
        isBrickCollision = false;
        isRemoveLife = false;
        isOtherLevel = false;

        //display size
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        displayWidth = size.x;
        displayHeight = size.y;

        //balpicture image set
        ballPicture = activity.findViewById(R.id.gameBall);
        ballPicture.setVisibility(View.VISIBLE);
        ballPicture.setX(displayWidth / 2);
        ballPicture.setY(displayHeight / 2);

        ballX = ballPicture.getX();
        ballY = ballPicture.getY();

        speed = MainActivity.startBallSpeed;

        ballCollisionCount = 0;

        //create new random vector to begin game
        calcVector();

        brickField = activity.findViewById(R.id.brickField);
        return true;
    }

    public void moveBall()
    {
        if(ballCollisionCount>=6) {
            speed ++;
            ballCollisionCount = 0;
        }
            calcBallXY();
            checkBrickCollision();
            checkPaddleCollision();

            if(!isBrickCollision)
                setBallXY();

            else {
                    calcBallXY();
                    setBallXY();
                    ballCollisionCount++;
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

        if(ballX <= 0) {
            _isBallMovementLeft=false;
            ballCollisionCount++;
        }

        if (ballY <= 0) {
            _isBallMovementUp=false;
            ballCollisionCount++;
        }

        if (ballX+ballPicture.getWidth() >= displayWidth) {
            _isBallMovementLeft=true;
            ballCollisionCount++;
        }

        if(ballY >= displayHeight - displayBarHeight - 2 * ballPicture.getHeight()) {

            _isBallMovementUp=true;
            isRemoveLife=true;
            ballCollisionCount++;
        }
            previousBallX = ballPicture.getX();
            previousBallY = ballPicture.getY();

            if (_isBallMovementUp)
                ballY = ballY - deltay;

            else
                ballY = ballY + deltay;

            if (_isBallMovementLeft)
                ballX = ballX - deltax;

            else
                ballX = ballX + deltax;
    }

    public void calcBallXYNearBrickBottom(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickY, brickHeight;

        brickHeight = brick.getHeight();
        brickY = (double) brick.getY() + brickFieldParams.topMargin; //50 - gridlayout top margin

        //interpoliaciniu daugianario metodu :
        ballY = brickY + brickHeight;
        ballX = (ballY-(vectorFunction*-1*ballX+previousBallY))/vectorFunction;

        setBallXY();
    }

    public void calcBallXYNearBrickTop(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickY;
        brickY = (double) brick.getY() + brickFieldParams.topMargin; //50 - gridlayout top margin

        previousBallY = ballY;
        previousBallX = ballX;

        //interpoliaciniu daugianario metodu :
        ballY = brickY - ballPicture.getHeight();
        ballX = (ballY-(vectorFunction*-1*ballX+previousBallY))/vectorFunction;
        setBallXY();
    }

    public void calcBallXYNearBrickLeft(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickX;

        brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin

        //interpoliaciniu daugianario metodu :
        ballX = brickX - ballPicture.getWidth();
        ballY = vectorFunction * ballX +(previousBallY + vectorFunction * -1 * previousBallX);

        setBallXY();
    }

    public void calcBallXYNearBrickRight(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickX, brickWidth;

        brickWidth = brick.getWidth();

        brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin

        //interpoliaciniu daugianario metodu :
        ballX = brickX + brickWidth;
        ballY = vectorFunction * ballX +(previousBallY + vectorFunction * -1 * previousBallX);
        setBallXY();
    }

    public Boolean checkBrickCollisionBottom(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickX, brickY, brickWidth, brickHeight;
        double tempBallX1 ,tempBallX2, tempBallY1, tempBallY2;

        brickWidth = brick.getWidth();
        brickHeight = brick.getHeight();

        brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin
        brickY = (double) brick.getY() + brickHeight + brickFieldParams.topMargin; //50 - gridlayout top margin

        tempBallX1 = brickX;
        tempBallY1 = vectorFunction * tempBallX1 +(previousBallY + vectorFunction * -1 * previousBallX);

        tempBallX2 = tempBallX1 + brickWidth;
        tempBallY2 = vectorFunction * tempBallX2 +(previousBallY + vectorFunction * -1 * previousBallX);

        if((tempBallY1 > brickY || tempBallY2 > brickY) && previousBallY > brickY && _isBallMovementUp)
            return true;

        else
            return false;

    }

    public Boolean checkBrickCollisionTop(int brickId)
    {
        View brick = brickField.getChildAt(brickId);

        double brickX, brickY, brickWidth, brickHeight;
        double tempBallX1 ,tempBallX2, tempBallY1, tempBallY2;

        brickWidth = brick.getWidth();
        brickHeight = brick.getHeight();

        brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin
        brickY = (double) brick.getY() + brickHeight + brickFieldParams.topMargin; //50 - gridlayout top margin

        tempBallX1 = brickX;
        tempBallY1 = vectorFunction * tempBallX1 +(previousBallY + vectorFunction * -1 * previousBallX);

        tempBallX2 = tempBallX1 + brickWidth;
        tempBallY2 = vectorFunction * tempBallX2 +(previousBallY + vectorFunction * -1 * previousBallX);

        if((tempBallY1 < brickY || tempBallY2 < brickY) && previousBallY < brickY && !_isBallMovementUp)
            return true;

        else
            return false;
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

        locationVectorX = (locationVectorX - ballX);
        locationVectorY = (locationVectorY - ballY);
        vectorFunction = (locationVectorY / locationVectorX);

        deltax = (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
        deltay = Math.sqrt(speed * speed - deltax * deltax);
    }

    public void calcVector(double x, double y)
    {
        locationVectorX = x;
        locationVectorY = y;

        locationVectorX = (locationVectorX - ballX);
        locationVectorY = (locationVectorY - ballY);

        vectorFunction = (locationVectorY / locationVectorX);

        deltax = (speed / Math.sqrt(vectorFunction * vectorFunction + 1));
        deltay = Math.sqrt(speed * speed - deltax * deltax);
    }

    public void checkBrickCollision()
    {
        brickFieldParams = (ViewGroup.MarginLayoutParams) brickField.getLayoutParams();
        int brickCount = brickField.getChildCount();

        if(brickCount == 0)
        {
               isOtherLevel = true;
               return;
        }

        double brickX, brickY, brickWidth, brickHeight;
        double ballWidth, ballHeight;
        double tempBallY, tempBallX;

        ballWidth = ballPicture.getWidth();
        ballHeight = ballPicture.getHeight();
        int i=0;
        for(i=0; i < brickCount; i++) {
            View brick = brickField.getChildAt(i);

            brickX = (double) brick.getX() + brickFieldParams.leftMargin; //12 - gridlayout start margin
            brickY = (double) brick.getY() + brickFieldParams.topMargin; //50 - gridlayout top margin

            brickWidth = brick.getWidth();
            brickHeight = brick.getHeight();

            //check if brick collides with top left ball corner
            if(_isBallMovementUp) {

                tempBallY = ballY + (deltay / 2);
                if(_isBallMovementLeft)
                    tempBallX = ballX + (deltax/2);
                else tempBallX = ballX - (deltax/2);

                if ((ballX >= brickX && ballX <= brickX + brickWidth && ballY >= brickY && ballY <= brickY + brickHeight) ||
                        (tempBallX >= brickX && tempBallX <= brickX + brickWidth && tempBallY >= brickY && tempBallY <= brickY + brickHeight)){
                    if(checkBrickCollisionBottom(i)) {
                        isBrickCollision = true;
                        calcBallXYNearBrickBottom(i);
                        brickField.removeViewInLayout(brick);
                        _isBallMovementUp=false;
                        return;
                    }
                }

                if ((ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + brickWidth && ballY >= brickY && ballY <= brickY + brickHeight) ||
                        (tempBallX + ballWidth >= brickX && tempBallX + ballWidth <= brickX + brickWidth && tempBallY >= brickY && tempBallY <= brickY + brickHeight)) {
                    if(checkBrickCollisionBottom(i)) {
                        isBrickCollision = true;
                        calcBallXYNearBrickBottom(i);
                        brickField.removeViewInLayout(brick);
                        _isBallMovementUp=false;
                        return;
                    }
                }
            }
                else // BALL MOVEMENT IS DOWN
                {
                    tempBallY = ballY - (deltay / 2);
                    if(_isBallMovementLeft)
                        tempBallX = ballX + (deltax/2);
                    else tempBallX = ballX - (deltax/2);

                    if ((ballX >= brickX && ballX <= brickX + brickWidth && ballY + brickHeight >= brickY && ballY + brickHeight <= brickY + brickHeight) ||
                            (tempBallX>= brickX && tempBallX <= brickX + brickWidth && tempBallY + brickHeight >= brickY && tempBallY + brickHeight <= brickY + brickHeight)){
                        if(checkBrickCollisionTop(i)) {
                            isBrickCollision = true;
                            _isBallMovementUp = true;
                            calcBallXYNearBrickTop(i);
                            brickField.removeViewInLayout(brick);
                            return;
                        }
                    }
                    if ((ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + brickWidth && ballY + brickHeight >= brickY && ballY + brickHeight <= brickY + brickHeight) ||
                            (tempBallX + ballWidth >= brickX && tempBallX + ballWidth <= brickX + brickWidth && tempBallY + brickHeight >= brickY && tempBallY + brickHeight <= brickY + brickHeight)){
                        if(checkBrickCollisionTop(i)) {
                            isBrickCollision = true;
                            _isBallMovementUp = true;
                            calcBallXYNearBrickTop(i);
                            brickField.removeViewInLayout(brick);
                            return;
                        }
                    }
                }

                if(_isBallMovementLeft) {

                    tempBallX = ballX + (deltax / 2);
                    if(_isBallMovementUp)
                        tempBallY = ballY + (deltay/2);
                    else tempBallY = ballY - (deltay/2);

                    if((ballX >= brickX + brickWidth - ballWidth && ballX <= brickX + brickWidth && ballY >= brickY && ballY <= brickY + brickHeight) ||
                            (tempBallX >= brickX + brickWidth - ballWidth && tempBallX <= brickX + brickWidth && tempBallY >= brickY && tempBallY <= brickY + brickHeight)) {
                        isBrickCollision = true;
                        _isBallMovementLeft=false;
                        calcBallXYNearBrickRight(i);
                        brickField.removeViewInLayout(brick);
                        return;
                    }
                    if((ballX >= brickX + brickWidth - ballWidth && ballX <= brickX + brickWidth && ballY + ballHeight >= brickY && ballY + ballHeight <= brickY + brickHeight) ||
                            (tempBallX >= brickX + brickWidth - ballWidth && tempBallX <= brickX + brickWidth && tempBallY + ballHeight >= brickY && tempBallY + ballHeight <= brickY + brickHeight)) {
                        isBrickCollision = true;
                        _isBallMovementLeft=false;
                        calcBallXYNearBrickRight(i);
                        brickField.removeViewInLayout(brick);
                        return;
                    }
                }
                else { // ball movement right

                    tempBallX = ballX - (deltax / 2);
                    if(_isBallMovementUp)
                        tempBallY = ballY + (deltay/2);
                    else tempBallY = ballY - (deltay/2);

                    if((ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + ballWidth && ballY >= brickY && ballY <= brickY + brickHeight) ||
                            (tempBallX + ballWidth >= brickX && tempBallX + ballWidth <= brickX + ballWidth && tempBallY >= brickY && tempBallY <= brickY + brickHeight)) {
                        isBrickCollision = true;
                        _isBallMovementLeft=true;
                        calcBallXYNearBrickLeft(i);
                        brickField.removeViewInLayout(brick);
                        return;
                    }
                    if((ballX + ballWidth >= brickX && ballX + ballWidth <= brickX + ballWidth && ballY + ballHeight >= brickY && ballY + ballHeight <= brickY + brickHeight) ||
                            (tempBallX + ballWidth >= brickX && tempBallX + ballWidth <= brickX + ballWidth && tempBallY + ballHeight >= brickY && tempBallY + ballHeight <= brickY + brickHeight)) {
                        isBrickCollision = true;
                        _isBallMovementLeft=true;
                        calcBallXYNearBrickLeft(i);
                        brickField.removeViewInLayout(brick);
                        return;
                    }
                }
        }
    }

    public void checkPaddleCollision ()
    {
        double paddleX, paddleY, paddleWidth;
        double MAXBOUNCEANGLE = PI/3;

        double tempBallY1 = ballY - (deltay / 2) + ballPicture.getHeight();

        paddleX = paddlePicture.getX();
        paddleY = paddlePicture.getY();
        paddleWidth = paddlePicture.getWidth();

                if(ballX + ballPicture.getWidth() >= paddleX && ballX <= paddleX + paddleWidth) {
                    if (ballY + ballPicture.getHeight() >= paddleY && (ballY + ballPicture.getHeight() <= paddleY + deltay || ballY + ballPicture.getHeight() - tempBallY1 <= paddleY + deltay)) {
                        if (!_isBallMovementUp) {

                            ballY = paddleY - ballPicture.getHeight();
                            ballX = (ballY - (vectorFunction * -1 * previousBallX + previousBallY)) / vectorFunction;

                            setBallXY();

                            double relativeIntersectX = (paddleX + (paddleWidth / 2)) - (ballX + (ballPicture.getWidth() / 2));
                            double normalizedRelativeIntersectionX = relativeIntersectX / (paddleWidth / 2);
                            double bounceAngle = normalizedRelativeIntersectionX * MAXBOUNCEANGLE;

                            locationVectorX = speed * -Math.sin(bounceAngle);
                            locationVectorY = speed * Math.cos(bounceAngle);

                            deltax = ballX + locationVectorX;
                            deltay = ballY - locationVectorY;

                            if (normalizedRelativeIntersectionX > 0)
                                _isBallMovementLeft = true;
                            else
                                _isBallMovementLeft = false;

                            calcVector(ballX + locationVectorX, ballY + locationVectorY);

                            _isBallMovementUp = true;
                        }
                    }
                }
    }
}

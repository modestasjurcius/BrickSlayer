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

import java.util.logging.Level;
import java.util.logging.Logger;

public class BallClass {
    private ImageView ballPicture;
    private ImageView paddlePicture;
    private float ballX;
    private float ballY;

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

        return true;
    }

    public void moveBall()
    {
        ballX -= 3;
        ballY -= 3;

        ballPicture.setX(ballX);
        ballPicture.setY(ballY);
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
}

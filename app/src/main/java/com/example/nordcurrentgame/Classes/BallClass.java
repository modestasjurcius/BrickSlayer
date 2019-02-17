package com.example.nordcurrentgame.Classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import com.example.nordcurrentgame.GameActivity;
import com.example.nordcurrentgame.R;

public class BallClass {
    public Bitmap ballBitmap;
    private ImageView ballPicture;
    public Canvas gameField;
    //private Paint myPaint;
    public BallClass(GameActivity activity)
    {
        initializeGameBall(activity);
        //gameField =  (Canvas) activity.findViewById(R.id.gameField);
    }

    public Boolean initializeGameBall(GameActivity activity)
    {
        ballPicture = new ImageView(activity);
        ballPicture.setImageResource(R.drawable.ball);
        ballPicture.setImageBitmap(ballBitmap);
        gameField = new Canvas(ballBitmap);
        gameField.drawColor(Color.TRANSPARENT);
        // gameField.dr
        return true;
    }
}

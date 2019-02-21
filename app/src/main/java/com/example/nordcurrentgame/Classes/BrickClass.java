package com.example.nordcurrentgame.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.nordcurrentgame.GameActivity;
import com.example.nordcurrentgame.R;

public class BrickClass {
    //imageviews
    public ImageView brickPicture;

    //booleans
    private Boolean _isBrickWall;

    //ints
    public int brickCount;

    public BrickClass(GameActivity activity) {
        _isBrickWall=false;
        setBrickWall(activity);
    }

    public Boolean setBrickWall(GameActivity activity)
    {
        GridLayout brickField = activity.findViewById(R.id.brickField);
        brickField.removeAllViews();

        Display display = activity.getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x - 48;

        for (int i = 0; i < 24; i++) {

            brickField = activity.findViewById(R.id.brickField);
            brickField.setColumnCount(6);
            brickField.setRowCount(4);
            brickPicture = new ImageView(activity);
            brickPicture.setImageResource(R.drawable.brick1);
            brickPicture.setScaleType(ImageView.ScaleType.FIT_XY);
            brickPicture.setLayoutParams(new android.view.ViewGroup.LayoutParams(width / 6,50));

            brickField.addView(brickPicture, i);
            brickCount=i+1;
        }
        _isBrickWall = true;
        return true;
    }
}

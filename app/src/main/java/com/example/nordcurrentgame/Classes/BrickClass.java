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

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.security.AccessController.getContext;

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

    public void log(int x)
    {
        String msg = "kon to = " + x;
        Logger log = Logger.getLogger("{Zdarowa}");
        log.log(Level.INFO,msg);
    }

    public Boolean setBrickWall(GameActivity activity)
    {
        ConstraintLayout brickField = (ConstraintLayout) activity.findViewById(R.id.brickField);
        ConstraintSet set = new ConstraintSet();
        brickField.removeAllViews();

        Display display = activity.getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x - 48;

        for (int i = 0; i < 24; i++) {

            brickPicture = new ImageView(activity);
            brickPicture.setImageResource(R.drawable.brick1);
            brickPicture.setScaleType(ImageView.ScaleType.FIT_XY);
            brickPicture.setLayoutParams(new android.view.ViewGroup.LayoutParams(width / 6,50));

            set.clone(brickField);
            brickField.addView(brickPicture, i);
            //set.connect();
            brickCount=i+1;
        }
        _isBrickWall = true;
        return true;
    }
}

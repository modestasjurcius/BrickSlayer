package com.example.nordcurrentgame.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
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
    public ImageView brickPicture;
    private Boolean _isBrickWall;

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
        GridLayout brickField = (GridLayout) activity.findViewById(R.id.brickField);
        brickField.setColumnCount(6);
        brickField.setRowCount(4);
        brickField.removeAllViews();
        brickField.getUseDefaultMargins();

        Display display = activity.getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x - 48;

        for (int i = 0; i < 24; i++) {

            brickPicture = new ImageView(activity);
            brickPicture.setImageResource(R.drawable.brick1);
            brickPicture.setScaleType(ImageView.ScaleType.FIT_XY);
            brickPicture.setLayoutParams(new android.view.ViewGroup.LayoutParams(width / 6,50));

            brickField.addView(brickPicture, i);
        }
        _isBrickWall = true;
        return true;
    }
}

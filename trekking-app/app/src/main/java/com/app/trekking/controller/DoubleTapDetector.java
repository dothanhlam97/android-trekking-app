package com.app.trekking.controller;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by lam on 7/1/18.
 */

public class DoubleTapDetector extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("DoubleTapDetector", "Double Tap!");
        //DO ACTIONS HERE
        return true;
    }
}

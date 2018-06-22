package com.app.trekking.controller;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.app.trekking.MainActivity;
import com.app.trekking.TraCuuActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lam on 6/21/18.
 */

public class favController {
    public static Animation moveCrossover, moveTop, moveLeft, controlFabForward, controlFabBackward;

    public static boolean handleFab(FloatingActionButton fab,
                                 FloatingActionButton fabTimDuong,
                                 FloatingActionButton fabToaDo,
                                 FloatingActionButton fabTraCuu,
                                 boolean isHide) {
        fab.startAnimation(controlFabForward);
        fabTimDuong.startAnimation(moveTop);
        fabTraCuu.startAnimation(moveLeft);
        fabToaDo.startAnimation(moveCrossover);
        if (isHide == true) {
            fabTimDuong.show();
            fabToaDo.show();
            fabTraCuu.show();
            return false;
        } else {
            fab.startAnimation(controlFabBackward);
            fabTimDuong.hide();
            fabToaDo.hide();
            fabTraCuu.hide();
            return true;
        }
    }

    public static void handleFabToaDo(GoogleMap map) {
        LatLng home = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
        CameraUpdate cameraHome = CameraUpdateFactory.newLatLngZoom(home, 16);
        map.animateCamera(cameraHome);
    }
}

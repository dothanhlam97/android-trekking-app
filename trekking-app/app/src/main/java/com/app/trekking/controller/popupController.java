package com.app.trekking.controller;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.app.trekking.R;

/**
 * Created by lam on 6/30/18.
 */

public class popupController {
    public static void onDialogLoginSuccess(View v, Context mContext) {

        Dialog oDialog = new Dialog(mContext);
        oDialog.setContentView(R.layout.popup_window_login);
        oDialog.show();
    }

    public static void onDialogLoginFail(View v, Context mContext) {

        Dialog oDialog = new Dialog(mContext);
        oDialog.setContentView(R.layout.popup_window_login_fail);
        oDialog.show();
    }
}

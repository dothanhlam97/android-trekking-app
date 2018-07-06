package com.app.trekking.controller;

import android.util.Log;

/**
 * Created by lam on 7/6/18.
 */

public class Profile {
    protected static String username="";
    protected static String dateOfBirth="";
    protected static String email="";
    protected static Boolean isLogin=false;

    public static void setLogin(String _username, String _email) {
        Log.d("is Login", "true");
        isLogin = true;
        username = _username;
        email = _email;
    }

    public static void setLogout() {
        isLogin = false;
        username = "";
        email = "";
        dateOfBirth = "";
    }

    public static Boolean checkLogin() {
        return isLogin;
    }

    public static void setUsername(String _username) {
        username = _username;
    }
    public static void setEmail(String _email) {
        email = email;
    }
    public static String getUsername() {
        return username;
    }
    public static String getEmail() {
        return email;
    }

}

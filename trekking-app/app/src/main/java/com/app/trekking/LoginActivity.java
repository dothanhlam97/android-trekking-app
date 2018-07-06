package com.app.trekking;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.trekking.controller.popupController;
import com.app.trekking.database.DatabaseController;
import com.app.trekking.controller.Profile;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    CheckBox ckbDieuKhoan;
    CallbackManager callbackManager;
    DatabaseController databaseController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // custom login facebook
        // CustomLogin = (Button) findViewById(R.id.btnDangNhapByFace);

        callbackManager = CallbackManager.Factory.create();
        databaseController = new DatabaseController(getApplicationContext());
        onLogin();
        onClickSignup();

    }

    public void onLogin() {
        Button loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText) findViewById(R.id.login_email);
                EditText password = (EditText) findViewById(R.id.login_password);
                String email_content = email.getText().toString();
                String password_content = password.getText().toString();
                int id = databaseController.validateUser(email_content, password_content);
                if (id != -1) {
                    Cursor cur = databaseController.getUserById(id);
                    if (cur != null) {
                        cur.moveToFirst();
                        Profile.setLogin(cur.getString(1));
//                        finish();
                        return;
                    }

                }
                popupController.onDialogLoginFail(view, LoginActivity.this);
            }
        });
    }

    public void onClickSignup() {
        Button loginBtn = (Button) findViewById(R.id.signup_btn_login_form);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
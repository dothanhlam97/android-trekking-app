package com.app.trekking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.trekking.controller.popupController;
import com.app.trekking.database.DatabaseController;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by lam on 6/30/18.
 */

public class SignupActivity extends AppCompatActivity {
    DatabaseController databaseController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Signup");

        databaseController = new DatabaseController(getApplicationContext());

        onClickSignup();
    }

    public void onClickSignup() {
        Button loginBtn = (Button) findViewById(R.id.signup_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.signup_name);
                EditText email = (EditText) findViewById(R.id.signup_email);
                EditText password = (EditText) findViewById(R.id.signup_password);
                String email_content = email.getText().toString();
                String password_content = password.getText().toString();
                String name_content = name.getText().toString();
                try {
                    databaseController.addUser(name_content, email_content, password_content);
                    Log.d("Add user", "done");
                    finish();
                } catch (Exception e) {
                    Log.e("add user", e.toString());
                }
            }
        });
    }

}

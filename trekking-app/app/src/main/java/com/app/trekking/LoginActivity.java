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

import com.app.trekking.database.DatabaseController;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    public static final String PIC_URL = "pic_url";
    public static final String EMAIL = "email";
    public static final String DOB = "birthday";
    public static final String GENDER = "gender";

    String UserName = "";
    String email = "";
    String profilePicUrl = "";
    Button CustomLogin;
    String gender = "";
    String dob = "";
    CheckBox ckbDieuKhoan;
    CallbackManager callbackManager;
    DatabaseController databaseController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // custom login facebook
//        CustomLogin = (Button) findViewById(R.id.btnDangNhapByFace);

        callbackManager = CallbackManager.Factory.create();
        databaseController = new DatabaseController(getApplicationContext());

        // custom

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                graphRequest(loginResult.getAccessToken());
                // Đăng nhập thành công
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

//        CustomLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ckbDieuKhoan = (CheckBox) findViewById(R.id.ckbDieuKhoan);
//                if (ckbDieuKhoan.isChecked()) {
//                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email","user_friends"));
//                }
//                else {
//                    Toast.makeText(LoginActivity.this, "Accept the terms and license.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        onLogin();
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
                Cursor cur = databaseController.getUserById(id);
                if (cur != null) {
                    cur.moveToFirst();
                    Log.d("username", cur.getString(1));
                    MainActivity.UserName = cur.getString(1);
                    MainActivity.isLogin = true;
                }
            }
        });
    }

    public static void onLogout() {
        Log.d("there", "there");
        MainActivity.UserName = "";
        MainActivity.isLogin = false;
    }

    public void signupOnLick() {
        Button signupBtn = (Button) findViewById(R.id.signup);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void graphRequest(AccessToken token) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                DisplayUserInfo(object);
            }
        });


        Bundle parameters = new Bundle();
        //-----------------------------------------------------------------Thêm
        parameters.putString("fields", "gender, first_name, last_name, middle_name, email, birthday, id, picture.type(large)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    private void DisplayUserInfo(JSONObject object) {
        String first_name, last_name, middle_name, id;
        Bitmap profilePic = null;
        first_name = "";
        last_name = "";
        middle_name = "";
        id = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");


            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            middle_name = object.getString("middle_name");
            email = object.getString("email");
            gender = object.getString("gender");
            dob = object.getString("birthday");

            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        TextView tv_first_name, tv_last_name, tv_id, tv_email;
        UserName = last_name + " " + middle_name + " " + first_name;

        // Đóng gói data gửi qua bên kia
        byBundle();
    }

    public void byBundle() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        intent.putExtra(USERNAME, UserName);
        intent.putExtra(PIC_URL, profilePicUrl);
        intent.putExtra(EMAIL, email);
        //--------------------------------------------Thêm
        intent.putExtra(GENDER, gender);
        intent.putExtra(DOB, dob);
        // Ẩn cái đăng nhập đi
        // MainActivity.isLogin = true;
        //
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
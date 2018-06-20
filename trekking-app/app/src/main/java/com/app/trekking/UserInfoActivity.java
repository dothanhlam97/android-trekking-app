package com.app.trekking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class UserInfoActivity extends Activity {

    EditText edtUserName, edtNgaySinh, edtGioiTinh, edtSDT, edtEmail, edtNgheNghiep;
    ImageView imgProfile;
    ImageButton imgButtonEdit;
    public static boolean isEdit = false;
    public static String UserName = "",NgaySinh = "", GioiTinh = "", SDT, Email = "", NgheNgiep = "";

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        imgButtonEdit = (ImageButton)findViewById(R.id.btnImgEdit);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtGioiTinh = (EditText) findViewById(R.id.edtGioiTinh);
        edtNgaySinh = (EditText)findViewById(R.id.edtNgaySinh);
        edtSDT = (EditText) findViewById(R.id.edtSDT);
        edtNgheNghiep = (EditText) findViewById(R.id.edtSDT);


        final Intent intent = getIntent();

        if (intent != null) {

            String username, email, profilePicUrl, gender, Dob;

            username = intent.getStringExtra(LoginActivity.USERNAME);
            email = intent.getStringExtra(LoginActivity.EMAIL);
            gender = intent.getStringExtra(LoginActivity.GENDER);
            profilePicUrl = intent.getStringExtra(LoginActivity.PIC_URL);

            //-----------------------------------------------------------------Thêm
            gender = intent.getStringExtra(LoginActivity.GENDER);
            Dob = intent.getStringExtra(LoginActivity.DOB);

            UserName = username;
            Email = email;

            edtGioiTinh.setText(GioiTinh);
            edtUserName.setText(UserName);
            edtEmail.setText(Email);
            edtNgheNghiep.setText(NgheNgiep);
            edtSDT.setText(SDT);
            edtNgaySinh.setText(NgaySinh);
            //
            try {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    URL fb_url = new URL(profilePicUrl);//small | noraml | large
                    HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
                    HttpsURLConnection.setFollowRedirects(true);
                    conn1.setInstanceFollowRedirects(true);
                    Bitmap fb_img = BitmapFactory.decodeStream(conn1.getInputStream());

                    Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(fb_img, 100);

                    imgProfile = (ImageView)findViewById(R.id.imgProfile);

                    imgProfile.setImageBitmap(circularBitmap);

                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }

        }


        imgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    edtUserName.setEnabled(true);
                    edtEmail.setEnabled(true);
                    edtGioiTinh.setEnabled(true);
                    edtNgaySinh.setEnabled(true);
                    edtSDT.setEnabled(true);
                    edtNgheNghiep.setEnabled(true);
                    isEdit = true;
                }
                else {
                    UserName = edtUserName.getText().toString();
                    Email = edtEmail.getText().toString();
                    GioiTinh = edtGioiTinh.getText().toString();
                    NgaySinh = edtNgaySinh.getText().toString();
                    SDT = edtSDT.getText().toString();
                    NgheNgiep = edtNgheNghiep.getText().toString();
                    edtUserName.setEnabled(false);
                    edtEmail.setEnabled(false);
                    edtGioiTinh.setEnabled(false);
                    edtNgaySinh.setEnabled(false);
                    edtSDT.setEnabled(false);
                    edtNgheNghiep.setEnabled(false);
                    isEdit = false;
                }
            }
        });
    }
}

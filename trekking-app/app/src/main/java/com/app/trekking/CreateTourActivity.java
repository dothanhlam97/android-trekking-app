package com.app.trekking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.trekking.controller.CommonController;
import com.app.trekking.controller.Profile;
import com.app.trekking.database.DatabaseController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Date;


import static java.lang.System.*;

/**
 * Created by lam on 7/1/18.
 */

public class CreateTourActivity extends AppCompatActivity {
    DatabaseController databaseController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_tour);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create new tour");

        databaseController = new DatabaseController(getApplicationContext());
        onChooseLocation();
        onClickSubmit();

        CommonController.setupUI(findViewById(R.id.create_new_tour), CreateTourActivity.this);
    }


    public void onChooseLocation() {
        Button loginBtn = (Button) findViewById(R.id.choose_location_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(CreateTourActivity.this, MapLocationOnTour.class);
                startActivity(intent);
            }
        });
    }

    public void onClickSubmit() {
        Button submitBtn = (Button) findViewById(R.id.create_tour_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tourNameView = (EditText) findViewById(R.id.tour_name);
                String tourName = tourNameView.getText().toString();
                EditText tourDescriptionView = (EditText) findViewById(R.id.tour_description);
                String tourDescription = tourDescriptionView.getText().toString();
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                Log.d("tour name", tourName);
                Log.d("tour des", tourDescription);
                databaseController.addTour(Profile.getEmail(), tourName, tourDescription, timestamp.toString());
            }
        });
    }
}
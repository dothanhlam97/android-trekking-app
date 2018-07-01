package com.app.trekking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.trekking.controller.CommonController;
import com.app.trekking.database.DatabaseController;

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

//        databaseController = new DatabaseController(getApplicationContext());
        onChooseLocation();
//        onClickSignup();

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

}
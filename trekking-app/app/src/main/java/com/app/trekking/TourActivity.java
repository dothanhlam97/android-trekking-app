package com.app.trekking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.app.trekking.controller.ItemTour;
import com.app.trekking.controller.TourListAdapter;
import com.app.trekking.database.DatabaseController;
import com.app.trekking.controller.Profile;

import java.util.ArrayList;

/**
 * Created by lam on 7/1/18.
 */

public class TourActivity extends AppCompatActivity {
    public static DatabaseController databaseController;
    public static ListView listTour;
    public static Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tours");
        mContext = this;
        listTour = (ListView) findViewById(R.id.listViewTour);
        databaseController = new DatabaseController(getApplicationContext());
        setupListTour(this);
        onCreateNewTour();
    }

    public static void setupListTour(Context mcontext) {
        ArrayList<ItemTour> list = new ArrayList<>();
        Cursor cur = databaseController.getListTour(Profile.getEmail());
        if (cur == null) {
            Log.d("get List tour", "no item");
        } else {
            cur.moveToFirst();
            while (cur.moveToNext()) {
                list.add(new ItemTour(cur.getString(3), cur.getString(5), cur.getInt(0)));
            }
        }
        TourListAdapter adapter = new TourListAdapter(mcontext,R.layout.custom_itemtour, list);
        listTour.setAdapter(adapter);
    }

    public void onCreateNewTour() {
        Button create_new_tour_btn = (Button) findViewById(R.id.create_new_tour_btn);
        create_new_tour_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourActivity.this, CreateTourActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void removeTour(Integer id) {
        databaseController.removeTourById(id);
        setupListTour(mContext);
    }

}

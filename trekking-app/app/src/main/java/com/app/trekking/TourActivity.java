package com.app.trekking;

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
    DatabaseController databaseController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tours");

        ArrayList<ItemTour> list = new ArrayList<>();
        databaseController = new DatabaseController(getApplicationContext());
        Cursor cur = databaseController.getListTour(Profile.getEmail());
        if (cur == null) {
            Log.d("get List tour", "no item");
        } else {
            cur.moveToFirst();
            while (cur.moveToNext()) {
                Log.d("cur ", cur.toString());
                list.add(new ItemTour(cur.getString(2), cur.getString(4), cur.getInt(0)));
            }
        }
        ListView listTour = (ListView) findViewById(R.id.listViewTour);
        TourListAdapter adapter = new TourListAdapter(this,R.layout.custom_itemtour, list);
        listTour.setAdapter(adapter);

        onCreateNewTour();
        onRemoveTour();
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

    public void onRemoveTour() {

    }
}

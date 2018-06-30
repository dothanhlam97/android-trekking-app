package com.app.trekking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.app.trekking.controller.ItemTour;
import com.app.trekking.controller.TourListAdapter;

import java.util.ArrayList;

/**
 * Created by lam on 7/1/18.
 */

public class TourActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tours");

        ArrayList<ItemTour> list = new ArrayList<>();
        list.add(new ItemTour("Test1", "01/01/01", 1));
        list.add(new ItemTour("Test2", "01/01/01", 2));
        list.add(new ItemTour("Test3", "01/01/01", 3));
        ListView listTour = (ListView) findViewById(R.id.listViewTour);
        TourListAdapter adapter = new TourListAdapter(this,R.layout.custom_itemtour, list);
        listTour.setAdapter(adapter);
    }
}

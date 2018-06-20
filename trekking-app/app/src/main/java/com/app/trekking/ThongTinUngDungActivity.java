package com.app.trekking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.trekking.R;

public class ThongTinUngDungActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinungdung);
        getSupportActionBar().setTitle("About app");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

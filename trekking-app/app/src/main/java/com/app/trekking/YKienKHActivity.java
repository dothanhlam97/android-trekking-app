package com.app.trekking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.trekking.R;

import java.util.ArrayList;

public class YKienKHActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ykienkhachhang);
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ItemListView> list = GetListData();

        listView = (ListView)findViewById(R.id.listViewYKienKH);
        CustomListAdapter adapter = new CustomListAdapter(this,R.layout.custom_itemlistview,list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if(i == 0){
                    intent = new Intent(getApplicationContext(), GopYSanPhamActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private ArrayList <ItemListView> GetListData() {
        ArrayList<ItemListView> list = new ArrayList<>();

        return list;
    }
}
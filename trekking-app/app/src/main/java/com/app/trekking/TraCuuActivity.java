package com.app.trekking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.trekking.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TraCuuActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracuu);
        getSupportActionBar().setTitle("Location search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<ItemListView1> list = GetListData();

        listView = (ListView)findViewById(R.id.listViewTraCuu);
        final Context mContext = this;
        CustomListAdapter1 adapter = new CustomListAdapter1(this,R.layout.custom_itemlistview1,list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mess = "";
                TextView placeId = (TextView) view.findViewById(R.id.idPlace);
                mess = placeId.getText().toString();
                Log.d("mess", mess);
                Intent intent = new Intent(TraCuuActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("MESS", mess);
                intent.putExtra("BUNDLE",bundle);
                startActivity(intent);
            }
        });
    }

    private ArrayList <ItemListView1> GetListData() {
        ArrayList<ItemListView1> list = new ArrayList<>();
        ItemListView1 location = null;

        try {
            //Load File
            Context mContext=getApplicationContext();
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.location)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }

            //Parse Json
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);
            Log.d("jsonFile", jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Log.d("object json", object.toString());
                Double tung = object.getDouble("locationX");
                Double hoanh = object.getDouble("locationY");
                int imageId = R.mipmap.icon_circlek;
                if (object.getString("type").equals("hotel")) {
                    imageId = R.drawable.ic_hotel;
                } else if (object.get("type").equals("restaurant")) {
                    imageId = R.drawable.ic_restaurant;
                } else if (object.get("type").equals("store")) {
                    imageId = R.drawable.ic_store;
                } else if (object.get("type").equals("place")) {
                    imageId = R.drawable.ic_place;
                }
                location = new ItemListView1(object.getString("id"), "icon_checkall", object.getString("name"), imageId);
                list.add(location);
            }

        } catch (FileNotFoundException e) {
            Log.e("jsonFile", "file not found");
        } catch (IOException e) {
            Log.e("jsonFile", "ioerror");
        } catch (JSONException e) {
            Log.e("jsonFile", "error while parsing json");
        }

        return list;
    }
}

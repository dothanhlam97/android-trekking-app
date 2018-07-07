package com.app.trekking.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.trekking.CustomListAdapter;
import com.app.trekking.ItemListView;
import com.app.trekking.R;
import com.app.trekking.TourActivity;
import com.app.trekking.database.DatabaseController;

import java.util.List;

public class TourListAdapter extends ArrayAdapter<ItemTour> {

    private List <ItemTour> listData;
    private int resource;
    private Context context;
    private DatabaseController databaseController;

    public TourListAdapter(@NonNull Context context, int resource, @NonNull List<ItemTour> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listData = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,null);
            holder = new ViewHolder();
            holder.nameView = (TextView) view.findViewById(R.id.tourName);
            holder.dateView = (TextView) view.findViewById(R.id.tourCreatedDate);
            holder.btnShowView = view.findViewById(R.id.showtour_btn);
            holder.btnRemoveView = view.findViewById(R.id.removetour_btn);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ItemTour item = this.listData.get(i);
        Log.d("tour name", item.getTourName());
        holder.nameView.setText(item.getTourName());
        holder.dateView.setText(item.getDateCreated());
        holder.btnShowView.setFocusable(true);
        holder.btnShowView.setClickable(true);
        holder.btnShowView.setHovered(true);
        holder.btnRemoveView.setFocusable(true);
        holder.btnRemoveView.setClickable(true);
        holder.btnRemoveView.setHovered(true);
        holder.id = item.getId();
//        Log.d("tag", holder.btnShowView.getTag(0).toString());
//        holder.btnRemoveView.setTag(0, new String("remove" + item.getId()));
        onClickRemove(holder);
        onClickDetail(holder);
        return view;
    }

    private void onClickRemove(final ViewHolder holder) {
        holder.btnRemoveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseController.removeTourById(holder.id);
                TourActivity.removeTour(holder.id);
            }
        });
    }

    private void onClickDetail(final ViewHolder holder) {
        holder.btnShowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = new Integer(holder.id);
                Log.d("remove", id.toString());
            }
        });
    }

    private class ViewHolder {
        TextView nameView;
        TextView dateView;
        Button btnShowView;
        Button btnRemoveView;
        int id;
    }
}
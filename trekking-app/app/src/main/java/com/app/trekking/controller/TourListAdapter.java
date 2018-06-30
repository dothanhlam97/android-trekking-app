package com.app.trekking.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.trekking.CustomListAdapter;
import com.app.trekking.ItemListView;
import com.app.trekking.R;

import java.util.List;

public class TourListAdapter extends ArrayAdapter<ItemTour> {

    private List <ItemTour> listData;
    private int resource;
    private Context context;

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
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ItemTour item = this.listData.get(i);
        holder.nameView.setText(item.getTourName());
        holder.dateView.setText(item.getDateCreated());

        return view;
    }

    private class ViewHolder {
        TextView nameView;
        TextView dateView;
    }
}
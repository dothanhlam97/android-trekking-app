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
            holder.btnShowView = view.findViewById(R.id.showtour_btn);
            holder.btnRemoveView = view.findViewById(R.id.removetour_btn);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ItemTour item = this.listData.get(i);
        holder.nameView.setText(item.getTourName());
        holder.dateView.setText(item.getDateCreated());
        holder.btnShowView.setFocusable(true);
        holder.btnShowView.setClickable(true);
        holder.btnShowView.setHovered(true);
        holder.btnRemoveView.setFocusable(true);
        holder.btnRemoveView.setClickable(true);
        holder.btnRemoveView.setHovered(true);
        holder.btnRemoveView.setId(item.getId());

        return view;
    }

    private class ViewHolder {
        TextView nameView;
        TextView dateView;
        Button btnShowView;
        Button btnRemoveView;
    }
}
package com.app.trekking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.trekking.R;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<ItemListView> {

    private List <ItemListView> listData;
    private int resource;
    private Context context;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull List<ItemListView> objects) {
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
            //Anh xa
            holder.flagView = (ImageView) view.findViewById(R.id.imageView_flag);
            holder.chuDeView = (TextView) view.findViewById(R.id.tvChuDe);
            holder.noidungView = (TextView) view.findViewById(R.id.tvNoiDung);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ItemListView item = this.listData.get(i);
        holder.noidungView.setText(item.getNoiDung());
        holder.chuDeView.setText(item.getChuDe());
        int imageId = this.getMipmapResIdByName(item.getLinkImage());
        holder.flagView.setImageResource(imageId);

        return view;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    private class ViewHolder {
        ImageView flagView;
        TextView chuDeView;
        TextView noidungView;
        TextView idPlace;
    }
}
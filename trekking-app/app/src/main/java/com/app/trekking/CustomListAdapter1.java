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

import org.w3c.dom.Text;

import java.util.List;

public class CustomListAdapter1 extends ArrayAdapter<ItemListView1> {
    private List<ItemListView1> listData;
    private int resource;
    private Context context;


    public CustomListAdapter1(@NonNull Context context, int resource, @NonNull List<ItemListView1> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.listData = objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CustomListAdapter1.ViewHolder holder;
        TextView idPlace;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,null);
            holder = new CustomListAdapter1.ViewHolder();
            //Anh xa
            holder.flagView = (ImageView) view.findViewById(R.id.imageView_flag1);
            holder.noidungView = (TextView) view.findViewById(R.id.tvNoiDung1);
            idPlace = (TextView) view.findViewById(R.id.idPlace);
            idPlace.setText(this.listData.get(i).getId());
            Log.d("idPlace", this.listData.get(i).getId());
            view.setTag(holder);
        } else {
            holder = (CustomListAdapter1.ViewHolder) view.getTag();
        }

        ItemListView1 item = this.listData.get(i);
        holder.noidungView.setText(item.getNoiDung());
        holder.flagView.setImageResource(item.getIdImage());

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
        TextView noidungView;
    }
}

package com.app.trekking;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.trekking.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

class MyInfoWindownAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    ImageView img;
    public MyInfoWindownAdapter(Activity context){
        this.context=context;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info, null);
        img = view.findViewById(R.id.iv_view);
        if (marker.getTitle().equals("")){
            TextView tvname=view.findViewById(R.id.tv_name);
            tvname.setText("Đi đến đây");
//            TextView tvdiachi=view.findViewById(R.id.tv_diachi);
//            tvdiachi.setText(null);
//            TextView tvopen=view.findViewById(R.id.tv_open);
//            tvopen.setText(null);
//            TextView tvdanhgia=view.findViewById(R.id.tv_danhgia);
//            tvdanhgia.setText(null);
            img.setImageBitmap(null);
            return view;
        }
        TextView tvname=view.findViewById(R.id.tv_name);
//        TextView tvdiachi=view.findViewById(R.id.tv_diachi);
//        TextView tvopen=view.findViewById(R.id.tv_open);
//        TextView tvdanhgia=view.findViewById(R.id.tv_danhgia);

        tvname.setText(marker.getTitle());
//        String s=marker.getSnippet();
//        String[]str=s.split("/",3);
//        tvdiachi.setText("Địa chỉ: "+str[0]);
//        if (str[1].compareTo("true")==0){
//            tvopen.setText("Trạng thái: Đang hoạt động");
//        }else {
//            tvopen.setText("Trạng thái: Tạm đóng cửa");
//        }
//        tvdanhgia.setText("Đánh giá: "+str[2]);
        Bitmap bitmap= (Bitmap) marker.getTag();

        img.setImageBitmap(bitmap);
        return view;
    }

}

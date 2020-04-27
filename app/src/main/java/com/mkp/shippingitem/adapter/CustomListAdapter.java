package com.mkp.shippingitem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mkp.shippingitem.R;
import com.mkp.shippingitem.model.ResponDataList;

import java.util.List;


public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ResponDataList> jarakItems;
    ImageLoader imageLoader;

    public CustomListAdapter(Activity activity, List<ResponDataList> jarakItems) {
        this.activity = activity;
        this.jarakItems = jarakItems;
    }

    @Override
    public int getCount() {
        return jarakItems.size();
    }

    @Override
    public Object getItem(int location) {
        return jarakItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list, null);


        TextView status = (TextView) convertView.findViewById(R.id.a1);
        TextView nama = (TextView) convertView.findViewById(R.id.a2);
        TextView phone = convertView.findViewById(R.id.a3);
        TextView tgl = convertView.findViewById(R.id.a4);
        TextView note = convertView.findViewById(R.id.a5);
        TextView loc = convertView.findViewById(R.id.a6);

        ResponDataList j = jarakItems.get(position);
        status.setText(j.getStatuss());
        nama.setText(j.getName());
        phone.setText(j.getPhone());
        tgl.setText(j.getUser_id());
        note.setText(j.getNote());
        loc.setText(j.getLocation());

        return convertView;
    }

}
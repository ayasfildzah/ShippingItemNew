package com.mkp.shippingitem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mkp.shippingitem.R;
import com.mkp.shippingitem.model.ResponDataList;

import java.util.ArrayList;

public class AbsentModel extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater inflater;
    CustomFilter filter;
    private ArrayList<ResponDataList> outBoxes=new ArrayList<>();
    private ArrayList<ResponDataList> listoutbox;

    public AbsentModel(Context context, ArrayList<ResponDataList> listoutbox) {
        this.context = context;
        this.listoutbox = listoutbox;
        this.outBoxes = listoutbox;
    }


    public int getCount() {
        return listoutbox.size();
    }

    @Override
    public ResponDataList getItem(int position) {
        return listoutbox.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.list_absen, parent, false);
        TextView a1 = view.findViewById(R.id.a1);
        TextView a2 = view.findViewById(R.id.a2);
        TextView a3 = view.findViewById(R.id.a3);
        TextView a4 = view.findViewById(R.id.a4);
        TextView a5 = view.findViewById(R.id.a5);
        TextView a6 = view.findViewById(R.id.a6);

        ResponDataList outs = this.getItem(i);

        a1.setText(outs.getStatuss());
        a2.setText(outs.getName());
        a3.setText("" + outs.getPhone());
        a4.setText(""+outs.getCreated_at());
        a5.setText(" " + outs.getNote());
        a6.setText(" " + outs.getLocation());



        return view;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if (filter == null) {
            filter = new CustomFilter();
        }

        return filter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                //CONSTARINT TO UPPER
                constraint = constraint.toString().toUpperCase();

                ArrayList<ResponDataList> filters = new ArrayList<ResponDataList>();

                //get specific items
                for (int i = 0; i < outBoxes.size(); i++) {
                    if (outBoxes.get(i).getName().toUpperCase().contains(constraint)) {
                        ResponDataList p = new ResponDataList(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getName(),outBoxes.get(i).getStatuss(), outBoxes.get(i).getLocation(),outBoxes.get(i).getCreated_at(),outBoxes.get(i).getUpdated_at(),outBoxes.get(i).getUser_id(),
                                outBoxes.get(i).getPhone(),outBoxes.get(i).getNote());

                        filters.add(p);
                    } else if (outBoxes.get(i).getStatuss().toUpperCase().contains(constraint)) {
                        ResponDataList p = new ResponDataList(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getName(),outBoxes.get(i).getStatuss(),
                                outBoxes.get(i).getLocation(), outBoxes.get(i).getCreated_at(),outBoxes.get(i).getUpdated_at(),outBoxes.get(i).getUser_id(),outBoxes.get(i).getPhone(),outBoxes.get(i).getNote());
                        filters.add(p);
                    }
                }

                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = outBoxes.size();
                results.values = outBoxes;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            listoutbox = (ArrayList<ResponDataList>) results.values;
            notifyDataSetChanged();
        }

    }


}
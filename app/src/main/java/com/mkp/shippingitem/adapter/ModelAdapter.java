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
import com.mkp.shippingitem.model.DataModel;

import java.util.ArrayList;

public class ModelAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater inflater;
    CustomFilter filter;
    private ArrayList<DataModel> outBoxes=new ArrayList<>();
    private ArrayList<DataModel> listoutbox;

    public ModelAdapter(Context context, ArrayList<DataModel> listoutbox) {
        this.context = context;
        this.listoutbox = listoutbox;
        this.outBoxes = listoutbox;
    }


    public int getCount() {
        return listoutbox.size();
    }

    @Override
    public DataModel getItem(int position) {
        return listoutbox.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.datamodel, parent, false);
        TextView a1 = view.findViewById(R.id.a1);
        TextView a2 = view.findViewById(R.id.a2);
        TextView a3 = view.findViewById(R.id.a3);
        TextView a4 = view.findViewById(R.id.a4);
        TextView a5 = view.findViewById(R.id.a5);
        TextView a6 = view.findViewById(R.id.a6);
        TextView a7 = view.findViewById(R.id.a7);

        DataModel outs = this.getItem(i);

        a1.setText(outs.getDelivery_number());
        a2.setText(outs.getCreator());
        a3.setText("" + outs.getNote());
        a4.setText(" " + outs.getSttus());
        a5.setText(" " + outs.getCreated_at());
        a6.setText(" " + outs.getLocation());
        a7.setText(" "+ outs.getPhone());



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

                ArrayList<DataModel> filters = new ArrayList<DataModel>();

                //get specific items
                for (int i = 0; i < outBoxes.size(); i++) {
                    if (outBoxes.get(i).getDelivery_number().toUpperCase().contains(constraint)) {
                        DataModel p = new DataModel(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getDelivery_number(),outBoxes.get(i).getCreator(),
                                outBoxes.get(i).getSttus(), outBoxes.get(i).getNote(),outBoxes.get(i).getCreated_at(),outBoxes.get(i).getPhone(),outBoxes.get(i).getLocation());

                        filters.add(p);
                    } else if (outBoxes.get(i).getCreator().toUpperCase().contains(constraint)) {
                        DataModel p = new DataModel(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getDelivery_number(),outBoxes.get(i).getCreator(),
                                outBoxes.get(i).getSttus(), outBoxes.get(i).getNote(),outBoxes.get(i).getCreated_at(),outBoxes.get(i).getPhone(),outBoxes.get(i).getLocation());
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

            listoutbox = (ArrayList<DataModel>) results.values;
            notifyDataSetChanged();
        }

    }


}
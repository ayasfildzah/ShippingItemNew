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
import com.mkp.shippingitem.model.DataListInvoice;

import java.util.ArrayList;

public class ModelList extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater inflater;
    CustomFilter filter;
    private ArrayList<DataListInvoice> outBoxes=new ArrayList<>();
    private ArrayList<DataListInvoice> listoutbox;

    public ModelList(Context context, ArrayList<DataListInvoice> listoutbox) {
        this.context = context;
        this.listoutbox = listoutbox;
        this.outBoxes = listoutbox;
    }


    public int getCount() {
        return listoutbox.size();
    }

    @Override
    public DataListInvoice getItem(int position) {
        return listoutbox.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_show_invoice, parent, false);
        TextView a1 = view.findViewById(R.id.invoice);
        TextView a2 = view.findViewById(R.id.creator);
        TextView a3 = view.findViewById(R.id.id);


        DataListInvoice outs = this.getItem(i);

        a1.setText(outs.getInvoice_recap_number());
        a2.setText("" + outs.getCreator());
        a3.setText(""+outs.getId());





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

                ArrayList<DataListInvoice> filters = new ArrayList<DataListInvoice>();

                //get specific items
                for (int i = 0; i < outBoxes.size(); i++) {
                    if (outBoxes.get(i).getInvoice_recap_number().toUpperCase().contains(constraint)) {
                        DataListInvoice p = new DataListInvoice(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getInvoice_recap_number(),outBoxes.get(i).getCreator(),
                                outBoxes.get(i).getCreated_at(),outBoxes.get(i).getUpdated_at(),outBoxes.get(i).getStatuss(),outBoxes.get(i).getAr_confirm_recap());

                        filters.add(p);
                    } else if (outBoxes.get(i).getCreator().toUpperCase().contains(constraint)) {
                        DataListInvoice p = new DataListInvoice(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getInvoice_recap_number(),outBoxes.get(i).getCreator(),
                                outBoxes.get(i).getCreated_at(),outBoxes.get(i).getUpdated_at(),outBoxes.get(i).getStatuss(),outBoxes.get(i).getAr_confirm_recap());
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

            listoutbox = (ArrayList<DataListInvoice>) results.values;
            notifyDataSetChanged();
        }

    }


}
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
import com.mkp.shippingitem.model.InvoiceDetails;

import java.util.ArrayList;

public class ModelInvoiceDetail extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater inflater;
    CustomFilter filter;
    private ArrayList<InvoiceDetails> outBoxes=new ArrayList<>();
    private ArrayList<InvoiceDetails> listoutbox;

    public ModelInvoiceDetail(Context context, ArrayList<InvoiceDetails> listoutbox) {
        this.context = context;
        this.listoutbox = listoutbox;
        this.outBoxes = listoutbox;
    }


    public int getCount() {
        return listoutbox.size();
    }

    @Override
    public InvoiceDetails getItem(int position) {
        return listoutbox.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.listinvoicedetails, parent, false);
        TextView a1 = view.findViewById(R.id.number1);
        TextView a2 = view.findViewById(R.id.id);
        TextView a3 = view.findViewById(R.id.creator);
        TextView a4 = view.findViewById(R.id.invoicenumber);
        TextView a5 = view.findViewById(R.id.stts);
        TextView a6 = view.findViewById(R.id.tgl);
        TextView a7 = view.findViewById(R.id.recapid);

        InvoiceDetails outs = this.getItem(i);

        a2.setText("" +outs.getId());
        a1.setText(outs.getInvoice_number());
        a3.setText(""+outs.getCreator());
        a4.setText(outs.getInvoice_recap_number());
        a5.setText(""+outs.getStatuss());
        a6.setText(""+outs.getCreated_at());
        a7.setText(""+outs.getInvoice_recap_id());





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

                ArrayList<InvoiceDetails> filters = new ArrayList<InvoiceDetails>();

                //get specific items
                for (int i = 0; i < outBoxes.size(); i++) {
                    if (outBoxes.get(i).getInvoice_number().toUpperCase().contains(constraint)) {
                        InvoiceDetails p = new InvoiceDetails(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getInvoice_number(),
                                outBoxes.get(i).getCreated_at(),outBoxes.get(i).getUpdated_at(),outBoxes.get(i).getInvoice_recap_id(),outBoxes.get(i).getInvoice_recap_number(),outBoxes.get(i).getCreator(),outBoxes.get(i).getStatuss(),outBoxes.get(i).getAr_confirm_recap());

                        filters.add(p);
                    } else if (outBoxes.get(i).getInvoice_recap_number().toUpperCase().contains(constraint)) {
                        InvoiceDetails p = new InvoiceDetails(outBoxes.get(i).getStatus(), outBoxes.get(i).getMessage(), outBoxes.get(i).getId(), outBoxes.get(i).getInvoice_number(),
                                outBoxes.get(i).getCreated_at(),outBoxes.get(i).getUpdated_at(),outBoxes.get(i).getInvoice_recap_id(),outBoxes.get(i).getInvoice_recap_number(),outBoxes.get(i).getCreator(),outBoxes.get(i).getStatuss(),outBoxes.get(i).getAr_confirm_recap());

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

            listoutbox = (ArrayList<InvoiceDetails>) results.values;
            notifyDataSetChanged();
        }

    }


}
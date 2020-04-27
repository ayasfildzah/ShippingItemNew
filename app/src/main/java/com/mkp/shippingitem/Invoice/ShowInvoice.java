package com.mkp.shippingitem.Invoice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.mkp.shippingitem.R;
import com.mkp.shippingitem.adapter.ModelInvoiceDetail;
import com.mkp.shippingitem.model.InvoiceDetails;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import java.util.ArrayList;

public class ShowInvoice extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView SubjectListView;
    private SearchView editText;
    private static final String TAG = ShowInvoice.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pesan);
        SubjectListView = findViewById(R.id.listview1);

        editText = (SearchView) findViewById(R.id.edittext1);
        SubjectListView.setTextFilterEnabled(true);
        setupSearchView();
    }

    private void setupSearchView() {
        editText.setIconifiedByDefault(false);
        editText.setOnQueryTextListener(this);
        editText.setSubmitButtonEnabled(true);
        editText.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            SubjectListView.clearTextFilter();
        } else {
            SubjectListView.setFilterText(newText);
        }
        return false;
    }

    private class req extends AsyncTask<String, Void, ArrayList<InvoiceDetails>> {

        ProgressDialog pgDialog = new ProgressDialog(ShowInvoice.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog.setMessage("\t Please Wait");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ArrayList<InvoiceDetails> doInBackground(String... strings) {

            try {
                return AppConstant.getListInvoiceDetails().dm(ShowInvoice.this);
            } catch (Exception e1) {
                LogHelper.verbose(TAG, "doInBackground " + e1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<InvoiceDetails> dataModels) {
            super.onPostExecute(dataModels);
            pgDialog.dismiss();
            try {

                final ModelInvoiceDetail adapter = new ModelInvoiceDetail(ShowInvoice.this, dataModels);
                SubjectListView.setAdapter(adapter);
                LogHelper.verbose(TAG, "result: " + SubjectListView.getAdapter());

            } catch (NullPointerException ei) {
                ei.printStackTrace();
                Toast.makeText(ShowInvoice.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
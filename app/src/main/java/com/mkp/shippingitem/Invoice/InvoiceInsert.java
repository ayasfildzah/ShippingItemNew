package com.mkp.shippingitem.Invoice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mkp.shippingitem.Invoice_Recap_details;
import com.mkp.shippingitem.R;
import com.mkp.shippingitem.adapter.ModelList;
import com.mkp.shippingitem.model.DataListInvoice;
import com.mkp.shippingitem.model.InvoiceData;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InvoiceInsert extends AppCompatActivity  implements SearchView.OnQueryTextListener {

    private static final String TAG = InvoiceInsert.class.getName();
    private String enteredDelivery, enteredStatus, enteredNote,enteredCreator,enteredPhone,enteredAlamat;
    private EditText editDeliv, editNote,kode_qr,editID;

    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView LgUser,editalamat,editPhone;
    Button btnScan;
    private String userLg,phoneLg;
    private SharedPreferences mPreferences;
    ListView SubjectListView;
    private SearchView editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_insert);

        editDeliv = findViewById(R.id.editTextNumber);
        LgUser = findViewById(R.id.LgUser);
        SubjectListView = (ListView) findViewById(R.id.listview1);
        editID = findViewById(R.id.editID);

        editText = (SearchView) findViewById(R.id.edittext1);

        new InvoiceInsert.req().execute();

        SubjectListView.setTextFilterEnabled(true);
        setupSearchView();
        //mPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editDeliv.setText(getIntent().getStringExtra("data2"));
        Button AddButton = findViewById(R.id.btnsubmit);
        //// insert button
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String ph  = editDeliv.getText().toString();
                //String e  = editID.getText().toString();

                if (editDeliv.getText().toString().equals("")) {
                    Toast.makeText(InvoiceInsert.this, "Invoice Number wajib di isi", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    new InvoiceInsert.insert().execute();
                    Intent intent = new Intent(InvoiceInsert.this, Invoice_Recap_details.class);
                    Toast.makeText(InvoiceInsert.this,"Berhasil",Toast.LENGTH_SHORT).show();
                    intent.putExtra("data1", editID.getText().toString());
                    intent.putExtra("data2", editDeliv.getText().toString());
                    startActivity(intent);
                }
            }
        });

        kode_qr = findViewById(R.id.editTextNumber);
        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);
        btnScan = findViewById(R.id.fab);
        btnScan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }

        });


        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        //get SharedPreferences dari Login
        userLg = mPreferences.getString("name","");
        LgUser.setText(userLg);


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

    private class req extends AsyncTask<String, Void, ArrayList<DataListInvoice>> {

        ProgressDialog pgDialog = new ProgressDialog(InvoiceInsert.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog.setMessage("\t Please Wait");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ArrayList<DataListInvoice> doInBackground(String... strings) {

            try {
                return AppConstant.getListInvoice().dm(InvoiceInsert.this);
            } catch (Exception e1) {
                LogHelper.verbose(TAG, "doInBackground " + e1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataListInvoice> dataModels) {
            super.onPostExecute(dataModels);
            pgDialog.dismiss();
            try {

                final ModelList adapter = new ModelList(InvoiceInsert.this, dataModels);
                SubjectListView.setAdapter(adapter);
                LogHelper.verbose(TAG, "result: " + SubjectListView.getAdapter());

            } catch (NullPointerException ei) {
                ei.printStackTrace();
                Toast.makeText(InvoiceInsert.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data.
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    editDeliv.setText(obj.getString("textPersonName"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    String contents = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(getBaseContext(), "Hasil :" + contents, Toast.LENGTH_SHORT).show();
                    editDeliv.setText(contents);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private class insert extends AsyncTask<String, Void, InvoiceData> {
        private ProgressDialog pgDialog = new ProgressDialog(InvoiceInsert.this);

        @Override
        protected void onPreExecute() {

            pgDialog.setMessage("\tMohon Tunggu");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected InvoiceData doInBackground(String... strings) {
            enteredDelivery = editDeliv.getText().toString();
            enteredCreator = LgUser.getText().toString();

            try {
                return AppConstant.getInvoiceInsert().insert(InvoiceInsert.this, enteredDelivery,enteredCreator);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(InvoiceData result) {
            super.onPostExecute(result);
            pgDialog.dismiss();
            try {

                LogHelper.verbose(TAG,"RESULT :"+result);

            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }
}
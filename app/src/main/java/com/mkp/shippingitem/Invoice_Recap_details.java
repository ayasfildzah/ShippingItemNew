package com.mkp.shippingitem;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mkp.shippingitem.Invoice.ShowInvoice;
import com.mkp.shippingitem.adapter.ModelInvoiceDetail;
import com.mkp.shippingitem.model.DataListInvoiceDetails;
import com.mkp.shippingitem.model.InvoiceDetails;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Invoice_Recap_details extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = MainActivity.class.getName();


    private String enteredInvoice, enteredCreator,enteredNumber,enteredID;
    private EditText editInvoice, editNote,kode_qr;

    Spinner spinnerStatus;
    TextView LgUser,iduser,textData1,textData2;
    Button btnScan;
    private String userId,number,userLg;
    private SharedPreferences mPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    ImageView btn_logout;
    ListView SubjectListView;
    private SearchView editText;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice__recap_details);

        editInvoice = findViewById(R.id.editTextNumber);
        editNote = findViewById(R.id.editTextNote);
        LgUser = findViewById(R.id.LgUser);

        editText = findViewById(R.id.edittext1);

       textData1 = findViewById(R.id.textData1);
       textData2 = findViewById(R.id.textData2);

        new Invoice_Recap_details.req().execute();
        SubjectListView = findViewById(R.id.listview1);
        SubjectListView.setTextFilterEnabled(true);
        setupSearchView();

        /*mPreferences =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //get SharedPreferences dari Login
        number = mPreferences.getString("inovice_recap_number", "");
        userId = mPreferences.getString("id", "");

        RecapInvoice.setText(number);
        Idd.setText(userId);*/
       textData1.setText(getIntent().getStringExtra("data1"));
       textData2.setText(getIntent().getStringExtra("data2"));
        Button AddButton = findViewById(R.id.btnsubmit);

        //// insert button
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editInvoice.getText().toString().equals("")) {
                    Toast.makeText(Invoice_Recap_details.this, "Surat Jalan Wajib Diisi", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    new Invoice_Recap_details.insertShipping().execute();
                    Toast.makeText(Invoice_Recap_details.this,"Berhasil",Toast.LENGTH_SHORT).show();
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
        mPreferences = this.getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
        userLg = mPreferences.getString("name", "");


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

    public void showlist(View view) {
        Intent intent = new Intent(Invoice_Recap_details.this, Invoice_Recap_details.class);
        startActivity(intent);
    }

    private class req extends AsyncTask<String, Void, ArrayList<InvoiceDetails>> {

        ProgressDialog pgDialog = new ProgressDialog(Invoice_Recap_details.this);

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
                return AppConstant.getListInvoiceDetails().dm(Invoice_Recap_details.this);
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

                final ModelInvoiceDetail adapter = new ModelInvoiceDetail(Invoice_Recap_details.this, dataModels);
                SubjectListView.setAdapter(adapter);
                LogHelper.verbose(TAG, "result: " + SubjectListView.getAdapter());

            } catch (NullPointerException ei) {
                ei.printStackTrace();
                Toast.makeText(Invoice_Recap_details.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
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
                    editInvoice.setText(obj.getString("textPersonName"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    String contents = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(getBaseContext(), "Hasil :" + contents, Toast.LENGTH_SHORT).show();
                    editInvoice.setText(contents);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    private class insertShipping extends AsyncTask<String, Void, DataListInvoiceDetails> {
        private ProgressDialog pgDialog = new ProgressDialog(Invoice_Recap_details.this);

        @Override
        protected void onPreExecute() {

            pgDialog.setMessage("\tMohon Tunggu");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected DataListInvoiceDetails doInBackground(String... strings) {
            enteredInvoice = editInvoice.getText().toString();
            enteredCreator = LgUser.getText().toString();
            enteredNumber = textData2.getText().toString();
            enteredID = textData1.getText().toString();

            try {
                return AppConstant.getInvoiceDetailsApi().insDetails(Invoice_Recap_details.this, enteredInvoice,enteredID,enteredCreator,enteredNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(DataListInvoiceDetails result) {
            super.onPostExecute(result);
            pgDialog.dismiss();
            try {

                LogHelper.verbose(TAG,"RESULT SHIPPING :"+result);
            } catch (NullPointerException e) {
                e.printStackTrace();
                LogHelper.verbose(TAG, e.getMessage());
            }
        }
    }
}

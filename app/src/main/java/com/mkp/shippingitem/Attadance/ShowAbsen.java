package com.mkp.shippingitem.Attadance;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.mkp.shippingitem.R;
import com.mkp.shippingitem.adapter.AbsentModel;
import com.mkp.shippingitem.model.ResponDataList;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import java.util.ArrayList;

public class ShowAbsen extends AppCompatActivity  implements SearchView.OnQueryTextListener {
    private static final String TAG = com.mkp.shippingitem.ShowPesan.class.getName();
    ListView SubjectListView;
    private SearchView editText;
    Toolbar toolbar;
    String creator;
    ImageView btn_logout;
    SharedPreferences mPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_absen);

        SubjectListView = (ListView) findViewById(R.id.listvieww);

        editText = (SearchView) findViewById(R.id.edittext1);

        //  mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
        // creator = mPreferences.getString("creator","");
        //editText.setFilterTouchesWhenObscured(creator);
        new ShowAbsen.req().execute();

        SubjectListView.setTextFilterEnabled(true);
        setupSearchView();
           }

    private void setupSearchView() {
        editText.setIconifiedByDefault(false);
        editText.setOnQueryTextListener(this);
        editText.setSubmitButtonEnabled(true);
        editText.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            SubjectListView.clearTextFilter();
        } else {
            SubjectListView.setFilterText(newText);
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private class req extends AsyncTask<String, Void, ArrayList<ResponDataList>> {

        ProgressDialog pgDialog = new ProgressDialog(ShowAbsen.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog.setMessage("\t Please Wait");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ArrayList<ResponDataList> doInBackground(String... strings) {

            try {
                return AppConstant.getAbsentPresentApi().dm(ShowAbsen.this);
            } catch (Exception e1) {
                LogHelper.verbose(TAG, "doInBackground " + e1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ResponDataList> dataModels) {
            super.onPostExecute(dataModels);
            pgDialog.dismiss();
            try {

                final AbsentModel adapter = new AbsentModel(ShowAbsen.this, dataModels);
                SubjectListView.setAdapter(adapter);
                LogHelper.verbose(TAG, "result: " + SubjectListView.getAdapter());

            } catch (NullPointerException ei) {
                ei.printStackTrace();
                Toast.makeText(ShowAbsen.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
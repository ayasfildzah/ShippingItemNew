package com.mkp.shippingitem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.mkp.shippingitem.adapter.ModelAdapter;
import com.mkp.shippingitem.model.DataModel;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.LogHelper;

import java.util.ArrayList;

public class ShowPesan extends AppCompatActivity  implements SearchView.OnQueryTextListener {
    private static final String TAG = ShowPesan.class.getName();
    ListView SubjectListView;
    private SearchView editText;
    Toolbar toolbar;
    String creator;
    ImageView btn_logout;
    SharedPreferences mPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add);

        SubjectListView = (ListView) findViewById(R.id.listview1);

        editText = (SearchView) findViewById(R.id.edittext1);

      //  mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //get SharedPreferences dari Login
       // creator = mPreferences.getString("creator","");
        //editText.setFilterTouchesWhenObscured(creator);
        new req().execute();

        SubjectListView.setTextFilterEnabled(true);
        setupSearchView();
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(ShowPesan.this, MainActivity.class);
                finish();
                startActivity(intent);


            }
        });

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

    public void about(View view) {
        Intent intent = new Intent(ShowPesan.this,FragmentBottom.class);
        startActivity(intent);
    }

    public void home(View view) {
        Intent intent = new Intent(ShowPesan.this,FragmentBottom.class);
        startActivity(intent);
    }

    private class req extends AsyncTask<String, Void, ArrayList<DataModel>> {

        ProgressDialog pgDialog = new ProgressDialog(ShowPesan.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgDialog.setMessage("\t Please Wait");
            pgDialog.setCancelable(false);
            pgDialog.show();
        }

        @Override
        protected ArrayList<DataModel> doInBackground(String... strings) {

            try {
                return AppConstant.getApiShippingList().dm(ShowPesan.this);
            } catch (Exception e1) {
                LogHelper.verbose(TAG, "doInBackground " + e1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataModel> dataModels) {
            super.onPostExecute(dataModels);
            pgDialog.dismiss();
            try {

                final ModelAdapter adapter = new ModelAdapter(ShowPesan.this, dataModels);
                SubjectListView.setAdapter(adapter);
                LogHelper.verbose(TAG, "result: " + SubjectListView.getAdapter());

            } catch (NullPointerException ei) {
                ei.printStackTrace();
                Toast.makeText(ShowPesan.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
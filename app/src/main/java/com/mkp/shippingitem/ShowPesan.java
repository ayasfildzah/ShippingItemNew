package com.mkp.shippingitem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mkp.shippingitem.Controller.RequestHandler;
import com.mkp.shippingitem.Jalan.AddJalan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPesan extends AppCompatActivity implements ListView.OnItemClickListener {


    private ListView listView;

    private String JSON_STRING;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_shipping);
        listView = findViewById(R.id.listItem);
        listView.setOnItemClickListener(this);
        getJSON();
    }


    private void showRate() {
        JSONObject jsonObject;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY3);

            for (int i = 0; i < result.length(); i++) {
                JSONObject ok = result.getJSONObject(i);
                String deliv = ok.getString(konfigurasi.TAG_Delivnum);

                String nt = ok.getString(konfigurasi.TAG_Note);

                HashMap<String, String> pel = new HashMap<>();
                pel.put(konfigurasi.TAG_Delivnum,deliv);

                pel.put(konfigurasi.TAG_Note,nt);



                list.add(pel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ShowPesan.this, list, R.layout.activity_show_pesan,
                new String[]{konfigurasi.TAG_Delivnum , konfigurasi.TAG_Note},
                new int[]{R.id.delivnum, R.id.status});

        listView.setAdapter(adapter);
    }

    private void getJSON() {

        class GetJSON extends AsyncTask<Void, Void, String> {

            private ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowPesan.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }


            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showRate();
            }


            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequest(konfigurasi.URL_All_ITEM);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AddJalan.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String csId = map.get(konfigurasi.TAG_Idd);
        intent.putExtra(konfigurasi.Kode_Idd, csId);
        startActivity(intent);
    }


}
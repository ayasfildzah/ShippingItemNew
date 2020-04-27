package com.mkp.shippingitem.Attadance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mkp.shippingitem.R;
import com.mkp.shippingitem.adapter.AppController;
import com.mkp.shippingitem.adapter.CustomListAdapter;
import com.mkp.shippingitem.model.ResponDataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListAbsen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipe;
    ListView list;
    TextView lokasisaya;
   CustomListAdapter adapter;
    List<ResponDataList> itemList = new ArrayList<>();


    private static final String TAG = ListAbsen.class.getSimpleName();

    /* 10.0.2.2 adalah IP Address localhost EMULATOR ANDROID STUDIO,
    Ganti IP Address tersebut dengan IP Laptop Apabila di RUN di HP. HP dan Laptop harus 1 jaringan */
    private static final String url = "https://alita.massindo.com/api/v1/attendances.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_absen);

        // menyamakan variabel pada layout dan java
        list = (ListView) findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        adapter = new CustomListAdapter(this, itemList);
        list.setAdapter(adapter);
        // mengisi data dari adapter ke listview

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           lokasi("");
                       }
                   }
        );

    }

    @Override
    public void onRefresh() {
        lokasi("");
    }

    // fungsi ngecek lokasi GPS device pengguna
    @SuppressLint("MissingPermission")
    private void lokasi(String user_id) {
        itemList.clear();
        adapter.notifyDataSetChanged();
            JsonArrayRequest jArr = new JsonArrayRequest(url ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                ResponDataList j = new ResponDataList();
                                j.setName(obj.getString("name"));
                                j.setLocation(obj.getString("location"));
                                j.setCreated_at(obj.getString("created_at"));
                                j.setStatuss(obj.getString("status"));
                                j.setPhone(obj.getString("phone"));
                                j.setNote(obj.getString("note"));
                                j.setUser_id(obj.getString("user_id"));



                                itemList.add(j);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // memberitahu adapter jika ada perubahan data

                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        // menambah permintaan ke queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    // untuk menyederhanakan angka dibelakan koma jarak

}
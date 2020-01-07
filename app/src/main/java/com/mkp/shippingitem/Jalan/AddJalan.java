package com.mkp.shippingitem.Jalan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mkp.shippingitem.Controller.UrlJsonAsyncTask;
import com.mkp.shippingitem.MainActivity;
import com.mkp.shippingitem.R;
import com.mkp.shippingitem.ShowHistory;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AddJalan extends AppCompatActivity {

    private final static String CREATE_TASK_URL = "https://alita.massindo.com/api/v1/shipping_items";
    /*private EditText editTextNama;*/
    private EditText editTextNumber, editTextStatus, editTextNote ;
    /*private Button btnsubmit;*/
    private  EditText kode_qr;
    private String mTaskNumber;
    private  String mTaskStatus, mTaskNote;
    Spinner spinner;
    private SharedPreferences mPreferences;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jalan);


        editTextNumber = findViewById(R.id.editTextNumber);
        spinner = findViewById(R.id.spinnerStatus);
        editTextNote = findViewById(R.id.editTextNote);
        /*spinner = findViewById(R.id.spinnerStatus); */
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);


        kode_qr = findViewById(R.id.editTextNumber);
        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }

        });
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
                    editTextNumber.setText(obj.getString("textPersonName"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    String contents = data.getStringExtra("SCAN_RESULT");
                    Toast.makeText(getBaseContext(), "Hasil :" + contents, Toast.LENGTH_SHORT).show();
                    editTextNumber.setText(contents);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void saveTask1(View button) {
        Intent intent = new Intent(AddJalan.this, ShowHistory.class);
        startActivity(intent);
        EditText taskNumberField = (EditText) findViewById(R.id.editTextNumber);
        mTaskNumber = taskNumberField.getText().toString();
        Spinner taskNameField = findViewById(R.id.spinnerStatus);
        mTaskStatus = taskNameField.getSelectedItem().toString();
        EditText taskRateField = (EditText)findViewById(R.id.editTextNote);
        mTaskNote= taskRateField.getText().toString();



        if (mTaskNumber.length() == 0) {
            // input fields are empty
            Toast.makeText(this,
                    "Please write something as a title for this Shipping Item",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            // everything is ok!
            CreateTaskTask createTask = new CreateTaskTask(AddJalan.this);
            createTask.setMessageLoading("Creating new Shipping Item...");
            createTask.execute(CREATE_TASK_URL);
        }
    }

    public void View(View view) {
        Intent intent = new Intent(AddJalan.this,ShowHistory.class);
        startActivity(intent);
    }

    private class CreateTaskTask extends UrlJsonAsyncTask {
        public CreateTaskTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject taskObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    json.put("success", "");
                    json.put("info", "Something went wrong. Retry!");
                    taskObj.put("delivery_number", mTaskNumber);
                    taskObj.put("status", mTaskStatus);
                    taskObj.put("note",mTaskNote);
                    holder.put("shipping_item", taskObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");
                    post.setHeader("Authorization", "Token token=" + mPreferences.getString("AuthToken", " "));

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.equals(200)) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("MESSAGE", "Input Berhasil");
                    startActivity(intent);
                    finish();
                }
               /* Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show(); */
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
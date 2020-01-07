package com.mkp.shippingitem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mkp.shippingitem.util.LogHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected EditText usermail;
    private EditText password;
    protected String enteredUsermail;
    private final String serverUrl = "http://alita.massindo.com/api/v1/users/sign_in";
    private SharedPreferences mPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usermail = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  new SignPro().execute();*/
                enteredUsermail = usermail.getText().toString();
                String enteredPassword = password.getText().toString();

                if (enteredUsermail.equals("") || enteredPassword.equals("")) {
                    Toast.makeText(MainActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }
                if (enteredUsermail.length() <= 1 || enteredPassword.length() <= 1) {
                    Toast.makeText(MainActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();
                    return;
                }
                // request authentication with remote server4
                AsyncDataClass asyncRequestObject = new AsyncDataClass();
                asyncRequestObject.execute(serverUrl, enteredUsermail, enteredPassword);
            }
        });


    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "result";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                LogHelper.verbose("POST", "POST: " + jsonResult);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }
       /* protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Result: " + result);
            if (result.equals("") || result == null) {
                Toast.makeText(MainActivity.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }
            int jsonResult = returnParsedJsonObject(result);
            if (result.equals(422)) {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                return;
            }
            else if (jsonResult == 200) {
                Toast.makeText(MainActivity.this,  "You have been successfully login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,ShowHistory.class);
                startActivity(intent);
            }
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }

    private int returnParsedJsonObject(String result) {
        JSONObject resultObject = null;
        int returnedResult = 200;
        try {
            resultObject = new JSONObject(result);
//            LogHelper.verbose("POST","RESULT: "+result);


            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }
}*/

        protected void onPreExecute() {
            super.onPreExecute();
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Result: " + result);
            if (result.equals("") || result == null) {
                Toast.makeText(MainActivity.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }
            // int jsonResult = returnParsedJsonObject(result);
            if (result.equals(200)) {
                LogHelper.verbose("ONPOST", "RESULT: SUKSES MASUK");
                Intent intent = new Intent(MainActivity.this, ShowHistory.class);
                intent.putExtra("MESSAGE", "You have been successfully login");
                startActivity(intent);
            } else if (result.equalsIgnoreCase("invalid email and password combination")) {
                LogHelper.verbose("ONPOST", "RESULT: GAGAL MASUK");
            } else {
                LogHelper.verbose("ONPOST", "RESULT: GAGAL MASUK");
                Intent intent = new Intent(MainActivity.this, ShowHistory.class);
                intent.putExtra("MESSAGE", "invalid email and password combination");
                startActivity(intent);
            }
//            if (jsonResult == 200) {
//                Intent intent = new Intent(MainActivity.this,ShowHistory.class);
//
//                intent.putExtra("MESSAGE", "You have been successfully login");
//                startActivity(intent);
//            }
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }

    private int returnParsedJsonObject(String result) {

        JSONObject resultObject = null;
        int returnedResult = 200;
        try {
            resultObject = new JSONObject(result);
//            LogHelper.verbose("POST","RESULT: "+result);


            returnedResult = resultObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }
}
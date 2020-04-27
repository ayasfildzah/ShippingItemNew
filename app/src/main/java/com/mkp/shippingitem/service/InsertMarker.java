package com.mkp.shippingitem.service;

import android.content.Context;

import com.mkp.shippingitem.model.DataInsert;
import com.mkp.shippingitem.util.GetHttpsURLConnection;
import com.mkp.shippingitem.util.HttpsTrustManager;
import com.mkp.shippingitem.util.LogHelper;
import com.mkp.shippingitem.util.ReadResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import javax.net.ssl.HttpsURLConnection;

import static com.mkp.shippingitem.util.AppConstant.CONNECTION_TIMEOUT;
import static com.mkp.shippingitem.util.AppConstant.READ_TIMEOUT;

public class InsertMarker {
    private static final String TAG = InsertMarker.class.getName();

    public DataInsert insShipping(Context context, String name, String status, String location, String user_id, String phone, String note) throws IOException {

        try {
            HttpsTrustManager.allowAllSSL();
            HttpsURLConnection connection = GetHttpsURLConnection.getHttpsURLConnection(context, "attendances.json");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = ("{\"name\":" + "\"" + name + "\"" + "," +
                    "\"status\":" + "\"" + status + "\"" + "," +
                    "\"location\":" + "\"" + location + "\"" + ","+
                    "\"user_id\":" + "\"" + user_id + "\"" + "," +
                    "\"phone\":" + "\"" + phone + "\"" + ","+
                     "\"note\":" + "\"" + note + "\"" + "}");
            LogHelper.verbose(TAG, "InsertAbsen" + data);
            OutputStream stream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            connection.connect();

            String response = ReadResponse.readResponse(connection);

            //JSON Multi Object
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    DataInsert res = new DataInsert();
                    res.setStatus(jsonObject.getString("status"));
                    res.setMessage(jsonObject.getString("message"));
                    JSONObject jo2 = jsonObject.getJSONObject("result");
                    res.setId(jo2.getString("id"));
                    res.setName(jo2.getString("name"));
                    res.setStatuss(jo2.getString("status"));
                    res.setLocation(jo2.getString("location"));
                    res.setCreated_at(jo2.getString("created_at"));
                    res.setUpdated_at(jo2.getString("updated_at"));
                    res.setUser_id(jo2.getString("user_id"));
                    res.setPhone(jo2.getString("phone"));
                    res.setNote(jo2.getString("note"));



                    return res;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } catch (SocketTimeoutException e1) {
            e1.printStackTrace();
            LogHelper.verbose(TAG, "InsertShipping: " + e1);
        } catch (InterruptedIOException e2) {
            e2.printStackTrace();
            LogHelper.verbose(TAG, "InsertShipping: " + e2);
        } catch (MalformedURLException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
            LogHelper.verbose(TAG, "InsertShipping: " + e3);
        } catch (Exception e4) {
            e4.printStackTrace();
            LogHelper.verbose(TAG, "InsertShipping: " + e4);
        }


        return null;
    }
}



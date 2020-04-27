package com.mkp.shippingitem.service;

import android.content.Context;

import com.mkp.shippingitem.model.InvoiceData;
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

public class InvoiceApi {
    private static final String TAG = InvoiceApi.class.getName();

    public InvoiceData insert(Context context, String invoice_recap_number, String creator) throws IOException {

        try {
            HttpsTrustManager.allowAllSSL();
            HttpsURLConnection connection = GetHttpsURLConnection.getHttpsURLConnection(context,"invoice_recaps");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = ("{\"invoice_recap_number\":" + "\"" + invoice_recap_number + "\"" + "," +
                            "\"creator\":" + "\"" + creator + "\"" + "}");

            LogHelper.verbose(TAG, "Invoice" + data);
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
                    InvoiceData res = new InvoiceData();
                    res.setStatus(jsonObject.getString("status"));
                    res.setMessage(jsonObject.getString("message"));
                    JSONObject jo2 = jsonObject.getJSONObject("result");
                    res.setId(jo2.getString("id"));
                    res.setInvoice_recap_number(jo2.getString("invoice_recap_number"));
                    res.setCreated_at(jo2.getString("created_at"));
                    res.setUpdated_at(jo2.getString("updated_at"));
                    res.setCreator(jo2.getString("creator"));
                    res.setStatuss(jo2.getString("status"));
                    res.setAr_confirm_recap(jo2.getString("ar_confirm_recap"));



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


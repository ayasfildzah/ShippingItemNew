package com.mkp.shippingitem.service;

import android.content.Context;

import com.mkp.shippingitem.model.DataListInvoice;
import com.mkp.shippingitem.util.AppConstant;
import com.mkp.shippingitem.util.GetHttpsURLConnection;
import com.mkp.shippingitem.util.HttpsTrustManager;
import com.mkp.shippingitem.util.LogHelper;
import com.mkp.shippingitem.util.ReadResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.mkp.shippingitem.util.AppConstant.CONNECTION_TIMEOUT;
import static com.mkp.shippingitem.util.AppConstant.READ_TIMEOUT;

public class ListInvoice {
    private static final String TAG = InvoiceApi.class.getName();

    public ArrayList<DataListInvoice> dm(Context context) throws IOException {
        try {
            HttpsTrustManager.allowAllSSL();
            HttpsURLConnection conection = GetHttpsURLConnection.getHttpsURLConnection(context, "invoice_recaps");
            conection.setRequestMethod("GET");
            conection.setReadTimeout(READ_TIMEOUT);
            conection.setConnectTimeout(CONNECTION_TIMEOUT);
            conection.connect();
            ArrayList<DataListInvoice> data1 = new ArrayList<DataListInvoice>();
            if (conection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                AppConstant.RESULT = ReadResponse.readResponse(conection);
                try {
                    DataListInvoice model = new DataListInvoice();
                    JSONObject jo = new JSONObject(AppConstant.RESULT);
                    model.setStatus(jo.getString("status"));
                    model.setMessage(jo.getString("message"));
                    JSONArray ja = jo.getJSONArray("result");
                    for (int x = 0; x < ja.length(); x++) {
                        JSONObject jo1 = ja.getJSONObject(x);
                        DataListInvoice model1 = new DataListInvoice();
                        model1.setId(jo1.getString("id"));
                        model1.setInvoice_recap_number(jo1.getString("invoice_recap_number"));
                        model1.setCreator(jo1.getString("creator"));
                        model1.setUpdated_at(jo1.getString("updated_at"));
                        model1.setCreated_at(jo1.getString("created_at"));
                        model1.setStatuss(jo1.getString("status"));
                        model1.setAr_confirm_recap(jo1.getString("ar_confirm_recap"));


                        data1.add(model1);
                    }
                    LogHelper.verbose(TAG, "result: " + AppConstant.RESULT);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return data1;
            }

        } catch (SocketTimeoutException e1) {
            e1.printStackTrace();
            LogHelper.verbose(TAG, "message: " + e1);
        } catch (InterruptedIOException e2) {
            e2.printStackTrace();
            LogHelper.verbose(TAG, "message: " + e2);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
            LogHelper.verbose(TAG, "message: " + e3);
        }
        return null;
    }


}
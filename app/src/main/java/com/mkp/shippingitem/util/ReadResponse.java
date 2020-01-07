package com.mkp.shippingitem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

public class ReadResponse {
    public static String readResponse(HttpsURLConnection conn) {
        try {
            InputStream input = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            String line = "";
            String data = "";
            while (line != null) {
                line = reader.readLine();
                data = data + line;
            }
            return (data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

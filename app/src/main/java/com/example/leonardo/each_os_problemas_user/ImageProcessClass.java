package com.example.leonardo.each_os_problemas_user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Marcos on 25/10/2017.
 */

public class ImageProcessClass {

    public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(20000);

            conn.setConnectTimeout(20000);

            conn.setRequestMethod("POST");

            conn.setDoInput(true);

            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(bufferedWriterDataFN(PData));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            int RC = conn.getResponseCode();

            if (RC == HttpsURLConnection.HTTP_OK) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                stringBuilder = new StringBuilder();

                String RC2;

                while ((RC2 = bufferedReader.readLine()) != null) {

                    stringBuilder.append(RC2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

        StringBuilder stringBuilder = new StringBuilder();
        boolean check = true;
        for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
            if (check)
                check = false;
            else
                stringBuilder.append("&");

            stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

            stringBuilder.append("=");

            stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
        }

        return stringBuilder.toString();
    }

}

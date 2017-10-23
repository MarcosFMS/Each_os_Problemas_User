package com.example.leonardo.each_os_problemas_user;

import android.util.Log;
import android.util.Pair;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by leonardo on 28/09/2017.
 */

public class UserFunctions {

    private final String STORE_USER_TAG = "store_user";

    private JSONParser jsonParser;

    //URL of the PHP API
    private static final String SERVER_URL = "http://192.168.3.8/Eacheosproblemas_server/android_index.php";

    // constructor
    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/

    private String sendPostDataString(JSONObject postDataParams){
        try {
            URL url = new URL(SERVER_URL); // here is your URL path

            Log.e("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    Log.d("response", line);
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    private String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public String storeUser(String nusp, String password, String email, String name) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("tag", STORE_USER_TAG);
            postDataParams.put("name", name);
            postDataParams.put("email", email);
            postDataParams.put("password", password);
            postDataParams.put("nusp", nusp);
            return sendPostDataString(postDataParams);
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject loginUserAluno(String login, String password) {
        JSONObject jsonn = null;
        try {

            URL url = new URL("http://34.209.238.147/RPII/android_index.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write("tag=sdsd&nusp=9402151&senha=123f321");
            writer.flush();
            writer.close();
            out.close();
                /*out.write("tag=farofa&nusp=9402151&senha=123f321".getBytes());
                out.flush();
                out.close();
                int responseCode=urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    //response+=line;
                    Log.e("hello", ""+responseCode);
                }
                }
                else {
                    //response="";
                    Log.e("Bye", ""+responseCode);

                }*/
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);

            byte[] contents = new byte[1024];

            /*int bytesRead = 0;
            String strFileContents="";
            while((bytesRead = in.read(contents)) != -1) {
                strFileContents += new String(contents, 0, bytesRead);
            }*/


            /*HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
            //httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            //httpConn.connect();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("tag=login&nusp=9402151&senha=123321");

            writer.flush();
            writer.close();
            os.close();
            int responseCode=httpConn.getResponseCode();
            Log.e("aaaa ",convertinputStreamToString(httpConn.getErrorStream()));
            InputStream is = httpConn.getInputStream();
            String parsedString = convertinputStreamToString(is);*/
            jsonn = new JSONObject("{\"success\":1}");
            //Log.e("hello", strFileContents);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            jsonn=new JSONObject("{\"success\":1}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.e("hello", jsonn.getString("success"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return jsonn;
    }

    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}



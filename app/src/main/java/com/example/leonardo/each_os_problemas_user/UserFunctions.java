package com.example.leonardo.each_os_problemas_user;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by leonardo on 28/09/2017.
 */

public class UserFunctions {

    private final String STORE_USER_TAG = "store_user", LOGIN_TAG = "login", STORE_PROBLEM_TAG = "store_problem", GET_USER_PROBLEM_TAG="get_user_problems";

    //URL of the PHP API
    public static final String SERVER_URL = "http://192.168.43.185/Eacheosproblemas_server/android_index.php";
    boolean check = true;

    // constructor
    public UserFunctions() {

    }

    /**
     * Function to Login
     **/


    private String sendPostDataString(JSONObject postDataParams) {
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

        while (itr.hasNext()) {

            String key = itr.next();
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
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String storeProblem(String nusp, String description, int type, String place, String imageTag, String imageData) {
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("tag", STORE_PROBLEM_TAG);
            postDataParams.put("nusp", nusp);
            postDataParams.put("description", description);
            postDataParams.put("image_name", imageTag);
            postDataParams.put("type", type);
            postDataParams.put("place", place);
            postDataParams.put("image_data", imageData);
            return sendPostDataString(postDataParams);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserProblems(String nusp){
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("tag", GET_USER_PROBLEM_TAG);
            postDataParams.put("nusp", nusp);
            return sendPostDataString(postDataParams);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String login(String nusp, String password) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("tag", LOGIN_TAG);
            postDataParams.put("password", password);
            postDataParams.put("nusp", nusp);
            return sendPostDataString(postDataParams);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNusp(Context context){
            SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference_key),context.MODE_PRIVATE);
            String nusp = sharedPref.getString(context.getString(R.string.nusp_key), null);
            return nusp;
    }

}



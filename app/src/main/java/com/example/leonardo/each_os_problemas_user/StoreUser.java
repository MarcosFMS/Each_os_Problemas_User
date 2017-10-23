package com.example.leonardo.each_os_problemas_user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class StoreUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_user);
    }


    protected void storeUser(View view){
        new StoreUser.ProcessStorage().execute();
    }



    private class ProcessStorage extends AsyncTask<String, String, String> {
        String nusp,password,email,name;

        @Override
        protected void onPreExecute() {
            EditText inputEmail = (EditText) findViewById(R.id.txtEmail);
            EditText inputPassword = (EditText) findViewById(R.id.txtPassword);
            EditText inputName = (EditText) findViewById(R.id.txtName);
            EditText inputNusp = (EditText) findViewById(R.id.txtNusp);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
            name = inputName.getText().toString();
            nusp= inputNusp.getText().toString();
        }

        @Override
        protected String doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            String response = userFunction.storeUser(nusp,password,email,name);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("response", response);
            try {
                JSONObject json = new JSONObject(response);
                Log.d("json", json.getString("success"));
                if(json.getInt("success") == 1){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

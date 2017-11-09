package com.example.leonardo.each_os_problemas_user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
//comment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int a = 0;
        Log.d("",a+"");
        Button bt_login = (Button) findViewById(R.id.btnLogin);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent Intent = new Intent(view.getContext(), AnActivity.class);
                view.getContext().startActivity(Intent);*/
                new ProcessLogin().execute();
            }
        });

    }

    protected void openStoreUserActivity(View view){
        Intent intent = new Intent(this, StoreUser.class);
        startActivity(intent);
    }

    private void storePreferences(String nusp, String name){
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.shared_preference_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.nusp_key), nusp);
        editor.putString(getString(R.string.name_key), name);
        editor.commit();
    }

    private class ProcessLogin extends AsyncTask<String, String, String> {
        String nusp,password;

        @Override
        protected void onPreExecute() {
            EditText inputPassword = (EditText) findViewById(R.id.txtPassword);
            EditText inputNusp = (EditText) findViewById(R.id.txtNusp);
            password = inputPassword.getText().toString();
            nusp= inputNusp.getText().toString();
        }

        @Override
        protected String doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            String response = userFunction.login(nusp,password);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("response", response);
            try {
                JSONObject json = new JSONObject(response);
                Log.d("json", json.getString("success"));
                if(json.getInt("success") == 1){
                    storePreferences(json.getJSONObject("user").getString("nusp"), json.getJSONObject("user").getString("name"));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Número USP ou senha incorretos!", Toast.LENGTH_SHORT);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Erro de conexão!", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }

    }

}

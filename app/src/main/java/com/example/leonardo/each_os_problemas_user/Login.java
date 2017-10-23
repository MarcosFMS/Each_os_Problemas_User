package com.example.leonardo.each_os_problemas_user;

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

    private class ProcessLogin extends AsyncTask<String, String, JSONObject> {
        String Login,password;

        @Override
        protected void onPreExecute() {
            EditText inputEmail = (EditText) findViewById(R.id.txtEmail);
            EditText inputPassword = (EditText) findViewById(R.id.txtPassword);
            Login = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.loginUserAluno(Login, password);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                Log.e("DATA= ",json.getString("success"));
                if (json.getString("success").equals("1"))
                {
                    Log.e("DATA= ","HERE");
                    JSONObject jsoon = json.getJSONObject("usuario");
                    /*AlunoHome.NomeAluno= jsoon.getString("Nome");
                    AlunoHome.TurmaAluno= jsoon.getString("Turma");
                    AlunoHome.IdAluno=jsoon.getString("Id");*/
                    Intent upanel = new Intent(com.example.leonardo.each_os_problemas_user.Login.this, HomePage.class);
                    upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(upanel);

                }
                else if (json.getString("error")=="0")
                {

                    Toast.makeText(getApplicationContext(), "Nome de usuario ou senha incorreta", Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

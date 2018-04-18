package com.ynov.aca.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.ynov.aca.IHttpResult;
import com.ynov.aca.R;
import com.ynov.aca.service.HttpService;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    IHttpResult mResultCallback = null;
    HttpService mHttpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Connexion");

        initHttp();
        mHttpService = new HttpService(mResultCallback,this);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        final EditText loginText = findViewById(R.id.loginText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final CheckBox boxRemember = findViewById(R.id.boxRemember);

        Button btnCheck = findViewById(R.id.btnCheck);
        Button btnReset = findViewById(R.id.btnReset);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = loginText.getText().toString();
                String password = passwordText.getText().toString();

                if(TextUtils.isEmpty(login)) {
                    loginText.setError(getResources().getString(R.string.login_empty_user));
                } else if(TextUtils.isEmpty(password)) {
                    passwordText.setError(getResources().getString(R.string.login_empty_password));
                } else {

                    if (boxRemember.isChecked()) {
                        sharedPreferences.edit()
                                .putBoolean("remember", true)
                                .putString("login", login)
                                .putString("password", password)
                                .apply();
                    } else {
                        sharedPreferences.edit()
                                .putBoolean("remember", false)
                                .apply();
                    }

                    mHttpService.getData("http://thibault01.com:8081/authorization?login=" + login + "&mdp=" + password);

                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginText.setText("");
                passwordText.setText("");
            }
        });
    }

    public void initHttp() {
        mResultCallback = new IHttpResult() {
            @Override
            public void httpSuccess(String requestType, String response) {
                Log.d("HttpLog", "HTTP String response: " + response);
                if(response.contentEquals("true")) {
                    // On passe à l'entité menu
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void httpSuccess(String requestType, JSONObject response) {
                Log.d("HttpLog", "HTTP JSON response: " + response);
            }

            @Override
            public void httpError(String requestType, VolleyError error) {
                Log.d("HttpLog", "HTTP error: " + error.getMessage());
            }
        };
    }

}


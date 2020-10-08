package com.example.diseasetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mTextEmail;
    private EditText mTextPassword;

    final String url = "https://lamp.ms.wits.ac.za/home/s1933028/login.php";
    final String url2 = "https://lamp.ms.wits.ac.za/home/s1933028/id.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPassword = (EditText) findViewById(R.id.edittext_pass);
    }


    public void moveToRegister(View view) {
        TextView mTextViewRegister = (TextView) findViewById(R.id.register);
        mTextViewRegister.setTextColor(Color.GRAY);
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    public void loginButton(View view) {
        String email = mTextEmail.getText().toString();
        String password = mTextPassword.getText().toString();

        new LoginUser().execute(email, password);
    }

    public class
    LoginUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("user_id", Email)
                    .add("user_password", Password)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    if (result.equalsIgnoreCase("Login Successful")) {
                        SharedPrefs.saveEmail(LoginActivity.this, Email);
                        SharedPrefs.savePassword(LoginActivity.this, Password);

                        String finalURL = url2 + "?userEmail=" + SharedPrefs.getEmail(LoginActivity.this);

                        OkHttpClient client1 = new OkHttpClient();

                        Request request1 = new Request.Builder()
                                .url(finalURL)
                                .build();

                        client1.newCall(request1).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    final String myResponse = response.body().string();

                                    JSONObject item = null;
                                    try {
                                        item = new JSONObject(myResponse);
                                        SharedPrefs.saveID(LoginActivity.this, item.getInt("USER_ID"));
                                        SharedPrefs.saveStatus(LoginActivity.this, item.getString("USER_STATUS"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();


                    } else if (result.equalsIgnoreCase("Login Failed1")) {
                        showToast("Email or Password mismatch!");
                    } else {
                        showToast("Unable to Log in");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, Text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}


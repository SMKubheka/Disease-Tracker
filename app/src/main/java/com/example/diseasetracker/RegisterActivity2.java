package com.example.diseasetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity2 extends AppCompatActivity {

    private EditText mTextPassword, getTextConfPassword;
    public TextView tv_1, tv_2, tv_3, tv_4;

    final String url = "https://lamp.ms.wits.ac.za/home/s1933028/register.php";
    final String url2 = "https://lamp.ms.wits.ac.za/home/s1933028/id.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mTextPassword = (EditText) findViewById(R.id.pass);
        getTextConfPassword = (EditText) findViewById(R.id.edittext_confpassword);

        tv_1 = (TextView) findViewById(R.id.textview_lower);
        tv_2 = (TextView) findViewById(R.id.textview_upper);
        tv_3 = (TextView) findViewById(R.id.textview_digit);
        tv_4 = (TextView) findViewById(R.id.textview_length);

        mTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = mTextPassword.getText().toString().trim();
                validatePassword(pass);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void validatePassword(String password){
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");

        if (!lowerCase.matcher(password).find()){
            tv_1.setVisibility(View.VISIBLE);
        }else{
            tv_1.setVisibility(View.INVISIBLE);
        }

        if (!upperCase.matcher(password).find()){
            tv_2.setVisibility(View.VISIBLE);
        }else{
            tv_2.setVisibility(View.INVISIBLE);
        }

        if (!digit.matcher(password).find()){
            tv_3.setVisibility(View.VISIBLE);
        }else{
            tv_3.setVisibility(View.INVISIBLE);
        }

        if(password.length() < 8){
            tv_4.setVisibility(View.VISIBLE);
        }else{
            tv_4.setVisibility(View.INVISIBLE);
        }

    }

    public void registerUser(View view) {

        if(tv_1.getVisibility() == View.VISIBLE || tv_2.getVisibility() == View.VISIBLE || tv_3.getVisibility() == View.VISIBLE || tv_4.getVisibility() == View.VISIBLE){

        }else{
            String first_name = SharedPrefs.getFName(RegisterActivity2.this);
            String last_name = SharedPrefs.getLName(RegisterActivity2.this);
            String email = SharedPrefs.getEmail(RegisterActivity2.this);
            String password = mTextPassword.getText().toString().trim();
            String cnf_password = getTextConfPassword.getText().toString().trim();

            new RegisterUser().execute(first_name, last_name, email, password, cnf_password);
        }
    }

    public class RegisterUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String First_Name = strings[0];
            String Last_Name = strings[1];
            String Email = strings[2];
            String Password = strings[3];
            String CnfPassword = strings[4];

            String finalURL = url + "?user_first_name=" + First_Name + "&user_last_name=" + Last_Name + "&user_id=" + Email + "&user_password=" + Password + "&user_cnfpassword=" + CnfPassword;

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .get()
                        .build();
                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();

                        if (result.equalsIgnoreCase("User registered successfully")) {
                            showToast("Registration Successful");

                            SharedPrefs.saveStatus(RegisterActivity2.this, "NEGATIVE");

                            String URL = url2 + "?userEmail=" + SharedPrefs.getEmail(RegisterActivity2.this);

                            OkHttpClient client1 = new OkHttpClient();

                            Request request1 = new Request.Builder()
                                    .url(URL)
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
                                            SharedPrefs.saveID(RegisterActivity2.this, item.getInt("USER_ID"));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            });


                            Intent i = new Intent(RegisterActivity2.this, SignUpCompleteActivity.class);
                            startActivity(i);
                            finish();
                        } else if (result.equalsIgnoreCase("User already exists")) {
                            showToast("User already exists");
                        } else if (result.equalsIgnoreCase("Invalid email")) {
                            showToast("Invalid email address");
                        } else if (result.equalsIgnoreCase("Passwords do not match")) {
                            showToast("Passwords do not match");
                        } else if (result.equalsIgnoreCase("Name, Email, or Password cannot be empty")) {
                            showToast("Name, Email or Password cannot be empty");
                        } else {
                            showToast("Cannot register at this time");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
                Toast.makeText(RegisterActivity2.this, Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}


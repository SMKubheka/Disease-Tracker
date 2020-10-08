package com.example.diseasetracker;


import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText mTextFName, mTextLName, mTextEmail, mTextPassword, getTextConfPassword;
    public TextView tv_1, tv_2, tv_3, tv_4;

    final String url = "https://lamp.ms.wits.ac.za/home/s1933028/register.php";
    final String url2 = "https://lamp.ms.wits.ac.za/home/s1933028/id.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextFName = (EditText) findViewById(R.id.FName);
        mTextLName = (EditText) findViewById(R.id.last_name);
        mTextEmail = (EditText) findViewById(R.id.email);


    }

    public void moveToLogin(View view) {
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(registerIntent);
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void registerNext(View view) {

        String fname = mTextFName.getText().toString().trim();
        String lname = mTextLName.getText().toString().trim();
        String email = mTextEmail.getText().toString().trim();

        if(fname.equals("") || lname.equals("") || email.equals("")){
            Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else{

            if(!isValidEmail(email)){
                Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            }else{
                SharedPrefs.saveFName(RegisterActivity.this, fname);
                SharedPrefs.saveLName(RegisterActivity.this, lname);
                SharedPrefs.saveEmail(RegisterActivity.this, email);

                Intent registerIntent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                startActivity(registerIntent);
            }



        }




    }

}

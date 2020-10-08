package com.example.diseasetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

public class CoverActivity extends AppCompatActivity {

    Button signup, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);

        if (SharedPrefs.getEmail(CoverActivity.this) != null && !SharedPrefs.getEmail(CoverActivity.this).equals("")){
            Intent i = new Intent(CoverActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else{

        }



    }

    public void signUp(View view){

        Intent registerIntent = new Intent(CoverActivity.this,RegisterActivity.class);
        startActivity(registerIntent);


    }
    public void login(View view){

        Intent registerIntent = new Intent(CoverActivity.this,LoginActivity.class);
        startActivity(registerIntent);


    }


}
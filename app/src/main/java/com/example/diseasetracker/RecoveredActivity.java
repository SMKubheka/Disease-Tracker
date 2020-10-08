package com.example.diseasetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.application.R;

import okhttp3.Request;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class RecoveredActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovered);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)( height*.6));
    }

    public void submitStatusN(View v) {

        String email = SharedPrefs.getEmail(RecoveredActivity.this);

        OkHttpClient client = new OkHttpClient();

        String preURL = "https://lamp.ms.wits.ac.za/home/s1933028/recovered.php";
        String url = preURL + "?user_id=" + email;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String myResponse = response.body().string();

                RecoveredActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (myResponse.equalsIgnoreCase("Recovery Updated")) {
                            Toast.makeText(RecoveredActivity.this, "Details sent", Toast.LENGTH_SHORT).show();
                        }
                        else if (myResponse.equalsIgnoreCase("Error sending details")) {
                            Toast.makeText(RecoveredActivity.this, "Error sending details", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });

        String status = "RECOVERED";
        SharedPrefs.saveStatus(RecoveredActivity.this, status);
    }
}



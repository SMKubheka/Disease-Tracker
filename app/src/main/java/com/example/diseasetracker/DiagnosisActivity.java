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

public class DiagnosisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    public void moveToCheckIn(View v) {

        Intent intent = new Intent(DiagnosisActivity.this, MainActivity.class);
        intent.putExtra("key", "value");
        startActivity(intent);
        finish();
    }

    public void submitStatus(View v) {

        String email = SharedPrefs.getEmail(DiagnosisActivity.this);

        OkHttpClient client = new OkHttpClient();

        String preURL = "https://lamp.ms.wits.ac.za/home/s1933028/diagnosis.php";
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

                DiagnosisActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (myResponse.equalsIgnoreCase("Success")) {
                            Toast.makeText(DiagnosisActivity.this, "Details sent", Toast.LENGTH_SHORT).show();
                        } else if (myResponse.equalsIgnoreCase("Success with no visit")) {
                            Toast.makeText(DiagnosisActivity.this, "Details sent", Toast.LENGTH_SHORT).show();
                        } else if (myResponse.equalsIgnoreCase("Error sending details")) {
                            Toast.makeText(DiagnosisActivity.this, "Error sending details", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        String status = "POSITIVE";
        SharedPrefs.saveStatus(DiagnosisActivity.this, status);

    }
}



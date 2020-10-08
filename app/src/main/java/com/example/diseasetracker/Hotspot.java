package com.example.diseasetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.application.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Hotspot extends AppCompatActivity {

    public void processJSON(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            LinearLayout l = (LinearLayout) findViewById(R.id.MainLL);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                HotspotLayout hl = new HotspotLayout(this);
                hl.populate(item);

                if (i % 2 == 0) {
                    hl.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
                l.addView(hl);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot);


        OkHttpClient client = new OkHttpClient();

        String url = "https://lamp.ms.wits.ac.za/home/s1933028/hotspot.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonStr = response.body().string();

                Hotspot.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        processJSON(jsonStr);
                    }
                });

            }
        });

    }

}

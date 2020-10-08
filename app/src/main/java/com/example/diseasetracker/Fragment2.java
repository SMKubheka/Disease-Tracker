package com.example.diseasetracker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.application.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Fragment2 extends Fragment {

    private TableLayout table;
    private TableRow t1;

    private TextView province;
    private TextView totalCases;
    private TextView deaths;
    private TextView recoveries;


    public void processJSONProvinces(View v, String json) {


        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                t1 = new TableRow(getContext());
                province = new TextView(getContext());
                totalCases = new TextView(getContext());
                deaths = new TextView(getContext());
                recoveries = new TextView(getContext());

                province.setText(item.getString("PROVINCE"));
                province.setGravity(Gravity.CENTER);
                totalCases.setText(item.getString("TOTAL_CASES"));
                totalCases.setGravity(Gravity.CENTER);
                deaths.setText(item.getString("DEATHS"));
                deaths.setGravity(Gravity.CENTER);
                recoveries.setText(item.getString("RECOVERIES"));
                recoveries.setGravity(Gravity.CENTER);


                t1.addView(province);
                t1.addView(totalCases);
                t1.addView(deaths);
                t1.addView(recoveries);

                table.addView(t1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment2, container, false);

        table = (TableLayout) view.findViewById(R.id.table1);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);
        table.setColumnStretchable(3, true);


        Button positive_button = (Button) view.findViewById(R.id.button_hot);
        positive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Hotspot.class);
                startActivity(in);
            }
        });

        OkHttpClient client = new OkHttpClient();

        String url = "https://lamp.ms.wits.ac.za/home/s1933028/locations.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonStr = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processJSONProvinces(view, jsonStr);
                    }
                });
            }
        });
        return view;
    }

}

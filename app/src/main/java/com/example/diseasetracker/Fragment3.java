package com.example.diseasetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.application.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment3 extends Fragment {

    TextView useUserStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        useUserStatus = (TextView) view.findViewById(R.id.userStatus);
        String userStat = SharedPrefs.getStatus(getActivity());

        if (userStat.equals("Positive") || userStat.equals("POSITIVE")) {
            userStat = "POSITIVE";
            useUserStatus.setText("YOUR STATUS IS: " + userStat);
        } else if (userStat.equals("Negative") || userStat.equals("NEGATIVE")) {
            userStat = "NEGATIVE";
            useUserStatus.setText("YOUR STATUS IS: " + userStat);
        } else if (userStat.equals("Recovered") || userStat.equals("RECOVERED")) {
            userStat = "RECOVERED";
            useUserStatus.setText("YOUR STATUS IS: " + userStat);
        }

        Button positive_button = (Button) view.findViewById(R.id.button_let_us_know);
        positive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = SharedPrefs.getEmail(getContext());

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

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (myResponse.equalsIgnoreCase("Success")) {
                                    String status = "POSITIVE";
                                    SharedPrefs.saveStatus(getActivity(), status);

                                    Fragment3.Update(getContext(), "POSITIVE");

                                    Intent in = new Intent(getActivity(), DiagnosisActivity.class);
                                    startActivity(in);

                                } else if (myResponse.equalsIgnoreCase("Error sending details")) {
                                    Toast.makeText(getActivity(), "Error sending details", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }
                });
            }
        });

        Button risk_button = (Button) view.findViewById(R.id.button_risk);
        risk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), RiskActivity.class);
                startActivity(in);
            }
        });

        Button resource_button = (Button) view.findViewById(R.id.button_resources);
        resource_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ResourceActivity.class);
                startActivity(in);
            }
        });

        Button recovered_button = (Button) view.findViewById(R.id.button_recovered);
        recovered_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = SharedPrefs.getEmail(getContext());

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

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (myResponse.equalsIgnoreCase("Recovery Updated")) {
                                    String status = "RECOVERED";
                                    SharedPrefs.saveStatus(getActivity(), status);

                                    Fragment3.Update(getContext(), "RECOVERED");

                                    Intent in = new Intent(getActivity(), RecoveredActivity.class);
                                    startActivity(in);
                                } else if (myResponse.equalsIgnoreCase("Error sending details")) {
                                    Toast.makeText(getActivity(), "Error sending details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

            }
        });


        return view;

    }

    public static void Update(Context context, String status) {
        TextView txtView = (TextView) ((Activity) context).findViewById(R.id.userStatus);
        txtView.setText("YOUR STATUS IS: " + status);
    }


}
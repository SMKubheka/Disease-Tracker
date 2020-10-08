package com.example.diseasetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

import okhttp3.Request;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_LONG;

public class RiskActivity extends AppCompatActivity {

    ArrayList<String> addressList;
    ArrayList<String> dateList;
    ArrayList<String> caseList;


    public void processCases(String caseAmount) {
        TextView rr = (TextView) findViewById(R.id.RiskResult);

        String convert = "";


        for (int i = 0; i < caseAmount.length(); i++) {
            if (caseAmount.charAt(i) != ' ' && caseAmount.charAt(i) != '\n') {
                convert += caseAmount.charAt(i);
            }
        }
        int inum = Integer.parseInt(convert);
        String riskLevel = "";
        if (inum == 0) {
            //riskLevel = "You are not at risk.";
            //Do nothing
        } else {
            riskLevel = "YOU ARE AT RISK";
            rr.setText(riskLevel);
        }


    }


    public void processJSON(String json) {

        try {

            JSONArray all = new JSONArray(json);
            TableLayout MainTL = (TableLayout) findViewById(R.id.TL);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);

            TextView addressSto = (TextView) findViewById(R.id.addressStorage);
            TextView dateSto = (TextView) findViewById(R.id.dateStorage);

            String addressStowage = "";
            String dateStowage = "";


            for (int i = 0; i < all.length(); i++) {
                JSONObject item = all.getJSONObject(i);
                String address = item.getString("LOCATION_NAME");
                String date = item.getString("VISIT_DATE");

                addressStowage = addressStowage + address + "#";
                dateStowage = dateStowage + date + "#";

                date += "   ";

                String compact = "";

                for (int j = 0; j < address.length(); j++) {

                    if (',' != address.charAt(j)) {
                        compact += address.charAt(j);
                    } else {
                        compact = compact + "  " + '\n';
                    }

                }
                compact += '\n';

                TableRow row = new TableRow(this);

                TextView dateTV = new TextView(this);
                TextView addressTV = new TextView(this);
                //TextView casesTV = new TextView(this);

                row.addView(dateTV);
                row.addView(addressTV);
                //row.addView(casesTV);

                dateTV.setText(date);
                addressTV.setText(compact);
                //casesTV.setText("test");


                dateTV.setGravity(Gravity.CENTER);
                addressTV.setGravity(Gravity.LEFT);
                //casesTV.setGravity(Gravity.CENTER);

                if (i % 2 == 0) {
                    row.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }

                MainTL.addView(row);
                row.setLayoutParams(lp);


            }

            addressSto.setText(addressStowage);
            dateSto.setText(dateStowage);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk);

        addressList = new ArrayList<String>();
        dateList = new ArrayList<String>();


        final int userID = SharedPrefs.getID(RiskActivity.this);
        String userid = String.valueOf(userID);
        String viewVisit = "https://lamp.ms.wits.ac.za/home/s1933028/viewvisit.php?userid=" + userid;


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(viewVisit)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    RiskActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            processJSON(myResponse);


                        }
                    });

                }
            }
        });


        final ImageButton info = (ImageButton) findViewById(R.id.infoButton);
        final Button risk_button = (Button) findViewById(R.id.risk);
        final TextView infoTV = (TextView) findViewById(R.id.infoText);
        infoTV.setVisibility(View.GONE);
        risk_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                TextView addressSto = (TextView) findViewById(R.id.addressStorage);
                TextView dateSto = (TextView) findViewById(R.id.dateStorage);

                String addressStowage = addressSto.getText().toString();
                String dateStowage = dateSto.getText().toString();

                String address = "";
                String date = "";

                for (int i = 0; i < addressStowage.length(); i++) {
                    if (addressStowage.charAt(i) == '#') {

                        addressList.add(address);
                        address = "";
                    } else {
                        address += addressStowage.charAt(i);
                    }

                }
                for (int i = 0; i < dateStowage.length(); i++) {

                    if (dateStowage.charAt(i) == '#') {

                        dateList.add(date);
                        date = "";
                    } else {
                        date += dateStowage.charAt(i);
                    }

                }

                int n = addressList.size();


                for (int i = 0; i < n; i++) {

                    String riskCheck = "https://lamp.ms.wits.ac.za/home/s1933028/riskcheck.php?date=" + dateList.get(i) + "&address=" + addressList.get(i);
                    OkHttpClient client1 = new OkHttpClient();

                    Request request1 = new Request.Builder()
                            .url(riskCheck)
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

                                RiskActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        processCases(myResponse);

                                    }
                                });
                            }
                        }
                    });

                }


                TextView rr = (TextView) findViewById(R.id.RiskResult);


                rr.setGravity(Gravity.CENTER);
                rr.setVisibility(View.VISIBLE);
                risk_button.setVisibility(View.GONE);
                info.setVisibility(View.VISIBLE);


            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent in = new Intent(RiskActivity.this, riskInfo.class);
                startActivity(in);


            }
        });


    }
}
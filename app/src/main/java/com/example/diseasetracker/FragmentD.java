package com.example.diseasetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.application.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentD extends Fragment {

    private TextView tvCases, tvRecovered, tvActive, tvTotalDeaths;
    public RequestQueue requestQueue;

    public static FragmentD newInstance() {
        FragmentD fragment = new FragmentD();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_main, container, false);

        tvCases = v.findViewById(R.id.tvCases);
        tvRecovered = v.findViewById(R.id.tvRecovered);
        tvActive = v.findViewById(R.id.tvActive);
        tvTotalDeaths = v.findViewById(R.id.tvTotalDeaths);

        httpGet();

        return v;

    }

    private void httpGet() {

        String url = "https://corona.lmao.ninja/v2/countries/south%20africa";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsingData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    private void parsingData(String json) {
        try {
            JSONObject item = new JSONObject(json);

            tvCases.setText(item.getString("cases"));
            tvRecovered.setText(item.getString("recovered"));
            tvActive.setText(item.getString("active"));
            tvTotalDeaths.setText(item.getString("deaths"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}




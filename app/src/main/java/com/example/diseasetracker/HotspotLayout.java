package com.example.diseasetracker;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class HotspotLayout extends LinearLayout {

    TextView LocationName;
    TextView PositiveVisits;

    public HotspotLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        LocationName = new TextView(context);
        PositiveVisits = new TextView(context);
        addView(LocationName);

        LinearLayout rightLayout = new LinearLayout(context);
        rightLayout.setOrientation(LinearLayout.VERTICAL);
        rightLayout.addView(PositiveVisits);

        PositiveVisits.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        PositiveVisits.setPadding(0, 0, 20, 0);

        addView(rightLayout);

    }

    public void populate(JSONObject jo) throws JSONException {
        LocationName.setText(jo.getString("LOCATION_NAME"));
        PositiveVisits.setText(jo.getString("POSITIVE_VISITS"));
    }
}



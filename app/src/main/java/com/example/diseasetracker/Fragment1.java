package com.example.diseasetracker;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import com.example.application.R;
import com.google.android.gms.maps.model.LatLng;


import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.LOCATION_SERVICE;

public class Fragment1 extends Fragment {

    String lat, lon, ymddate, AddressPHP, locationLat, locationLong;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView editDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String email = SharedPrefs.getEmail(getContext());


        final View view = inflater.inflate(R.layout.fragment1, container, false);


        final Button confirm_button = (Button) view.findViewById(R.id.confirmButton);
        final Button cancel_button = (Button) view.findViewById(R.id.cancel_Button);
        final ImageView check_inGPS = (ImageView) view.findViewById(R.id.check_in_GPS);
        final ImageView check_inAddress = (ImageView) view.findViewById(R.id.check_in_Address);
        final Button enterButton = (Button) view.findViewById(R.id.enterButton);
        final ImageButton infoButton = (ImageButton) view.findViewById(R.id.checkInfo);

        final EditText addressIn = (EditText) view.findViewById(R.id.address);
        final TextView pinText = (TextView) view.findViewById(R.id.pinText);
        final TextView targetText = (TextView) view.findViewById(R.id.targetText);
        editDate = (TextView) view.findViewById(R.id.editDate);

        final TextView addressText = (TextView) view.findViewById(R.id.addressText);
        final TextView addressView = (TextView) view.findViewById(R.id.addressView);
        final TextView coordinatesText = (TextView) view.findViewById(R.id.coordinatesText);
        final TextView coordinatesView = (TextView) view.findViewById(R.id.coordinatesView);
        final TextView notificationView = (TextView) view.findViewById(R.id.checkInView);

        confirm_button.setVisibility(View.GONE);
        cancel_button.setVisibility(View.GONE);
        addressIn.setVisibility(View.GONE);
        editDate.setVisibility(View.GONE);
        enterButton.setVisibility(View.GONE);
        infoButton.setVisibility(View.GONE);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), infoActivity.class);
                startActivity(in);
            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                editDate.setText(date);
                ymddate = date;
            }
        };

        confirm_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                addressView.setText("");
                coordinatesView.setText("");
                notificationView.setText("");

                String LocationPHP = "https://lamp.ms.wits.ac.za/home/s1933028/location.php?latitude=" + lat + "&longitude=" + lon + "&address=" + AddressPHP + "&userID=" + email;

                OkHttpClient client1 = new OkHttpClient();

                Request request1 = new Request.Builder()
                        .url(LocationPHP)
                        .build();


                client1.newCall(request1).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                        }
                    }

                });

                String urlPHP = "https://lamp.ms.wits.ac.za/home/s1933028/checkin.php?latitude=" + lat + "&longitude=" + lon + "&email=" + email + "&date=" + ymddate + "&address=" + AddressPHP;

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(urlPHP)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                        }
                    }

                });


              /*  String clearVisitPHP = "https://lamp.ms.wits.ac.za/home/s1933028/clearvisit.php?latitude=";

                OkHttpClient client2 = new OkHttpClient();

                Request request2 = new Request.Builder()
                        .url(clearVisitPHP)
                        .build();


                client2.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {

                        }
                    }

                });

               */


                confirm_button.setVisibility(View.GONE);
                cancel_button.setVisibility(View.GONE);
                addressIn.setVisibility(View.GONE);
                editDate.setVisibility(View.GONE);
                enterButton.setVisibility(View.GONE);
                check_inAddress.setVisibility(View.VISIBLE);
                pinText.setVisibility(View.VISIBLE);
                check_inGPS.setVisibility(View.VISIBLE);
                targetText.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.GONE);
                pinText.setText("Check in at current location");
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                confirm_button.setVisibility(View.GONE);
                cancel_button.setVisibility(View.GONE);
                infoButton.setVisibility(View.GONE);
                addressIn.setVisibility(View.GONE);
                editDate.setVisibility(View.GONE);
                enterButton.setVisibility(View.GONE);
                check_inAddress.setVisibility(View.VISIBLE);
                pinText.setVisibility(View.VISIBLE);
                check_inGPS.setVisibility(View.VISIBLE);
                targetText.setVisibility(View.VISIBLE);

                addressView.setText("");
                coordinatesView.setText("");
                notificationView.setText("");

                pinText.setText("Check in at current location");
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String AddressIN = addressIn.getText().toString();
                ymddate = editDate.getText().toString();

                addressIn.setText("");
                editDate.setText("");
                notificationView.setText("Check in at:");
                confirm_button.setVisibility(View.VISIBLE);
                cancel_button.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.VISIBLE);
                check_inGPS.setVisibility(View.INVISIBLE);
                pinText.setVisibility(View.INVISIBLE);


                Geocoder geocoder = new Geocoder(getActivity());
                List<Address> addresses = null;


                TextView t3 = (TextView) getView().findViewById(R.id.coordinatesText);
                TextView t2 = (TextView) getView().findViewById(R.id.addressText);
                t2.setText(AddressIN);


                try {
                    addresses = geocoder.getFromLocationName(AddressIN, 1);
                    AddressPHP = addresses.get(0).getAddressLine(0);
                    Address add = addresses.get(0);

                    addressView.setText(AddressPHP);

                    lat = new Double(add.getLatitude()).toString();
                    lon = new Double(add.getLongitude()).toString();

                    coordinatesView.setText("COORDINATES:   " + "(" + lat + "," + lon + ")   " + "Date:    " + ymddate);

                } catch (IOException e) {
                    e.printStackTrace();

                    addressView.setText("GEOCODE FAIL\n(click again to refresh)");
                }


            }
        });


        check_inAddress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirm_button.setVisibility(View.GONE);
                cancel_button.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.VISIBLE);
                enterButton.setVisibility(View.VISIBLE);
                addressIn.setVisibility(View.VISIBLE);
                editDate.setVisibility(View.VISIBLE);
                check_inGPS.setVisibility(View.INVISIBLE);
                targetText.setVisibility(View.VISIBLE);
                pinText.setVisibility(View.INVISIBLE);

                addressView.setVisibility(View.VISIBLE);
                coordinatesView.setVisibility(View.VISIBLE);
                notificationView.setVisibility(View.VISIBLE);

                addressView.setText("");
                coordinatesView.setText("");
                notificationView.setText("");


            }
        });

        check_inGPS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

                confirm_button.setVisibility(View.VISIBLE);
                cancel_button.setVisibility(View.VISIBLE);
                infoButton.setVisibility(View.VISIBLE);

                check_inAddress.setVisibility(View.INVISIBLE);
                targetText.setVisibility(View.INVISIBLE);

                addressView.setVisibility(View.VISIBLE);
                coordinatesView.setVisibility(View.VISIBLE);
                notificationView.setVisibility(View.VISIBLE);

                ymddate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


                notificationView.setText("Loading..." + "\n" + "click again to refresh");

                pinText.setText("Click again to refresh GPS");


                LocationManager locationManager;
                final long MIN_TIME = 1000; //1 second
                final long MIN_DISTANCE = 5; //meters
                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {


                        TextView coordinatesText = (TextView) view.findViewById(R.id.coordinatesText);
                        TextView addressText = (TextView) view.findViewById(R.id.addressText);


                        locationLat = new Double(location.getLatitude()).toString();
                        locationLong = new Double(location.getLongitude()).toString();

                        coordinatesText.setText("COORDINATES:     " + "(" + locationLat + ',' + locationLong + ")   " + "Date:   " + ymddate);


                        try {
                            Geocoder geo = new Geocoder(getActivity(), Locale.getDefault());

                            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            //List<Address> addresses = geo.getFromLocation(rosebank.latitude, rosebank.longitude, 1);
                            if (addresses.isEmpty()) {
                                addressText.setText("Waiting for Location");
                            } else {
                                if (addresses.size() > 0) {
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();

                                    addressText.setText(address);


                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            addressText.setText("Street Address could not be found (click again to refresh)");
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);

                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                AddressPHP = addressText.getText().toString();

                addressView.setText(AddressPHP);

                if (addressView.getText().toString() != "") {
                    notificationView.setText("Check in at:" + '\n' + "(Click again to refresh)");
                }


                lat = locationLat;
                lon = locationLong;
                coordinatesView.setText("COORDINATES:     " + "(" + lat + ',' + lon + ")   " + "Date:   " + ymddate);


            }
        });


        return view;
    }


}
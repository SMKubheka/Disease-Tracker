package com.example.diseasetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.application.R;
import com.google.android.material.navigation.NavigationView;


import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handling the navigation drawer toggle
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Fragment usage

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!isNetworkAvailable() == true) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        } else if (isNetworkAvailable() == true) {

            if (getIntent().getExtras() == null) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new FragmentD()).commit();
                    navigationView.setCheckedItem(R.id.navigation_dashboard);
                }
            } else {
                String value = getIntent().getStringExtra("key");
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                Fragment1 frag = new Fragment1();
                frag.setArguments(bundle);
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.navigation_map);
            }

        }
        if (!isNetworkAvailable()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        } else if (isNetworkAvailable()) {

            if (getIntent().getExtras() == null) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new FragmentD()).commit();
                    navigationView.setCheckedItem(R.id.navigation_dashboard);
                }
            } else {
                String value = getIntent().getStringExtra("key");
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                Fragment1 frag = new Fragment1();
                frag.setArguments(bundle);
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.navigation_map);
            }

        }


    }



    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                        return true;
                    }
                }
            }
        }


        return false;


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentD()).commit();
                break;
            case R.id.navigation_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment1()).commit();
                break;
            case R.id.navigation_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment2()).commit();
                break;
            case R.id.navigation_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment3()).commit();
                break;

            case R.id.navigation_share:
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Stay Home, Stay Safe and Protect South Africa. Download the SA COVID-19 Tracer app to " +
                        "make it easier to trace the virus. Available on the Google Play Store now.";
                String shareSub = "COVID-19";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
                break;
            case R.id.navigation_security:
                Intent i = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.logOut:
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
                SharedPrefs.savePassword(MainActivity.this, "");
                SharedPrefs.saveEmail(MainActivity.this, "");
                SharedPrefs.saveID(MainActivity.this, 0);
                SharedPrefs.saveStatus(MainActivity.this, "");
                Intent intent = new Intent(getApplicationContext(), CoverActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // DISABLE THE RETURN BUTTON

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


}


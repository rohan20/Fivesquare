package com.rohan.fivesquare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.location.places.ui.PlacePicker;

// TODO: 25-Jul-16 Detailed view of every venue
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();

        if (i != null) {
            if (i.getExtras() != null) {
                if (i.getStringExtra("") != null) {
                    if (i.getStringExtra("").equals("")) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment())
                                .addToBackStack(null)
                                .commit();

                        return;
                    }
                }
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SplashScreenFragment())
                    .addToBackStack(null)
                    .commit();
        }


    }

}

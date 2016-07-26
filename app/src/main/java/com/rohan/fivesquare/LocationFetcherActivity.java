package com.rohan.fivesquare;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.rohan.fivesquare.Utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class LocationFetcherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.current_location_text_view)
    TextView mCurrentLocationTextView;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private PlaceAutocompleteFragment mPlaceAutocompleteFragment;

    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderApi mFusedLocationProviderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_fetcher);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Needs location permission", Toast.LENGTH_SHORT).show();
            return;
        }

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setSmallestDisplacement(20);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(Constants.LOCATION_REFRESH_TIME);
        mLocationRequest.setFastestInterval(Constants.LOCATION_REFRESH_TIME);

        mFusedLocationProviderApi = LocationServices.FusedLocationApi;

        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        mCurrentLocationTextView = (TextView) findViewById(R.id.current_location_text_view);

        mCurrentLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(LocationFetcherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationFetcherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LocationFetcherActivity.this, "Needs location permission", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ActivityCompat.checkSelfPermission(LocationFetcherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationFetcherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 23)
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE_ASK_LOCATION_PERMISSION);
                    return;
                }

                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    Toast.makeText(LocationFetcherActivity.this, "", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationFetcherActivity.this);
                    builder.setMessage("Enable GPS?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });

                    builder.show();
                    Toast.makeText(LocationFetcherActivity.this, "Enable GPS for accurate location", Toast.LENGTH_SHORT).show();
                }

                if (mLocation == null) {
                    Toast.makeText(LocationFetcherActivity.this, "Location not available right now. Please search manually or try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Constants.MY_LATITIUDE, String.valueOf(mLocation.getLatitude()));
                editor.putString(Constants.MY_LONGITUDE, String.valueOf(mLocation.getLongitude()));

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(LocationFetcherActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    Toast.makeText(LocationFetcherActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                int maxAddressLines = addresses.get(0).getMaxAddressLineIndex();
                String address = "";
                for (int i = 0; i < maxAddressLines; i++)
                    address += (addresses.get(0).getAddressLine(i)) + " "; // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

//                            String city = addresses.get(0).getLocality();
//                            String state = addresses.get(0).getAdminArea();
//                            String country = addresses.get(0).getCountryName();
//                            String postalCode = addresses.get(0).getPostalCode();
//                            String knownName = addresses.get(0).getFeatureName();

                editor.putString(Constants.CURRENT_LOCATION_TEXT, address.toString());
                editor.apply();

                Intent i = new Intent(LocationFetcherActivity.this, MainActivity.class);
                i.putExtra("", "");
                Toast.makeText(LocationFetcherActivity.this, "Location Updated", Toast.LENGTH_SHORT).show();
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        mPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragment_place_autocomplete);

        mPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.MY_LATITIUDE, String.valueOf(place.getLatLng().latitude));
                editor.putString(Constants.MY_LONGITUDE, String.valueOf(place.getLatLng().longitude));
                editor.putString(Constants.CURRENT_LOCATION_TEXT, place.getAddress().toString());
                editor.apply();

                Intent i = new Intent(LocationFetcherActivity.this, MainActivity.class);
                i.putExtra("", "");
                Toast.makeText(LocationFetcherActivity.this, "Location Updated", Toast.LENGTH_SHORT).show();
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .build();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(LocationFetcherActivity.this, "Permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        mFusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case Constants.REQUEST_CODE_ASK_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                } else {
                    Toast.makeText(LocationFetcherActivity.this, "Requires location permission", Toast.LENGTH_SHORT).show();
                    return;
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
//        Toast.makeText(LocationFetcherActivity.this, "Location available now.", Toast.LENGTH_SHORT).show();
    }
}

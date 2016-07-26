package com.rohan.fivesquare;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rohan.fivesquare.Response.Response;
import com.rohan.fivesquare.Utils.Constants;
import com.rohan.fivesquare.Utils.MyLatLng;
import com.rohan.fivesquare.Utils.RESTAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.select_location_text_view_home_fragment)
    TextView mSelectLocationTextView;

    @BindView(R.id.food_icon)
    TextView mFoodCategory;
    @BindView(R.id.nightlife_icon)
    TextView mNightlifeCategory;
    @BindView(R.id.events_icon)
    TextView mEventsCategory;
    @BindView(R.id.movies_icon)
    TextView mMoviesCategory;
    @BindView(R.id.gym_icon)
    TextView mGymCategory;
    @BindView(R.id.hotels_icon)
    TextView mHotelsCategory;
    @BindView(R.id.main_relative_layout)
    RelativeLayout mMainLayout;

    private RESTAdapter restAdapter;
    private MyLatLng latLng;

    private ProgressDialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        restAdapter = new RESTAdapter(Constants.FOURSQUARE_BASE_URL_VENUES_LIST);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Fetching data...");

        mSelectLocationTextView.setOnClickListener(this);
        mFoodCategory.setOnClickListener(this);
        mNightlifeCategory.setOnClickListener(this);
        mEventsCategory.setOnClickListener(this);
        mMoviesCategory.setOnClickListener(this);
        mGymCategory.setOnClickListener(this);
        mHotelsCategory.setOnClickListener(this);

        if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) != null) {
            latLng = new MyLatLng(Double.parseDouble(getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null)), Double.parseDouble(getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LONGITUDE, null)));
//            Snackbar.make(mMainLayout, "Location Updated", Snackbar.LENGTH_LONG).show();
        }

        SharedPreferences mSharedPrefs = getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE);
        mSelectLocationTextView.setText(mSharedPrefs.getString(Constants.CURRENT_LOCATION_TEXT, Constants.SELECT_YOUR_LOCATION));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.select_location_text_view_home_fragment:

                int locationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
                if (locationPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_CODE_ASK_LOCATION_PERMISSION);
                } else {
                    getLocation();
                }

                break;

            case R.id.hotels_icon:

                if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) == null) {
                    Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Call<Response> requestHotels = restAdapter.getFoursquareAPI().getAPIResponse(
                        latLng.getLatLng(),
                        Constants.INTENT_VALUE,
                        Constants.HOTELS_CATEGORY_ID,
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LIMIT_VALUE,
                        Constants.RADIUS_VALUE,
                        Constants.LATEST_VERSION_API
                );

                requestHotels.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (!response.isSuccessful()) {
                            Snackbar.make(mMainLayout, "Unable to fetch data (Invalid response)", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Title", "Hotels");

                        VenuesListFragment venuesListFragment = new VenuesListFragment();
                        venuesListFragment.setArguments(b);

                        //set data here
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesListFragment)
                                .addToBackStack(null)
                                .commit();
                        dialog.hide();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.hide();
                    }
                });

                break;

            case R.id.gym_icon:

                if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) == null) {
                    Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Call<Response> requestGym = restAdapter.getFoursquareAPI().getAPIResponse(
                        latLng.getLatLng(),
                        Constants.INTENT_VALUE,
                        Constants.GYM_CATEGORY_ID,
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LIMIT_VALUE,
                        Constants.RADIUS_VALUE,
                        Constants.LATEST_VERSION_API
                );

                requestGym.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (!response.isSuccessful()) {
                            Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Title", "Gym");

                        VenuesListFragment venuesListFragment = new VenuesListFragment();
                        venuesListFragment.setArguments(b);

                        //set data here
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesListFragment)
                                .addToBackStack(null)
                                .commit();
                        dialog.hide();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.hide();
                    }
                });

                break;


            case R.id.movies_icon:

                if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) == null) {
                    Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Call<Response> requestMovies = restAdapter.getFoursquareAPI().getAPIResponse(
                        latLng.getLatLng(),
                        Constants.INTENT_VALUE,
                        Constants.MOVIES_CATEGORY_ID,
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LIMIT_VALUE,
                        Constants.RADIUS_VALUE,
                        Constants.LATEST_VERSION_API
                );

                requestMovies.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (!response.isSuccessful()) {
                            Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Title", "Movies");

                        VenuesListFragment venuesListFragment = new VenuesListFragment();
                        venuesListFragment.setArguments(b);

                        //set data here
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesListFragment)
                                .addToBackStack(null)
                                .commit();
                        dialog.hide();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.hide();
                    }
                });

                break;

            case R.id.events_icon:

                if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) == null) {
                    Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Call<Response> requestEvents = restAdapter.getFoursquareAPI().getAPIResponse(
                        latLng.getLatLng(),
                        Constants.INTENT_VALUE,
                        Constants.EVENTS_CATEGORY_ID,
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LIMIT_VALUE,
                        Constants.RADIUS_VALUE,
                        Constants.LATEST_VERSION_API
                );

                requestEvents.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (!response.isSuccessful()) {
                            Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Title", "Events");

                        VenuesListFragment venuesListFragment = new VenuesListFragment();
                        venuesListFragment.setArguments(b);

                        //set data here
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesListFragment)
                                .addToBackStack(null)
                                .commit();
                        dialog.hide();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.hide();
                    }
                });

                break;

            case R.id.nightlife_icon:

                if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) == null) {
                    Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Call<Response> requestNightLife = restAdapter.getFoursquareAPI().getAPIResponse(
                        latLng.getLatLng(),
                        Constants.INTENT_VALUE,
                        Constants.NIGHTLIFE_CATEGORY_ID,
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LIMIT_VALUE,
                        Constants.RADIUS_VALUE,
                        Constants.LATEST_VERSION_API
                );

                requestNightLife.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (!response.isSuccessful()) {
                            Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Title", "Nightlife");

                        VenuesListFragment venuesListFragment = new VenuesListFragment();
                        venuesListFragment.setArguments(b);

                        //set data here
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesListFragment)
                                .addToBackStack(null)
                                .commit();

                        dialog.hide();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.hide();
                    }
                });

                break;

            case R.id.food_icon:

                if (getActivity().getSharedPreferences(Constants.MY_SHARED_PREFS, Context.MODE_PRIVATE).getString(Constants.MY_LATITIUDE, null) == null) {
                    Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Call<Response> requestFood = restAdapter.getFoursquareAPI().getAPIResponse(
                        latLng.getLatLng(),
                        Constants.INTENT_VALUE,
                        Constants.FOOD_CATEGORY_ID,
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LIMIT_VALUE,
                        Constants.RADIUS_VALUE,
                        Constants.LATEST_VERSION_API
                );

                requestFood.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (!response.isSuccessful()) {
                            Snackbar.make(mMainLayout, "Please select location first", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Title", "Food");

                        VenuesListFragment venuesListFragment = new VenuesListFragment();
                        venuesListFragment.setArguments(b);

                        //set data here
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesListFragment)
                                .addToBackStack(null)
                                .commit();

                        dialog.hide();

                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.hide();
                    }
                });

                break;
        }
    }

    private void getLocation() {

        Intent i = new Intent(getActivity(), LocationFetcherActivity.class);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getActivity().startActivity(i);

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // TODO: 25-Jul-16 Instead of launching new activity, create alert dialog.


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case Constants.REQUEST_CODE_ASK_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Snackbar.make(mMainLayout, "Requires location permission", Snackbar.LENGTH_SHORT).show();
                }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}

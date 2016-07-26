package com.rohan.fivesquare.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rohan on 24-Jul-16.
 */
public class RESTAdapter {

    private FoursquareAPI foursquareAPI;

    public RESTAdapter(String base_url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        foursquareAPI = retrofit.create(FoursquareAPI.class);

    }

    public FoursquareAPI getFoursquareAPI() {
        return foursquareAPI;
    }

}

package com.rohan.fivesquare.Utils;

import com.rohan.fivesquare.Response.Response;
import com.rohan.fivesquare.Response.ResponseDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rohan on 24-Jul-16.
 */
public interface FoursquareAPI {

    @GET("search")
    Call<Response> getAPIResponse(
            @Query(Constants.LAT_LNG) String latlng,
            @Query(Constants.INTENT) String intentValue,
            @Query(Constants.CATEGORY_ID) String categoryID,
            @Query(Constants.CLIENT_ID) String clientID,
            @Query(Constants.CLIENT_SECRET) String clientSecret,
            @Query(Constants.LIMIT) String limit,
            @Query(Constants.RADIUS) String radius,
            @Query(Constants.VERSION) String version
    );

    @GET("venues/{venue_id}")
    Call<ResponseDetails> getVenueDetails(
            @Path(Constants.VENUE_ID) String venueID,
            @Query(Constants.CLIENT_ID) String clientID,
            @Query(Constants.CLIENT_SECRET) String clientSecret,
            @Query(Constants.VERSION) String version
    );

}

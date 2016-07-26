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

//venues:    ?ll=28.6330,77.1387&categoryId=4d4b7105d754a06374d81259&client_id=OUMVAROYJKNAXE04CRHGKWTZ5BDQAGLXHEQ02KRAPN2YJCAT&client_secret=DSQOOUFL2Z11O3P2EQ5E55LMXN1VOYAFSULXRSUU2M54LZWH&limit=10&radius=1000&v=20140828")
    //venue details: 501e52cde4b099ff7781306a?client_id=OUMVAROYJKNAXE04CRHGKWTZ5BDQAGLXHEQ02KRAPN2YJCAT&client_secret=DSQOOUFL2Z11O3P2EQ5E55LMXN1VOYAFSULXRSUU2M54LZWH&v=20160725
}

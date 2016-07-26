package com.rohan.fivesquare.Utils;

import com.rohan.fivesquare.BuildConfig;

/**
 * Created by Rohan on 24-Jul-16.
 */
public class Constants {

    public static final String MY_CLIENT_ID = BuildConfig.CLIENTID;
    public static final String MY_CLIENT_SECRET = BuildConfig.CLIENTSECRET;
    public static final int REQUEST_CODE_ASK_LOCATION_PERMISSION = 100;
    public static final int LOCATION_REFRESH_TIME = 60 * 60 * 1000;     //1hr
    public static final int LOCATION_REFRESH_DISTANCE = 0;     //any movement

    public static final String FOURSQUARE_BASE_URL_VENUES_LIST = "https://api.foursquare.com/v2/venues/";
    public static final String FOURSQUARE_BASE_URL_VENUE_DETAILS = "https://api.foursquare.com/v2/";
    public static final String SELECT_YOUR_LOCATION = "Select your location";
    public static final String MY_SHARED_PREFS = "sharedPrefs";
    public static final String MY_LATITIUDE = "latitude";
    public static final String MY_LONGITUDE = "longitude";
    public static final String CURRENT_LOCATION_TEXT = "location";

    public static final String FOOD_CATEGORY_ID = "4d4b7105d754a06374d81259";
    public static final String NIGHTLIFE_CATEGORY_ID = "4d4b7105d754a06376d81259";
    public static final String EVENTS_CATEGORY_ID = "4d4b7105d754a06373d81259";
    public static final String MOVIES_CATEGORY_ID = "4bf58dd8d48988d17f941735";
    public static final String GYM_CATEGORY_ID = "4bf58dd8d48988d175941735";
    public static final String HOTELS_CATEGORY_ID = "4bf58dd8d48988d1fa931735";

    //    ?ll=28.6330,77.1387&categoryId=4d4b7105d754a06374d81259&client_id=OUMVAROYJKNAXE04CRHGKWTZ5BDQAGLXHEQ02KRAPN2YJCAT&client_secret=DSQOOUFL2Z11O3P2EQ5E55LMXN1VOYAFSULXRSUU2M54LZWH&limit=10&radius=1000&v=20140828")
    public static final String LATEST_VERSION_API = "20140828";

    public static final String VENUE_ID = "venue_id";

    public static final String LAT_LNG = "ll";
    public static final String CATEGORY_ID = "categoryId";
    public static final String LIMIT = "limit";
    public static final String RADIUS = "radius";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String VERSION = "v";

}

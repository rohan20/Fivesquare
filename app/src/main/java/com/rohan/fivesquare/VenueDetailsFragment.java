package com.rohan.fivesquare;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rohan.fivesquare.Adapters.VenueImagesAdapter;
import com.rohan.fivesquare.Adapters.VenueTipsAdapter;
import com.rohan.fivesquare.Response.ResponseDetails;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenueDetailsFragment extends Fragment {

    TextView mDetailsNameTextView;
    TextView mDetailsAddressTextView;
    TextView mDetailsCategoryTextView;
    TextView mDetailsDistanceTextView;
    TextView mRatingTextView;
    ImageView mFoursquareImageView;
    ImageView mIconImageView;
    ImageView mCallImageView;
    TextView mLikesTextView;
    ImageView mNavigationImageView;

    public VenueDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venue_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();

        mDetailsNameTextView = (TextView) v.findViewById(R.id.details_name_text_view);
        mDetailsAddressTextView = (TextView) v.findViewById(R.id.details_address_text_view);
        mDetailsCategoryTextView = (TextView) v.findViewById(R.id.details_category_text_view);
        mDetailsDistanceTextView = (TextView) v.findViewById(R.id.details_distance_text_view);
        mRatingTextView = (TextView) v.findViewById(R.id.details_rating_text_view);
        mFoursquareImageView = (ImageView) v.findViewById(R.id.details_foursquare_link_image_view);
        mIconImageView = (ImageView) v.findViewById(R.id.details_icon_image_view);
        mCallImageView = (ImageView) v.findViewById(R.id.details_call_image_view);
        mLikesTextView = (TextView) v.findViewById(R.id.likes_number_text_view);
        mNavigationImageView = (ImageView) v.findViewById(R.id.details_navigation_image_view);

        Bundle b = getArguments();
        Gson gson = new Gson();

        String json = b.getString("Response");
        final ResponseDetails response = gson.fromJson(json, ResponseDetails.class);

        mDetailsNameTextView.setText(response.getResponse().getVenue().getName());
        mDetailsCategoryTextView.setText(response.getResponse().getVenue().getCategories().get(0).getName());
        mDetailsAddressTextView.setText("");
        for (int i = 0; i < response.getResponse().getVenue().getLocation().getFormattedAddress().size(); i++) {
            mDetailsAddressTextView.append(response.getResponse().getVenue().getLocation().getFormattedAddress().get(i) + " ");
        }

        mDetailsDistanceTextView.setText(b.getString("Distance") + " away");

        if (response.getResponse().getVenue().getRating() == null || response.getResponse().getVenue().getRating().equals(""))
            mRatingTextView.setText("N/A");
        else
            mRatingTextView.setText(response.getResponse().getVenue().getRating().toString());

        if (response.getResponse().getVenue().getLikes().getCount() == null || response.getResponse().getVenue().getLikes().getCount().equals(""))
            mLikesTextView.setText("N/A");
        else
            mLikesTextView.setText(response.getResponse().getVenue().getLikes().getCount().toString());

        Picasso.with(getActivity()).load(response.getResponse().getVenue().getCategories().get(0).getIcon().getPrefix() + "bg_64" + response.getResponse().getVenue().getCategories().get(0).getIcon().getPrefix()).placeholder(R.drawable.food).into(mIconImageView);

        TextView mEmptyTipsTextView = (TextView) v.findViewById(R.id.empty_tips_recycler_view_text_view);
        RecyclerView mRecyclerViewTips = (RecyclerView) v.findViewById(R.id.details_tips_recycler_view);

        if (response.getResponse().getVenue().getTips().getGroups().size() == 0) {
            mRecyclerViewTips.setVisibility(View.INVISIBLE);
            mEmptyTipsTextView.setVisibility(View.VISIBLE);
        } else if (response.getResponse().getVenue().getTips().getGroups().get(0).getItems().size() == 0) {
            mRecyclerViewTips.setVisibility(View.INVISIBLE);
            mEmptyTipsTextView.setVisibility(View.VISIBLE);
        } else {
            VenueTipsAdapter adapterTips = new VenueTipsAdapter(getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerViewTips.setLayoutManager(linearLayoutManager);
            mRecyclerViewTips.setAdapter(adapterTips);
            adapterTips.setList(response.getResponse().getVenue().getTips().getGroups().get(0).getItems());
        }

        TextView mEmptyPhotosTextView = (TextView) v.findViewById(R.id.empty_images_recycler_view_text_view);
        RecyclerView mRecyclerViewPhotos = (RecyclerView) v.findViewById(R.id.details_photos_recycler_view);

        if (response.getResponse().getVenue().getPhotos().getGroups().size() == 0) {
            mRecyclerViewPhotos.setVisibility(View.INVISIBLE);
            mEmptyPhotosTextView.setVisibility(View.VISIBLE);
        } else if (response.getResponse().getVenue().getPhotos().getGroups().get(0).getItems().size() == 0) {
            mRecyclerViewPhotos.setVisibility(View.INVISIBLE);
            mEmptyPhotosTextView.setVisibility(View.VISIBLE);
        } else {
            VenueImagesAdapter adapterPhotos = new VenueImagesAdapter(getActivity());
            LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewPhotos.setLayoutManager(horizontalLinearLayoutManager);
            mRecyclerViewPhotos.setAdapter(adapterPhotos);
            adapterPhotos.setList(response.getResponse().getVenue().getPhotos().getGroups().get(0).getItems());
        }


        if (response.getResponse().getVenue().getContact().getPhone() == null || response.getResponse().getVenue().getContact().getPhone().equals("")) {
            mCallImageView.setVisibility(View.GONE);
        } else {

            mCallImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + response.getResponse().getVenue().getContact().getPhone()));
                    startActivity(intent);
                }
            });
        }

        mFoursquareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(response.getResponse().getVenue().getCanonicalUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mNavigationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = response.getResponse().getVenue().getLocation().getLat();
                double longitude = response.getResponse().getVenue().getLocation().getLng();
                String label = response.getResponse().getVenue().getName() + ", " + response.getResponse().getVenue().getLocation().getCity();
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}

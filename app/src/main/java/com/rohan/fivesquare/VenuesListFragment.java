package com.rohan.fivesquare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rohan.fivesquare.Adapters.VenuesAdapter;
import com.rohan.fivesquare.Response.Response;
import com.rohan.fivesquare.Response.Venue;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenuesListFragment extends Fragment {

    TextView mTitleTextView;

    public VenuesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venues_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();
        mTitleTextView = (TextView) v.findViewById(R.id.fivesquare_title_text_view_venues_list);
        Bundle b = getArguments();

        Gson gson = new Gson();

        String json = b.getString("Response");
        Response response = gson.fromJson(json, Response.class);
        String title = b.getString("Title");

        mTitleTextView.setText(title);

        List<Venue> venuesList = response.getResponse().getVenues();
        List<VenueModel> requiredVenuesList = new ArrayList<>();
        for (Venue venue : venuesList) {
            VenueModel model = new VenueModel();
            model.setId(venue.getId());
            model.setName(venue.getName());
            if (venue.getLocation().getFormattedAddress().size() == 1) {
                model.setAddress(venue.getLocation().getFormattedAddress().get(0));
            } else {
                model.setAddress(venue.getLocation().getFormattedAddress().get(0) + " " + venue.getLocation().getFormattedAddress().get(1));
            }
            model.setCategory(venue.getCategories().get(0).getName().toString());
            model.setDistance(venue.getLocation().getDistance().toString() + " mtr");
            model.setIcon(venue.getCategories().get(0).getIcon().getPrefix() + "bg_64" + venue.getCategories().get(0).getIcon().getSuffix());

            requiredVenuesList.add(model);
        }

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.venues_recycler_view);
        VenuesAdapter adapter = new VenuesAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setList(requiredVenuesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}

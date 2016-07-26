package com.rohan.fivesquare.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rohan.fivesquare.Constants;
import com.rohan.fivesquare.MainActivity;
import com.rohan.fivesquare.R;
import com.rohan.fivesquare.RESTAdapter;
import com.rohan.fivesquare.Response.ResponseDetails;
import com.rohan.fivesquare.VenueDetailsFragment;
import com.rohan.fivesquare.VenueModel;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Rohan on 25-Jul-16.
 */
public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    Context mContext;
    private List<VenueModel> mVenues;

    public VenuesAdapter(Context context) {
        mContext = context;
    }

    /**
     * @param venues
     */
    public void setList(List<VenueModel> venues) {
        mVenues = venues;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.venue_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mName.setText(mVenues.get(position).getName());
        holder.mCategory.setText(mVenues.get(position).getCategory());
        holder.mAddress.setText(mVenues.get(position).getAddress());
        holder.mDistance.setText(mVenues.get(position).getDistance());
        Picasso.with(mContext).load(mVenues.get(position).getIcon()).placeholder(R.drawable.food).into(holder.mIcon);
//        holder.mIcon.setImageURI(Uri.parse(mVenues.get(position).getIcon()));
        if (position % 3 == 0)
            holder.mSparkButton.setChecked(true);
        else
            holder.mSparkButton.setChecked(false);

        holder.mVenueItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RESTAdapter restAdapter = new RESTAdapter(Constants.FOURSQUARE_BASE_URL_VENUE_DETAILS);
                Call<ResponseDetails> request = restAdapter.getFoursquareAPI().getVenueDetails(
                        mVenues.get(position).getId(),
                        Constants.MY_CLIENT_ID,
                        Constants.MY_CLIENT_SECRET,
                        Constants.LATEST_VERSION_API
                );

                request.enqueue(new Callback<ResponseDetails>() {
                    @Override
                    public void onResponse(Call<ResponseDetails> call, retrofit2.Response<ResponseDetails> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(mContext, "Error in fetching data. Please try later.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Bundle b = new Bundle();
                        b.putString("Response", new Gson().toJson(response.body()));
                        b.putString("Distance", mVenues.get(0).getDistance());

                        VenueDetailsFragment venuesDetailsFragment = new VenueDetailsFragment();
                        venuesDetailsFragment.setArguments(b);

                        //set data here
                        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, venuesDetailsFragment)
                                .addToBackStack(null)
                                .commit();

                    }

                    @Override
                    public void onFailure(Call<ResponseDetails> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mVenues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //        @BindView(R.id.name_text_view)
        TextView mName;
        //        @BindView(R.id.category_text_view)
        TextView mCategory;
        //        @BindView(R.id.address_text_view)
        TextView mAddress;
        //        @BindView(R.id.icon_image_view)
        ImageView mIcon;
        //        @BindView(R.id.distance_text_view)
        TextView mDistance;

        SparkButton mSparkButton;

        RelativeLayout mVenueItem;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.name_text_view);
            mCategory = (TextView) itemView.findViewById(R.id.category_text_view);
            mAddress = (TextView) itemView.findViewById(R.id.address_text_view);
            mIcon = (ImageView) itemView.findViewById(R.id.icon_image_view);
            mDistance = (TextView) itemView.findViewById(R.id.distance_text_view);
            mSparkButton = (SparkButton) itemView.findViewById(R.id.spark_button_favourite);
            mVenueItem = (RelativeLayout) itemView.findViewById(R.id.venue_item_relative_layout);

//            ButterKnife.bind(mContext, itemView);
        }
    }
}

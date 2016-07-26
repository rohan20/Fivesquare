package com.rohan.fivesquare.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rohan.fivesquare.R;
import com.rohan.fivesquare.Response.Item;
import com.rohan.fivesquare.Response.Photos;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rohan on 26-Jul-16.
 */
public class VenueImagesAdapter extends RecyclerView.Adapter<VenueImagesAdapter.ViewHolder> {

    Context mContext;
    List<Item> mPhotosList;

    public VenueImagesAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<Item> photosList) {
        mPhotosList = photosList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.venue_recycler_image_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(mContext).load(mPhotosList.get(position).getPrefix() + "100x100" + mPhotosList.get(position).getSuffix()).placeholder(R.drawable.placeholder).into(holder.mImage);

    }

    @Override
    public int getItemCount() {
        return mPhotosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.details_image_item);
        }
    }
}

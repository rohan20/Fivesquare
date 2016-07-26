package com.rohan.fivesquare.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohan.fivesquare.R;
import com.rohan.fivesquare.Response.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rohan on 25-Jul-16.
 */
public class VenueTipsAdapter extends RecyclerView.Adapter<VenueTipsAdapter.ViewHolder> {

    Context mContext;
    List<Item> mItemsList;

    public VenueTipsAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<Item> itemsList) {
        mItemsList = itemsList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.venue_recycler_tips_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(mContext).load(mItemsList.get(position).getUser().getUserPhoto().getPrefix() + "50x50" + mItemsList.get(position).getUser().getUserPhoto().getSuffix()).placeholder(R.drawable.placeholder).into(holder.mUserImageView);
        holder.mUserNameTextView.setText(mItemsList.get(position).getUser().getFirstName() + " " + mItemsList.get(position).getUser().getLastName());
        holder.mUserTipTextView.setText(mItemsList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mUserImageView;
        TextView mUserNameTextView;
        TextView mUserTipTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mUserImageView = (ImageView) itemView.findViewById(R.id.tips_user_image);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.tips_user_name);
            mUserTipTextView = (TextView) itemView.findViewById(R.id.tips_user_tip);
        }
    }
}

package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class TweetAdapter  extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context context;

    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.ivIcon);
        holder.tvTime.setText(tweet.getRelativeTimeAgo());
    }



    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivIcon;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTime;

        public ViewHolder (View itemView){
            super (itemView);

            // find views and assign them to our class variables
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }
}

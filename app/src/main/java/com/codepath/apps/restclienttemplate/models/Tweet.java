package com.codepath.apps.restclienttemplate.models;

import android.icu.text.SimpleDateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.util.Locale;


@Parcel
public class Tweet {
    // Attributes
    public Long uid;
    public String body;
    public String createdAt;
    public User user;


    public static Tweet fromJSON (JSONObject object) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = object.getString("text");
        tweet.uid = object.getLong("id");
        tweet.user = User.fromJSON(object.getJSONObject("user"));
        tweet.createdAt = object.getString("created_at");
        return tweet;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(createdAt).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }
}
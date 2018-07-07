package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.room.ColumnInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    @ColumnInfo
    public String name;

    // normally this field would be annotated @PrimaryKey but because this is an embedded object
    // it is not needed
    @ColumnInfo
    public long twitter_id;
    public String screenName;
    public String profileImageUrl;

    public static User fromJSON(JSONObject tweetJson) throws JSONException {

        User user = new User();

        user.twitter_id = tweetJson.getLong("id");
        user.name = tweetJson.getString("name");
        user.screenName = tweetJson.getString("screen_name");
        user.profileImageUrl = tweetJson.getString("profile_image_url");

        return user;
    }

}
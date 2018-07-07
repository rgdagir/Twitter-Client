package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.TwitterClient;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    static int REQUEST_CODE = 10;
    static int RESULT_CODE = 9;
    TwitterClient twClient;
    RecyclerView rvTweets;
    ArrayList<Tweet> tweets;
    TweetAdapter tweetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        twClient = TwitterApplication.getRestClient(this);

        // get RecyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        // initialize the data source
        tweets = new ArrayList<>();
        // construct adapter
        tweetAdapter = new TweetAdapter(tweets);
        // set up Adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(tweetAdapter);
        populateTimeline();
        Toast.makeText(getApplicationContext(), "Fetching tweets...", Toast.LENGTH_LONG).show();

    }

    private void populateTimeline() {
        twClient.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i< response.length(); i++) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    }
                    catch (JSONException e){
                        Log.d("Client ferrou", response.toString());
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failed throwable", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failed object", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Failed array", errorResponse.toString());
                throwable.printStackTrace();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent compose = new Intent (TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(compose, REQUEST_CODE);
        return true;
    }

    public synchronized void dataUpdated(Tweet newTweet) {
        tweets.add(0, newTweet);
        tweetAdapter.notifyItemChanged(0);
        rvTweets.scrollToPosition(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            Tweet newTweet = Parcels.unwrap(data.getParcelableExtra("newTweet"));
            dataUpdated(newTweet);
        }
    }
}

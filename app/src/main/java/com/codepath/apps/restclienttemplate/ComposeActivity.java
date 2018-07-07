package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    static int RESULT_CODE = 9;
    Button tweetBtn;
    TwitterClient twitterClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        final EditText simpleEditText = (EditText) findViewById(R.id.writeTweet);
//        simpleEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        tweetBtn = findViewById(R.id.tweetBtn);
        twitterClient = new TwitterClient(this);
        tweetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strValue = simpleEditText.getText().toString();
                twitterClient.postTweet(strValue, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Tweet newTweet = new Tweet();
                        Log.d("Tweeted!", response.toString());
                        try {
                            newTweet = newTweet.fromJSON(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent goBack = new Intent();
                        goBack.putExtra("newTweet", Parcels.wrap(newTweet));
                        setResult(RESULT_CODE, goBack);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("Posting tweet failed...", errorResponse.toString());
                    }
                });
            }
        });


    }
}

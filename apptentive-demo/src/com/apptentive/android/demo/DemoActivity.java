/*
 * Created by Sky Kelsey on 2011-05-30.
 * Copyright 2011 Apptentive, Inc. All rights reserved.
 */

package com.apptentive.android.demo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.FeedbackModule;
import com.apptentive.android.sdk.RatingModule;

public class DemoActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// BEGIN APPTENTIVE INITIALIZATION

		final Apptentive apptentive = Apptentive.getInstance();
		apptentive.setActivity(this);
		apptentive.setApiKey("0d7c775a973b30ed6a8ca2cf6469af3168a8c5e38ccd26755d1fdaa3387c6454");
		//apptentive.setApiKey("<YOUR_API_KEY>");
		apptentive.setAppDisplayName("Demo Activity");

		final RatingModule ratingModule = apptentive.getRatingModule();
		ratingModule.setDaysBeforePrompt(5);
		ratingModule.setUsesBeforePrompt(4);
		ratingModule.setSignificantEventsBeforePrompt(5);
		ratingModule.setDaysBeforeReprompting(10);
		// Bump uses each time the app starts.
		ratingModule.logUse();

		// Add custom data fields to feedback this way:
		final FeedbackModule feedbackModule = apptentive.getFeedbackModule();
		feedbackModule.addDataField("boo", "far");

		// END APPTENTIVE INITIALIZATION


		Button resetButton = (Button) findViewById(R.id.button_reset);
		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ratingModule.reset();
			}
		});
		Button eventButton = (Button) findViewById(R.id.button_event);
		eventButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ratingModule.logEvent();
			}
		});
		Button dayButton = (Button) findViewById(R.id.button_day);
		dayButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ratingModule.day();
			}
		});
		Button choiceButton = (Button) findViewById(R.id.button_choice);
		choiceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ratingModule.forceShowEnjoymentDialog(DemoActivity.this);
			}
		});
		Button ratingsButton = (Button) findViewById(R.id.button_ratings);
		ratingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ratingModule.showRatingDialog(DemoActivity.this);
			}
		});
		Button feedbackButton = (Button) findViewById(R.id.button_feedback);
		feedbackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				feedbackModule.forceShowFeedbackDialog(DemoActivity.this);
			}
		});
		Button surveyButton = (Button) findViewById(R.id.button_survey);
		surveyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				apptentive.getSurveyModule().show(DemoActivity.this);
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Log.e("DEMO", "onWindowFocusChanges(" + hasFocus + ")");
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			Apptentive.getInstance().getRatingModule().run(DemoActivity.this);
		}
	}
}

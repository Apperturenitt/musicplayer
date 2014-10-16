package com.apperture.shaketunes;

/**
 * Created by Pradeep on 15-10-2014.
 */

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;
public class parsehelp extends android.app.Application {

public parsehelp() {
        }

@Override
public void onCreate() {
        super.onCreate();

        // Initialize the Parse SDK.
        Parse.initialize(this, "YOUR_APP_ID", "YOUR_CLIENT_KEY");
    Parse.initialize(this, "8GKfgGBxjrjrA4RONYy5akGu2aqqRLZfb0T0dXX1", "sU4luC7WK8kq2bWsFhlIw6GYtdFbBXTq472RshCE");

    ParseInstallation.getCurrentInstallation().saveInBackground();
    if(ParseInstallation.getCurrentInstallation().containsKey("id"))
    Log.d("parse",  ParseInstallation.getCurrentInstallation().getString("id"));
    else Log.d("parse",  "not found id");

    //ParseInstallation.getCurrentInstallation()
        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, home.class);
        }
        }

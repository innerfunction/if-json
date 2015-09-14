package com.innerfunction.json.test;

import com.innerfunction.json.JSONValue;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

public class App extends Application {

    @SuppressLint("NewApi")
    public void onCreate() {
        super.onCreate();
        try {
            JSONValue.parseWithException("{\"a\":{\"b\":\"c\"}}");
        }
        catch(Exception e) {
            Log.e("App","Error starting application", e );
        }
    }

}

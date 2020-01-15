package com.trivia.lambatriviaapp.Custom_Class;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.trivia.lambatriviaapp.R;

import co.paystack.android.PaystackSdk;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        //**paystack payment
        PaystackSdk.initialize(getApplicationContext());
    }

}

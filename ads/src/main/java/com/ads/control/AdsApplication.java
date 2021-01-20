package com.ads.control;

import android.app.Application;

import java.util.List;

public abstract class AdsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Admod.getInstance().init(this, getListTestDeviceId());
        if(enableAdsResume()) {
            AppOpenManager.getInstance().init(this);
        }
    }

    public abstract boolean enableAdsResume();

    public abstract List<String> getListTestDeviceId();
}

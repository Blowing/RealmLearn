package com.wujie.jsonexample;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by wujie on 2018/7/16/016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

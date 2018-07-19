package com.wujie.kotlinexample

import android.app.Application
import io.realm.Realm

/**
 * Created by wujie on 2018/7/18/018.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
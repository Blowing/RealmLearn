package learn.realm.wujie.com.realmlearn;

import android.app.Application;
import android.support.multidex.MultiDex;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wujie on 2017-6-29.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        MultiDex.install(this);
    }
}

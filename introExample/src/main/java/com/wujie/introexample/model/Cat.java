package com.wujie.introexample.model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

/**
 * Created by wujie on 2018/7/17/017.
 */

public class Cat extends RealmObject {
    public String name;
    @LinkingObjects("cats")
    public final RealmResults<Person> owners = null;
}

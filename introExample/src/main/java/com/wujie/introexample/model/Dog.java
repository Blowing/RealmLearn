package com.wujie.introexample.model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

/**
 * Created by wujie on 2018/7/17/017.
 */

public class Dog extends RealmObject{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @LinkingObjects("dog")
    public final RealmResults<Person> owners = null;
}

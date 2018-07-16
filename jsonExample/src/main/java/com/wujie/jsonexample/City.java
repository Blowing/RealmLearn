package com.wujie.jsonexample;

import io.realm.RealmObject;

/**
 * Created by wujie on 2018/7/16/016.
 */

public class City extends RealmObject {
    private String name;
    private long votes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}

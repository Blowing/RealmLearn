package com.wujie.kotlinexample.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

/**
 * Created by wujie on 2018/7/18/018.
 */
class Cat : RealmObject() {
    var name: String? = null

    @LinkingObjects("cats")
    var owners: RealmResults<Person>? = null
}
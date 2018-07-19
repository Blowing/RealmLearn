package com.wujie.kotlinexample.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by wujie on 2018/7/18/018.
 */
open class Person : RealmObject() {

    @PrimaryKey
    var id: Long = 0

    var name: String = ""

    var dog: Dog? = null

    var cats: RealmList<Cat> = RealmList()

    @Ignore
    var tempRefrence: Int = 0
}
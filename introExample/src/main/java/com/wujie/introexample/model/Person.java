package com.wujie.introexample.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wujie on 2018/7/17/017.
 */

public class Person extends RealmObject{
    private int age;

    @Index
    private String name;

    @PrimaryKey
    private long id;

    private Dog dog;

    private RealmList<Cat> cats;

    private RealmList<String> phoneNumbers;

    private int temReference;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public RealmList<Cat> getCats() {
        return cats;
    }

    public void setCats(RealmList<Cat> cats) {
        this.cats = cats;
    }

    public RealmList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(RealmList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public int getTemReference() {
        return temReference;
    }

    public void setTemReference(int temReference) {
        this.temReference = temReference;
    }
}

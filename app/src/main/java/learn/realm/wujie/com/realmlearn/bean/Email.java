package learn.realm.wujie.com.realmlearn.bean;

import io.realm.RealmObject;

/**
 * Created by Troy on 2017-6-29.
 */

public class Email extends RealmObject {
    public String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean active;
}

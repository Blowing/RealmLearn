package learn.realm.wujie.com.realmlearn.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Troy on 2017-6-29.
 */

public class Contact extends RealmObject {
    public String name;
    public RealmList<Email> emails;
}

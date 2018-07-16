package learn.realm.wujie.com.realmlearn.bean;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Troy on 2017-6-29.
 */

public class Contact extends RealmObject {

    @Required
    public String name;
    private RealmList<Email> emails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Email> getEmails() {
        return emails;
    }

    public void setEmails(RealmList<Email> emails) {
        this.emails = emails;
    }
}

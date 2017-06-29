package learn.realm.wujie.com.realmlearn.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import learn.realm.wujie.com.realmlearn.R;
import learn.realm.wujie.com.realmlearn.bean.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //新建对象，并进行存储
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final User user = realm.createObject(User.class);
        user.setName("John");
        user.setAge(17);
        realm.commitTransaction();

        //复制一个对象到Realm数据库
        User user1 = new User();
        user1.setName("wujie");
        user1.setAge(78);
        realm.beginTransaction();
        realm.copyToRealm(user1);
        realm.commitTransaction();

        final User user2 = new User();
        user2.setAge(123);
        user2.setName("xcv");
        //使用事物块
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user2);
            }
        });

        //删除

        final RealmResults<User> users = realm.where(User.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user3 = users.get(0);
                user3.deleteFromRealm();
                users.deleteFirstFromRealm();
                users.deleteLastFromRealm();
                users.deleteFromRealm(1);
                users.deleteAllFromRealm();
            }
        });

        //改

        final User user4 = realm.where(User.class).equalTo("age", 18).findFirst();
        realm.beginTransaction();
        user4.setName("123");
        realm.commitTransaction();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                user4.setName("2345");
            }
        });

    }

    public List<User> queryAllUser() {
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<User> users = mRealm.where(User.class).findAll();
        return mRealm.copyToRealm(users);
    }

    public User findUser(int age) {
        Realm mRealm = Realm.getDefaultInstance();
        User user = mRealm.where(User.class).lessThan("age", 89).findFirst();
        return user;
    }

}

package com.wujie.introexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujie.introexample.model.Person;

import java.util.Arrays;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class IntroExampleActivity extends AppCompatActivity {


    private LinearLayout rootLayout;
    private Realm realm;

    private RealmResults<Person> persons;

    private final OrderedRealmCollectionChangeListener<RealmResults<Person>> realmChangeListener
            = new OrderedRealmCollectionChangeListener<RealmResults<Person>>() {
        @Override
        public void onChange(RealmResults<Person> people, OrderedCollectionChangeSet changeSet) {
            String insertions = changeSet.getInsertions().length == 0 ? "" : "\n - Insertions: " + Arrays.toString(changeSet.getInsertions());
            String deletions = changeSet.getDeletions().length == 0 ? "" : "\n - Deletions: " + Arrays.toString(changeSet.getDeletions());
            String changes = changeSet.getChanges().length == 0 ? "" : "\n - Changes: " + Arrays.toString(changeSet.getChanges());
            showStatus("Person was loaded, or written to. " + insertions + deletions + changes);
        }
    };

    private void showStatus(String s) {

        TextView textView = new TextView(this);
        textView.setText(s);
        rootLayout.addView(textView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_example);
        rootLayout = findViewById(R.id.container);
        rootLayout.removeAllViews();
        realm = Realm.getDefaultInstance();

        persons = realm.where(Person.class).findAllAsync();
        persons.addChangeListener(realmChangeListener);
        
        basicCRUD(realm);
        basicQuery(realm);

    }

    private void basicCRUD(Realm realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class, 1);
                person.setName("YOUNd PErSON");
                person.setAge(14);
                person.getPhoneNumbers().add("13541352828");
            }
        });

        final Person person = realm.where(Person.class).findFirst();
        showStatus(person.getName()+":"+person.getAge());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("zhubuaji");
                person.setAge(99);
                showStatus(person.getName()+":"+person.getAge());
            }
        });

        showStatus("Deleting all persons");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }


        private void basicQuery(Realm realm) {
            showStatus("\nPerforming basic Query operation...");

            // Let's add a person so that the query returns something.
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Person oldPern  = new Person();
                    oldPern.setId(2);
                    oldPern.setAge(47);
                    oldPern.setName("天天干");
                    realm.insertOrUpdate(oldPern);
                }
            });

            showStatus("Number of persons: " + realm.where(Person.class).count());

            RealmResults<Person> results = realm.where(Person.class).equalTo("age", 47).findAll();

            showStatus("Size of result set: " + results.size() + results.get(0).getName());
        }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        persons.removeAllChangeListeners();
        realm.close();
    }
}

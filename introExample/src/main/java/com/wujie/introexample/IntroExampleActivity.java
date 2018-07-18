package com.wujie.introexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujie.introexample.model.Cat;
import com.wujie.introexample.model.Dog;
import com.wujie.introexample.model.Person;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class IntroExampleActivity extends AppCompatActivity {


    private LinearLayout rootLayout;
    private Realm realm;

    private RealmResults<Person> persons;

    private final OrderedRealmCollectionChangeListener<RealmResults<Person>> realmChangeListener
            = new OrderedRealmCollectionChangeListener<RealmResults<Person>>() {
        @Override
        public void onChange(RealmResults<Person> people, OrderedCollectionChangeSet changeSet) {
            String insertions = changeSet.getInsertions().length == 0 ? "" : "\n - Insertions: "
                    + Arrays.toString(changeSet.getInsertions());
            String deletions = changeSet.getDeletions().length == 0 ? "" : "\n - Deletions: " +
                    Arrays.toString(changeSet.getDeletions());
            String changes = changeSet.getChanges().length == 0 ? "" : "\n - Changes: " + Arrays
                    .toString(changeSet.getChanges());
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
        basicLinkQuery(realm);
        new ComplexBackgroundOperation(this).execute();

    }

    private void basicCRUD(Realm realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class, 112);
                person.setName("YOUNd PErSON");
                person.setAge(14);
                person.getPhoneNumbers().add("13541352828");
            }
        });

        final Person person = realm.where(Person.class).findFirst();
        showStatus(person.getName() + ":" + person.getAge());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("zhubuaji");
                person.setAge(99);
                showStatus(person.getName() + ":" + person.getAge());
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
                Person oldPern = new Person();
                oldPern.setId(15);
                oldPern.setAge(47);
                oldPern.setName("天天干");
                realm.insertOrUpdate(oldPern);
            }
        });

        showStatus("Number of persons: " + realm.where(Person.class).count());

        RealmResults<Person> results = realm.where(Person.class).equalTo("age", 47).findAll();

        showStatus("Size of result set: " + results.size() + results.get(0).getName());
    }

    private void basicLinkQuery(Realm realm) {
        showStatus("\nPerforming basic Link Query operation...");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person catLady = realm.createObject(Person.class, 12);
                catLady.setName("马小雅");
                catLady.setAge(37);
                Cat maomi = realm.createObject(Cat.class);
                maomi.name = "guagua";
                catLady.getCats().add(maomi);
                showStatus("Number of persons: " + realm.where(Person.class).count());
                RealmResults<Person> results = realm.where(Person.class).equalTo("cats.name",
                        "guagua").findAll();
                showStatus("Size of set :" + results.size());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        persons.removeAllChangeListeners();
        realm.close();
    }

    private static class ComplexBackgroundOperation extends AsyncTask<Void, Void, String> {

        private WeakReference<IntroExampleActivity> weakReference;

        public ComplexBackgroundOperation(IntroExampleActivity introExampleActivity) {
            this.weakReference = new WeakReference<IntroExampleActivity>(introExampleActivity);
        }

        @Override
        protected void onPreExecute() {
            IntroExampleActivity activity = weakReference.get();
            if (activity == null) {
                return;
            }
            activity.showStatus("\n\nBeginning complex operations on background thread.");
        }

        @Override
        protected String doInBackground(Void... voids) {
            IntroExampleActivity activity = weakReference.get();
            if (activity == null) {
                return "";
            }

            Realm realm = Realm.getDefaultInstance();
            String info;
            info = activity.comPlexReadWrite(realm);
            info = info + activity.comPlexQuery(realm);
            return info;
        }

        @Override
        protected void onPostExecute(String s) {
            IntroExampleActivity activity = weakReference.get();
            if (activity == null) {
                return;
            }
            activity.showStatus(s);
        }
    }

    private String comPlexReadWrite(Realm realm) {
        String status = "\nPerforming complex Read/Write operation...";

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog tugou = realm.createObject(Dog.class);
                tugou.setName("黑狗");
                for (int i = 0; i < 10; i++) {
                    Person person = realm.createObject(Person.class, i+1);
                    person.setName("大王" + i);
                    person.setAge(i * 10);
                    person.setDog(tugou);
                    for (int j = 0; j < 10; j++) {
                        Cat cat = realm.createObject(Cat.class);
                        cat.name = "cat" + i;
                        person.getCats().add(cat);
                    }
                }
            }
        });
        status += "\nNumber of persons: " + realm.where(Person.class).count();

        for (Person p :
                realm.where(Person.class).findAll()) {
            String dogName;
            if (p.getDog() == null) {
                dogName = "None";
            } else {
                dogName = p.getDog().getName();
            }
            status += "\n" + p.getName() + ":" + p.getAge() + " : " + dogName + " : " +
                    p.getCats().size()+":";
        }
        RealmResults<Person> sortedPersons = realm.where(Person.class).sort("age", Sort.DESCENDING)
                .findAll();
        status += "\nSorting " + sortedPersons.last().getName() + " == " + realm.where(Person
                .class).findFirst()
                .getName();
        return status;
    }

    private String comPlexQuery(Realm realm) {
        String status = "\n\nPerforming complex Query operation...";
        status += "\nNumber of persons: " + realm.where(Person.class).count();
        RealmResults<Person> results = realm.where(Person.class).between("age", 18, 100)
                .findAll();
        status += "\nSize of result set: " + results.size();
        return status;
    }
}

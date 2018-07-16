package com.wujie.jsonexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class JsonExampleActivity extends AppCompatActivity {

    private GridView gridView;
    private CityAdapter adapter;
    private Realm realm;
    private RealmResults<City> cities;
    private RealmChangeListener<RealmResults<City>> realmChangeListener = new RealmChangeListener<RealmResults<City>>() {


        @Override
        public void onChange(RealmResults<City> cities) {
            adapter.setDatas(cities);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_example);

        Realm.deleteRealm(Realm.getDefaultConfiguration());
        realm = Realm.getDefaultInstance();

        gridView = findViewById(R.id.cites_list);

        cities = realm.where(City.class).findAllAsync();
        cities.addChangeListener(realmChangeListener);

        adapter = new CityAdapter();
        gridView.setAdapter(adapter);

        loadCitys();
    }

    private void loadCitys() {
        loadJsonFromStram();
        loadJsonFormJSObject();
        loadJsonFormString();
    }

    private void loadJsonFromStram() {

        try {
            InputStream in = getAssets().open("cities.json");
            realm.beginTransaction();
            realm.createAllFromJson(City.class, in);
            realm.commitTransaction();
        } catch (IOException e) {
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            e.printStackTrace();
        }
    }

    private void loadJsonFormJSObject() {
        final Map<String ,Object > map = new HashMap<>();
        map.put("name", "ziyang");
        map.put("votes", "120");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(City.class, new JSONObject(map));
            }
        });
    }

    private void loadJsonFormString () {
        final String json = "{ name: \"danshan\", votes: 145}";
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(City.class, json);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cities.removeAllChangeListeners();
        realm.close();
    }
}

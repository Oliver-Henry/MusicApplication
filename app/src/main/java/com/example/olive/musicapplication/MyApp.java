package com.example.olive.musicapplication;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by kalpesh on 08/02/2018.
 */

public class MyApp extends Application {

    private static MyApp sInstance;
    private static Context context;

    public static MyApp getInstance() {
        if (sInstance == null) {
            sInstance = new MyApp();
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("DayOutDatabase")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }



    public Context getAppContext(){
        return context;
    }

}

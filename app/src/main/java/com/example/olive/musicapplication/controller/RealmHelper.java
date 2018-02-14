package com.example.olive.musicapplication.controller;

import com.example.olive.musicapplication.data.network.model.Result;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by olive on 11/02/2018.
 */

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveMusic(final Result result){

        //Async

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(result);
            }
        });
    }

    public ArrayList<Result> getMusic(){

        ArrayList<Result> resultArrayList = new ArrayList<>();

        RealmResults<Result> realmResults= realm.where(Result.class).findAll();

        for(Result result: realmResults){
            resultArrayList.add(result);
        }
        return resultArrayList;
    }
}

package com.example.olive.musicapplication.data.network;

import com.example.olive.musicapplication.data.network.model.MusicModel;

import io.reactivex.Observable;

/**
 * Created by olive on 13/02/2018.
 */

public class AppDataManager implements IDataManager {
    IApiHelper iApiHelper;


    public AppDataManager() {
        iApiHelper = new AppApiHelper();
    }


    @Override
    public Observable<MusicModel> getRockList(String ApiKey) {
        return iApiHelper.getRockList(ApiKey);
    }
}


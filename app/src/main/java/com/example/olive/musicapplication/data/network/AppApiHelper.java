package com.example.olive.musicapplication.data.network;

import com.example.olive.musicapplication.data.network.model.MusicModel;
import com.example.olive.musicapplication.data.network.service.IRequestInterface;
import com.example.olive.musicapplication.data.network.service.ServiceConnection;

import io.reactivex.Observable;

/**
 * Created by olive on 13/02/2018.
 */

public class AppApiHelper implements IApiHelper {
    IRequestInterface iRequestInterface;


    public AppApiHelper() {
        iRequestInterface = ServiceConnection.getConnection();
    }

    @Override
    public Observable<MusicModel> getRockList(String ApiKey) {
        return iRequestInterface.getRockMusicList();
    }
}

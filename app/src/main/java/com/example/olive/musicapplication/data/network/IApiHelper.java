package com.example.olive.musicapplication.data.network;

import com.example.olive.musicapplication.data.network.model.MusicModel;

import io.reactivex.Observable;

/**
 * Created by olive on 13/02/2018.
 */

public interface IApiHelper {

    Observable<MusicModel> getRockList(String ApiKey);
}

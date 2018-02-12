package com.example.olive.musicapplication.service;

import com.example.olive.musicapplication.model.MusicModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by olive on 10/02/2018.
 */

public interface IRequestInterface {

    @GET(APIList.ROCK_API)
    Observable<MusicModel> getRockMusicList();

    @GET(APIList.CLASSIC_API)
    Observable<MusicModel> getClassicMusicList();

    @GET(APIList.POP_API)
    Observable<MusicModel> getPopMusicList();

}

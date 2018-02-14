package com.example.olive.musicapplication.rock_music;

import com.example.olive.musicapplication.data.network.model.MusicModel;
import com.example.olive.musicapplication.ui.base.MvpView;

/**
 * Created by olive on 13/02/2018.
 */

public interface IRockMusicMvpView extends MvpView {
    void onFetchDataProgress();
    void onFetchDataSuccess(MusicModel musicModel);
    void onFetchDataError(String error);
}

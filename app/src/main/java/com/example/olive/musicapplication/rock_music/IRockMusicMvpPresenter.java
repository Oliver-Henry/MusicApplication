package com.example.olive.musicapplication.rock_music;

import com.example.olive.musicapplication.ui.base.MvpPresenter;

/**
 * Created by olive on 13/02/2018.
 */

public interface IRockMusicMvpPresenter<V extends  IRockMusicMvpView> extends MvpPresenter<V> {
    void loadRockMusicList();
}

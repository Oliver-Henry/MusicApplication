package com.example.olive.musicapplication.rock_music;

import com.example.olive.musicapplication.data.network.IDataManager;
import com.example.olive.musicapplication.data.network.model.MusicModel;
import com.example.olive.musicapplication.data.network.service.APIList;
import com.example.olive.musicapplication.ui.base.BasePresenter;
import com.example.olive.musicapplication.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by olive on 13/02/2018.
 */

public class RockMusicPresenterImpl <V extends IRockMusicMvpView> extends BasePresenter<V> implements IRockMusicMvpPresenter<V> {

    public RockMusicPresenterImpl(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadRockMusicList() {

        getCompositeDisposable().add(getDataManager().getRockList(APIList.ROCK_API)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(new Consumer<MusicModel>() {
                       @Override
                       public void accept(MusicModel musicModel) throws Exception {
                           getMvpView().onFetchDataSuccess(musicModel);
                       }
                   },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().onFetchDataError(throwable.getMessage());
                    }
                }
        ));


    }

}

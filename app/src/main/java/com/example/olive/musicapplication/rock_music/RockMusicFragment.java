package com.example.olive.musicapplication.rock_music;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.olive.musicapplication.R;
import com.example.olive.musicapplication.controller.RealmHelper;
import com.example.olive.musicapplication.data.network.AppDataManager;
import com.example.olive.musicapplication.data.network.model.MusicModel;
import com.example.olive.musicapplication.data.network.model.Result;
import com.example.olive.musicapplication.ui.base.BaseFragment;
import com.example.olive.musicapplication.ui.utils.rx.AppSchedulerProvider;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class RockMusicFragment extends BaseFragment implements IRockMusicMvpView {

    //private CompositeDisposable compositeDisposable;
    //private IRequestInterface iRequestInterface;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RockMusicPresenterImpl<RockMusicFragment> rockMusicFragmentRockMusicPresenter;

    private Realm realm;
    private RealmHelper realmHelper;
    private ArrayList<Result> arrayList;
//    List<Result> results;


    public RockMusicFragment() {
        // Required empty public constructor
    }

    public void initRealm() {
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        arrayList = new ArrayList<>();
        arrayList = realmHelper.getMusic();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //iRequestInterface= ServiceConnection.getConnection();
        recyclerView=  view.findViewById(R.id.rVRock);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout= view.findViewById(R.id.swiperefreshRock);
        //compositeDisposable = new CompositeDisposable();
//        int position = getArguments().getInt("position", -1);
//        String previewURL = getArguments().getString("previewURL");
        initRealm();
        callService();
//        this.results = results;
        rockMusicFragmentRockMusicPresenter = new RockMusicPresenterImpl<>(new AppDataManager(), new AppSchedulerProvider(), new CompositeDisposable());
        rockMusicFragmentRockMusicPresenter.onAttach(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callService();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock_music, container, false);
    }
            // Check for network if online load from API else load from realm

    //todo: Progress dialog / back press pop-up "do you want to close app" / while offline text grey
    public void callService(){
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) {
                        if(isConnectedToInternet){
                            rockMusicFragmentRockMusicPresenter.loadRockMusicList();
                        }
                        else{
                            recyclerView.setAdapter(new RealmRockSongsAdapter(getActivity().getApplicationContext(), R.layout.row, arrayList));
                            Toast.makeText(getActivity(), "Not Connected to Network", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onFetchDataProgress() {
        showLoading();
    }

    @Override
    public void onFetchDataSuccess(MusicModel musicModel) {
        recyclerView.setAdapter(new RockSongsAdapter(getActivity().getApplicationContext(), musicModel.getResults(), R.layout.row));
        refreshLayout.setRefreshing(false);
        hideLoading();
    }

    @Override
    public void onFetchDataError(String error) {
        refreshLayout.setRefreshing(false);
        showMessage(error);
        hideLoading();
    }


//    public void saveToRealm(){
//
//        this.results = results;
//        Result result = new Result(results.get(position).getArtistName()){
//            saveMusic();
//        }
//    }



}

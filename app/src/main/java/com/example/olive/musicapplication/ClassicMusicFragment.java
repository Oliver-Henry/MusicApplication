package com.example.olive.musicapplication;


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

import com.example.olive.musicapplication.model.MusicModel;
import com.example.olive.musicapplication.service.IRequestInterface;
import com.example.olive.musicapplication.service.ServiceConnection;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassicMusicFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    private IRequestInterface iRequestInterface;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;


    public ClassicMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRequestInterface= ServiceConnection.getConnection();
        recyclerView=  view.findViewById(R.id.rVClassic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout= view.findViewById(R.id.swiperefreshClassic);
        compositeDisposable = new CompositeDisposable();
        callService();
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
        return inflater.inflate(R.layout.fragment_classic_music, container, false);
    }


    public void callService(){
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isConnectedToInternet) {
                        if(isConnectedToInternet){
                            displayClassicSongs();
                        }
                        else{
                            Toast.makeText(getActivity(), "Not Connected to Network", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(compositeDisposable!=null && !compositeDisposable.isDisposed()){
            compositeDisposable.clear();
        }
    }

    public void displayClassicSongs(){
        compositeDisposable.add(
                iRequestInterface.getClassicMusicList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<MusicModel>() {
                            @Override
                            public void accept(MusicModel musicModel) throws Exception {
                                recyclerView.setAdapter(new ClassicSongsAdapter(getActivity().getApplicationContext(), this, musicModel.getResults(), R.layout.row));
                                refreshLayout.setRefreshing(false);
                            }
                        }, (Throwable throwable) -> {
                            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show(); // test error
                            refreshLayout.setRefreshing(false);
                        }));
    }

}

package com.example.olive.musicapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olive.musicapplication.model.MusicModel;
import com.example.olive.musicapplication.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by olive on 11/02/2018.
 */

class PopSongsAdapter extends RecyclerView.Adapter<PopSongsAdapter.MyViewHolder> {
    Context applicationContext;
    Consumer<MusicModel> consumer;
    List<Result> results;
    int row;

    public PopSongsAdapter(Context applicationContext, Consumer<MusicModel> consumer, List<Result> results, int row) {
        this.applicationContext = applicationContext;
        this.consumer = consumer;
        this.results = results;
        this.row = row;
    }

    @Override
    public PopSongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(row, parent, false));
    }

    @Override
    public void onBindViewHolder(PopSongsAdapter.MyViewHolder holder, int position) {
        holder.txtCollectionName.setText(results.get(position).getCollectionName());
        holder.txtArtistName.setText(results.get(position).getArtistName());

        if(results.get(position).getTrackPrice() == null){holder.txtPrice.setText("N/A");}
        else{holder.txtPrice.setText(results.get(position).getTrackPrice().toString() + " USD");}

        String url = results.get(position).getArtworkUrl60();
        Picasso.with(applicationContext).load(url).resize(230, 230).centerCrop().into(holder.imgView);

        holder.callItemClick(new OnSongSelectedListener() {

            @Override
            public void OnClick(View view, int position) {
                // Toast.makeText(applicationContext, "Click", Toast.LENGTH_LONG).show();
                playMedia();
            }


            Uri preview = Uri.parse(results.get(position).getPreviewUrl());

            public void playMedia() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(String.valueOf(preview)));
                if (intent.resolveActivity(applicationContext.getPackageManager()) != null) {
                    applicationContext.startActivity(intent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtCollectionName;
        private TextView txtArtistName;
        private TextView txtPrice;
        private ImageView imgView;
        private OnSongSelectedListener onSongSelectedListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtCollectionName =itemView.findViewById(R.id.tVCollectionName);
            txtArtistName =itemView.findViewById(R.id.tVArtistName);
            txtPrice =itemView.findViewById(R.id.tVPrice);
            imgView =itemView.findViewById(R.id.imgV);
            itemView.setOnClickListener(this);
        }

        public void callItemClick(OnSongSelectedListener onSongSelectedListener) {
            this.onSongSelectedListener= onSongSelectedListener;
        }

        @Override
        public void onClick(View view) {
            onSongSelectedListener.OnClick(view, getPosition());
        }
    }
}

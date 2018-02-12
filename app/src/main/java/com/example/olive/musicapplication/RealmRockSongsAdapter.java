package com.example.olive.musicapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olive.musicapplication.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by olive on 12/02/2018.
 */

class RealmRockSongsAdapter extends RecyclerView.Adapter<RealmRockSongsAdapter.MyViewHolder> {
    Context applicationContext;
    int row;
    ArrayList<Result> arrayList;
    public RealmRockSongsAdapter(Context applicationContext, int row, ArrayList<Result> arrayList) {
        this.applicationContext = applicationContext;
        this.row = row;
        this.arrayList = arrayList;
    }

    @Override
    public RealmRockSongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(row, parent, false));

    }

    @Override
    public void onBindViewHolder(RealmRockSongsAdapter.MyViewHolder holder, int position) {
        holder.txtCollectionName.setText(arrayList.get(position).getCollectionName());
        holder.txtArtistName.setText(arrayList.get(position).getArtistName());

        if(arrayList.get(position).getTrackPrice() == null){holder.txtPrice.setText("N/A");}
        else{holder.txtPrice.setText(arrayList.get(position).getTrackPrice().toString() + " USD");}

        String url = arrayList.get(position).getArtworkUrl60();
        Picasso.with(applicationContext).load(url).resize(230, 230).centerCrop().into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCollectionName;
        private TextView txtArtistName;
        private TextView txtPrice;
        private ImageView imgView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtCollectionName =itemView.findViewById(R.id.tVCollectionName);
            txtArtistName =itemView.findViewById(R.id.tVArtistName);
            txtPrice =itemView.findViewById(R.id.tVPrice);
            imgView =itemView.findViewById(R.id.imgV);
        }
    }
}

/*
  DANA.id
  PT. Espay Debit Indonesia Koe.
  Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.example.movie.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.movie.Database.Database;
import com.example.movie.Model.MovieModel;
import com.example.movie.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ykhdzr (yoko.ahadazaro@dana.id)
 * @version AdapterFavorite.java, v 0.1 3/9/2021 9:06 PM by ykhdzr
 */
public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder>{

    private ArrayList<MovieModel> arrMovie;
    private Context mCtx;
    Database db;
    Cursor cursor;

    public AdapterFavorite(Context mCtx,Cursor cursor){
        this.mCtx=mCtx;
        this.cursor=cursor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View v = LayoutInflater.from(mCtx).inflate(R.layout.activity_movie_view,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavorite.MyViewHolder holder,int position){
        db = new Database(mCtx);

        if(!cursor.moveToPosition(position)){
            return;
        }

        final String id = cursor.getString((cursor.getColumnIndex(db.MOVIE_ID)));
        final String image = cursor.getString((cursor.getColumnIndex(db.MOVIE_IMAGE)));
        final String title = cursor.getString((cursor.getColumnIndex(db.MOVIE_TITLE)));
        final String releasedate = cursor.getString((cursor.getColumnIndex(db.MOVIE_RELESASEDATE)));
        final String rating = cursor.getString((cursor.getColumnIndex(db.MOVIE_RATING)));
        final String overview = cursor.getString((cursor.getColumnIndex(db.MOVIE_OVERVIEW)));

        Glide.with(mCtx).load(image).centerInside().into(holder.iv_movie);

    }

    @Override
    public int getItemCount(){
        return cursor.getCount();
    }


     class MyViewHolder extends RecyclerView.ViewHolder{
         ImageView iv_movie;
         CardView cv_movie;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            iv_movie = itemView.findViewById(R.id.iv_movie);
            cv_movie = itemView.findViewById(R.id.cv_movie);
        }
    }
}
package com.example.movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.Activity.MovieDetailActivity;
import com.example.movie.Model.MovieModel;
import com.example.movie.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<MovieModel> arrMovie;
    private Context mCtx;

    public Adapter(ArrayList<MovieModel> arrMovie, Context mCtx) {
        this.arrMovie = arrMovie;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.activity_movie_view,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movie = arrMovie.get(position);

        String poster_path = movie.getPoster_path();
        Glide.with(mCtx).load(poster_path).centerInside().into(holder.iv_movie);

        holder.cv_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(mCtx,MovieDetailActivity.class);
                detailIntent.putExtra("title_movieDetail",movie.getTitle());
                detailIntent.putExtra("posterpath_movieDetail",movie.getPoster_path());
                detailIntent.putExtra("releasedate_movieDetail",movie.getRelease_date());
                detailIntent.putExtra("voteaverage_movieDetail",movie.getVote_average());
                detailIntent.putExtra("overview_movieDetail",movie.getOverview());
                detailIntent.putExtra("movieId_movieDetail",movie.getId());
                //detailIntent.putExtra("runtime_movieDetail",movie.getRuntime());
                mCtx.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrMovie.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_movie;
        CardView cv_movie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_movie = itemView.findViewById(R.id.iv_movie);
            cv_movie = itemView.findViewById(R.id.cv_movie);
        }
    }
}

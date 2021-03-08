package com.example.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<MovieModel> arrMovie;
    private Context mCtx;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

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

        final String poster_path = movie.getPoster_path();

        Glide.with(mCtx).load(poster_path).centerInside().into(holder.iv_movie);
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

package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<MovieModel> arrMovie;
    RecyclerView rvMovie;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovie = findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new GridLayoutManager(this,2));

        arrMovie = new ArrayList<>();
        GetRetrofitResponse();

//        layoutManager = new LinearLayoutManager(this);

    }

    public void GetRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .popularMovie(Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200) {
                    Log.v("Tag", "the response" + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie : movies) {
//                        Log.v("Tag", "The release date" + movie.getRelease_date());
//                        Log.v("Tag", "Title" + movie.getTitle());
//                        Log.v("Tag", "Overview" + movie.getOverview());
                        String title = movie.getTitle();
                        String poster = Credentials.IMAGE_URL+movie.getPoster_path();
                        String release_date = movie.getRelease_date();
                        int movie_id = movie.getMovie_id();
                        Double vote_average = movie.getVote_average();
                        String overview = movie.getOverview();
                        //Log.v("Tag", "Overview" + overview );
                        int runtime = movie.getRuntime();

                        arrMovie.add(new MovieModel(title,poster,release_date,movie_id,vote_average,overview,runtime));
                    }
                    mAdapter = new Adapter(arrMovie, MainActivity.this);
                    rvMovie.setAdapter(mAdapter);
                } else {
                    Log.v("Tag", "Error"+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

}
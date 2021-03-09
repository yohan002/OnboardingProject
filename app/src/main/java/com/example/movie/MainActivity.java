package com.example.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    int flag = 0;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovie = findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new GridLayoutManager(this,2));

        arrMovie = new ArrayList<>();

        GetRetrofitResponse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                flag = 0;
                arrMovie.clear();
                GetRetrofitResponse();
                Toast.makeText(this,"Popular Movie",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                flag = 1;
                arrMovie.clear();
                GetRetrofitResponse();
                Toast.makeText(this,"Top Rated Movie",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this,"Favorite Movie",Toast.LENGTH_SHORT).show();
                arrMovie.clear();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = null;
                
        if (flag == 0){
            responseCall = movieApi.popularMovie(Credentials.API_KEY);
        }else if(flag == 1){
            responseCall = movieApi.topratedMovie(Credentials.API_KEY);
        }
        
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
                        arrMovie.add(new MovieModel(title,poster,release_date,movie_id,vote_average,overview));
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
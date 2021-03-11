package com.example.movie.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movie.Adapter.Adapter;
import com.example.movie.Adapter.AdapterFavorite;
import com.example.movie.Database.Database;
import com.example.movie.Utils.Credentials;
import com.example.movie.Utils.MovieApi;
import com.example.movie.Model.MovieModel;
import com.example.movie.Response.MovieSearchResponse;
import com.example.movie.R;
import com.example.movie.Request.Servicey;

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
    Database db;
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
                FavoriteAdapter();
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
                        String title = movie.getTitle();
                        String poster = Credentials.IMAGE_URL+movie.getPoster_path();
                        String release_date = movie.getRelease_date();
                        int movie_id = movie.getId();
                        Double vote_average = movie.getVote_average();
                        String overview = movie.getOverview();
                        int runtime = 0;
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

    void FavoriteAdapter(){
        db = new Database(this);
        mAdapter = new AdapterFavorite(MainActivity.this,db.getAllData());
        rvMovie.setAdapter(mAdapter);
    }
}
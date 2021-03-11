package com.example.movie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movie.Database.Database;
import com.example.movie.R;
import com.example.movie.Request.Servicey;
import com.example.movie.Response.MovieResponse;
import com.example.movie.Utils.Credentials;
import com.example.movie.Utils.MovieApi;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView iv_movie;
    TextView tv_title,tv_release_date,tv_rating,tv_description,tv_runtime;
    Button btn_favorite;

    int flagMovieDetail = 0;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        db = new Database(this);

        iv_movie = findViewById(R.id.iv_movie);
        tv_title = findViewById(R.id.tv_title);
        tv_release_date = findViewById(R.id.tv_release_date);
        tv_runtime = findViewById(R.id.tv_runtime);
        tv_rating = findViewById(R.id.tv_rating);
        tv_description = findViewById(R.id.tv_description);
        btn_favorite = findViewById(R.id.btn_favorite);

        getMovie();

        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = String.valueOf(btn_favorite.getText());
                if (temp.equals("MARK AS FAVORITE")){
                    flagMovieDetail = 1;
                    getMovie();
                }else if(temp.equals("UNMARK AS FAVORITE")){
                    flagMovieDetail = 2;
                    getMovie();
                }
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void GetRetrofitResponse(int id) {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieResponse> responseCall = movieApi
                .detailMovie(
                        id,Credentials.API_KEY);
        Log.v("Tag", "ID"+id);

        responseCall.enqueue(new Callback<MovieResponse>(){
            @Override
            public void onResponse(Call<MovieResponse> call,Response<MovieResponse> response){
                if(response.code() == 200){
                    tv_runtime.setText(String.valueOf(response.body().getRuntime()));
                    return;
                }else{
                    Log.v("Tag", "Error"+response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call,Throwable t){

            }
        });
    }

    void getMovie(){
        Intent intent = getIntent();
        final String posterPath = intent.getStringExtra("posterpath_movieDetail");
        final String title = intent.getStringExtra("title_movieDetail");
        final String release_date = intent.getStringExtra("releasedate_movieDetail");
        final String overview = intent.getStringExtra("overview_movieDetail");
        final Double rating = intent.getDoubleExtra("voteaverage_movieDetail",0);
        final int movieId = intent.getIntExtra("movieId_movieDetail",0);
        final int runtime = intent.getIntExtra("runtime_movieDetail",0);

        GetRetrofitResponse(movieId);

        String Id = String.valueOf(movieId);
        String Rating = String.valueOf(rating);
        String Runtime = String.valueOf(tv_runtime.getText());

        if(db.checkData(Id).equals(true)){
            btn_favorite.setText("UNMARK AS FAVORITE");
        }else{
            btn_favorite.setText("MARK AS FAVORITE");
        }

        Glide.with(this).load(posterPath).centerInside().into(iv_movie);
        tv_title.setText(title);
        tv_release_date.setText(release_date);
        tv_rating.setText(rating + "/10");
        tv_description.setText(overview);

        ContentValues contentValues = new ContentValues();

        if(flagMovieDetail == 1){
            contentValues.put("movie_id",Id);
            contentValues.put("movie_image",posterPath);
            contentValues.put("movie_title",title);
            contentValues.put("movie_releasedate",release_date);
            contentValues.put("movie_rating",Rating);
            contentValues.put("movie_overview",overview);
            contentValues.put("movie_runtime",Runtime);
            Log.v("Tag", "Runtime"+Runtime);

            if(db.insertData(Id,posterPath,title,release_date,Rating,overview,Runtime)){
                Toast.makeText(MovieDetailActivity.this,"Insert success",Toast.LENGTH_SHORT).show();
            }
        }else if(flagMovieDetail == 2){
            if(db.deleteData(Id) > 0){
                Toast.makeText(MovieDetailActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
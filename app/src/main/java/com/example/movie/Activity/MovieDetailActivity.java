package com.example.movie.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movie.Database.Database;
import com.example.movie.Model.MovieModel;
import com.example.movie.R;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView iv_movie;
    TextView tv_title,tv_release_date,tv_rating,tv_description;
    Button btn_favorite;

    ArrayList<MovieModel> arrMovie;

    int  flagMovieDetail = 0;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        db = new Database(this);

        iv_movie = findViewById(R.id.iv_movie);
        tv_title = findViewById(R.id.tv_title);
        tv_release_date = findViewById(R.id.tv_release_date);
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

    void getMovie(){
        Intent intent = getIntent();
        final String posterPath = intent.getStringExtra("posterpath_movieDetail");
        final String title = intent.getStringExtra("title_movieDetail");
        final String release_date = intent.getStringExtra("releasedate_movieDetail");
        final String overview = intent.getStringExtra("overview_movieDetail");
        final Double rating = intent.getDoubleExtra("voteaverage_movieDetail",0);
        final int movieId = intent.getIntExtra("movieId_movieDetail",0);
        //Log.v("Tag", "ID" + movieId );

        Glide.with(this).load(posterPath).centerInside().into(iv_movie);
        tv_title.setText(title);
        tv_release_date.setText(release_date);
        tv_rating.setText(rating + "/10");
        tv_description.setText(overview);

        ContentValues contentValues = new ContentValues();

        String Id = String.valueOf(movieId);
        String Rating = String.valueOf(rating);

        if(db.checkData(Id).equals(true)){
            btn_favorite.setText("UNMARK AS FAVORITE");
        }else{
            btn_favorite.setText("MARK AS FAVORITE");
        }

        if(flagMovieDetail == 1){
            contentValues.put("movie_id",Id);
            contentValues.put("movie_image",posterPath);
            contentValues.put("movie_title",title);
            contentValues.put("movie_releasedate",release_date);
            contentValues.put("movie_rating",Rating);
            contentValues.put("movie_overview",overview);

            if(db.insertData(Id,posterPath,title,release_date,Rating,overview)){
                Toast.makeText(MovieDetailActivity.this,"Insert success",Toast.LENGTH_SHORT).show();
            }
        }else if(flagMovieDetail == 2){
            if(db.deleteData(Id) > 0){
                Toast.makeText(MovieDetailActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
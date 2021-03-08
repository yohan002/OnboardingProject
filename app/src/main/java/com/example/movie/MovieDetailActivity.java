package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView iv_movie;
    TextView tv_title,tv_genre,tv_release_date,tv_duration,tv_rating,tv_description;
    Button btn_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        iv_movie = findViewById(R.id.iv_movie);
        tv_title = findViewById(R.id.tv_title);
        tv_genre = findViewById(R.id.tv_genre);
        tv_release_date = findViewById(R.id.tv_release_date);
        tv_duration = findViewById(R.id.tv_duration);
        tv_rating = findViewById(R.id.tv_rating);
        tv_description = findViewById(R.id.tv_description);

        getMovie();

//        btn_favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    void getMovie(){
        Intent intent = getIntent();
        final String poster_path = intent.getStringExtra("posterpath_movieDetail");
        final String title = intent.getStringExtra("title_movieDetail");
        final String release_date = intent.getStringExtra("releasedate_movieDetail");
        final String overview = intent.getStringExtra("overview_movieDetail");
        Log.v("Tag", "Overview" + overview );

        Glide.with(this).load(poster_path).centerInside().into(iv_movie);
        tv_title.setText(title);
//        tv_genre.setText();
        tv_release_date.setText(release_date);
        //tv_duration.setText(String(runtime_movieDetail));
        //tv_rating.setText(voteaverage_movieDetail.toString());
        tv_description.setText(overview);

    }
}
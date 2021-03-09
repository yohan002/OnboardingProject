package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView iv_movie;
    TextView tv_title,tv_release_date,tv_rating,tv_description;
//    TextView tv_genre,tv_duration;
    Button btn_favorite;

    int  flag = 0;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        db = new Database(this);

        iv_movie = findViewById(R.id.iv_movie);
        tv_title = findViewById(R.id.tv_title);
        //tv_genre = findViewById(R.id.tv_genre);
        tv_release_date = findViewById(R.id.tv_release_date);
        //tv_duration = findViewById(R.id.tv_duration);
        tv_rating = findViewById(R.id.tv_rating);
        tv_description = findViewById(R.id.tv_description);
        btn_favorite = findViewById(R.id.btn_favorite);

        if (flag == 0){
            getMovie();
        }

        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                getMovie();
            }
        });
    }

    void getMovie(){
        Intent intent = getIntent();
        final String posterPath = intent.getStringExtra("posterpath_movieDetail");
        final String title = intent.getStringExtra("title_movieDetail");
        final String release_date = intent.getStringExtra("releasedate_movieDetail");
        final String overview = intent.getStringExtra("overview_movieDetail");
        //final int duration = intent.getIntExtra("runtime_movieDetail",0);
        final Double rating = intent.getDoubleExtra("voteaverage_movieDetail",0);
        final int movieId = intent.getIntExtra("movieId_movieDetail",0);
        Log.v("Tag", "Overview" + overview );

        Glide.with(this).load(posterPath).centerInside().into(iv_movie);
        tv_title.setText(title);
//        tv_genre.setText();
        tv_release_date.setText(release_date);
       //tv_duration.setText(duration + " min");
        tv_rating.setText(rating + "/10");
        tv_description.setText(overview);

        if(flag == 1){
            String Id = String.valueOf(movieId);
            String Rating = String.valueOf(rating);

            ContentValues contentValues = new ContentValues();
            contentValues.put("movie_id",Id);
            contentValues.put("movie_image",posterPath);
            contentValues.put("movie_title",title);
            contentValues.put("movie_releasedate",release_date);
            contentValues.put("movie_rating",Rating);
            contentValues.put("movie_overview",overview);

            if(db.insertData(Id,posterPath,title,release_date,Rating,overview)){
                Toast.makeText(MovieDetailActivity.this,"Insert success",Toast.LENGTH_SHORT);
            }
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }


    }

}
package com.example.movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //Popular movies
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> popularMovie(
            @Query("api_key") String key
    );

    //Top Rated movies
    @GET("/3/movie/top_rated")
    Call<MovieSearchResponse> topratedMovie(
            @Query("api_key") String key
    );

    //Detail movies
    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Query("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}

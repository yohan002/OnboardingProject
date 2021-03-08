package com.example.movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

//    @GET("/3/search/movie")
//    Call<MovieSearchResponse> searchMovie(
//            @Query("api_key") String key
//            ,@Query("query") String query
//            ,@Query("page") String page
//    );

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

}

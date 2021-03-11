package com.example.movie.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    public Integer getRuntime(){
        return runtime;
    }

    @Override
    public String toString(){
        return "MovieResponse{"+
                "runtime="+runtime+
                '}';
    }
}

package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetMeaning {

    @GET("{word}")
    Call<List<ModelClass>> getMeaning(@Path("word") String word);
}

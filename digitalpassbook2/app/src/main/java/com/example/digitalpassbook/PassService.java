package com.example.digitalpassbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PassService {
    @GET("passes")
    Call<List<Pass>> getall();

    @POST("passes/new")
    Call<Pass> create(@Body Pass pass);

    @GET("passes/{id}")
    Call<Pass> get(@Path("id") int id);

    @DELETE("passes/{id}")
    Call<Pass> removePass(@Path("studentID") int id);

    @PATCH("passes/{id}")
    Call<Pass> updatePass(@Path("studentID") int id, @Body Pass pass);
}

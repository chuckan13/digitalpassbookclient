package com.example.digitalpassbook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {
    @GET("events")
    Call<List<Event>> getall();

    @POST("events/new")
    Call<Event> create(@Body Event event);

    @GET("events/{id}")
    Call<Event> get(@Path("id") int id);

    @DELETE("events/{id}")
    Call<Event> delete(@Path("id") int id);

    @PATCH("events/{id}")
    Call<Event> update(@Path("id") int id, @Body Event event);
}

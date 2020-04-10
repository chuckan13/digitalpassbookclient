package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface EventService {
    @GET("events")
    fun getall(): Call<List<Event?>?>?

    @POST("events/new")
    fun create(@Body event: Event?): Call<Event?>?

    @GET("events/{id}")
    operator fun get(@Path("id") id: Int): Call<Event?>?

    @DELETE("events/{id}")
    fun delete(@Path("id") id: Int): Call<Event?>?

    @PATCH("events/{id}")
    fun update(
        @Path("id") id: Int,
        @Body event: Event?
    ): Call<Event?>?


    companion object {
        fun create(): EventService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


            return retrofit.create(EventService::class.java)
        }
    }
}
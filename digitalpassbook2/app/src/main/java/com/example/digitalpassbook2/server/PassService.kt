package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface PassService {
//    Get all passes sorted by date
    @GET("passes")
    fun getall(): Call<List<Pass?>?>?

//    Create new pass
    @POST("passes/new")
    fun create(@Body pass: Pass?): Call<Pass?>?

//    Get pass by {id}
    @GET("passes/{id}")
    fun get(@Path("id") id: Int): Call<Pass?>?

//    Delete pass by {id}
    @DELETE("passes/{id}")
    fun delete(@Path("id") id: Int): Call<Pass?>?

//    Update pass by {id} with @body pass
    @PATCH("passes/{id}")
    fun update(
        @Path("id") id: Int,
        @Body pass: Pass?
    ): Call<Pass?>?

//    Get the passes of a student with {id} sorted by date and excluding expired passes
    @GET("passes/user/{id}")
    fun getByUserId(@Path("id") userId: Int): Call<List<Pass?>?>?

//    Get the passes of an event with {id}
    @GET("passes/event/{id}")
    fun getByEventsId(@Path("id") eventsId: Int): Call<List<Pass?>?>?

    companion object {
        fun create(): PassService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


            return retrofit.create(PassService::class.java)
        }
    }
}
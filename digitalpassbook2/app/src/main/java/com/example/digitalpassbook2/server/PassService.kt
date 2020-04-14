package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface PassService {
//    Get all passes sorted by date
    @GET("passes")
    suspend fun getall(): List<Pass?>?

//    Create new pass
    @POST("passes/new")
    suspend fun create(@Body pass: Pass?): Pass?

//    Get pass by {id}
    @GET("passes/{id}")
    suspend fun get(@Path("id") id: Int): Pass?

//    Delete pass by {id}
    @DELETE("passes/{id}")
    suspend fun delete(@Path("id") id: Int): Pass?

//    Update pass by {id} with @body pass
    @PATCH("passes/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body pass: Pass?
    ): Pass?

//    Get the passes of a student with {id} sorted by date and excluding expired passes
    @GET("passes/user/{id}")
    suspend fun getByUserId(@Path("id") userId: Int): List<Pass?>?

//    Get the passes of an event with {id}
    @GET("passes/event/{id}")
    fun getByEventsId(@Path("id") eventsId: Int): List<Pass?>?

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
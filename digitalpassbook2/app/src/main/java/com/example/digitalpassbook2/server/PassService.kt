package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface PassService {
    @GET("passes")
    fun getall(): Call<List<Pass?>?>?

    @POST("passes/new")
    fun create(@Body pass: Pass?): Call<Pass?>?

    @GET("passes/{id}")
    fun get(@Path("id") id: Int): Call<Pass?>?

    @DELETE("passes/{id}")
    fun delete(@Path("id") id: Int): Call<Pass?>?

    @PATCH("passes/{id}")
    fun update(
        @Path("id") id: Int,
        @Body pass: Pass?
    ): Call<Pass?>?

    @GET("passes/user/{id}")
    fun getByUserId(@Path("id") userId: Int): Call<List<Pass?>?>?

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
package com.example.digitalpassbook2

import retrofit2.Call
import retrofit2.http.*

interface PassService {
    @GET("passes")
    fun getall(): Call<List<Pass?>?>?

    @POST("passes/new")
    fun create(@Body pass: Pass?): Call<Pass?>?

    @GET("passes/{id}")
    operator fun get(@Path("id") id: Int): Call<Pass?>?

    @DELETE("passes/{id}")
    fun delete(@Path("id") id: Int): Call<Pass?>?

    @PATCH("passes/{id}")
    fun update(
        @Path("id") id: Int,
        @Body pass: Pass?
    ): Call<Pass?>?
}
package com.example.digitalpassbook2

import retrofit2.Call
import retrofit2.http.*

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
}
package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface EventService {
//    Get a list of all events sorted by date
    @GET("events")
    fun getall(): Call<List<Event?>?>?

//    Create a new event
    @POST("events/new")
    fun create(@Body event: Event?): Call<Event?>?

//    Get an event object by its id
    @GET("events/{id}")
    operator fun get(@Path("id") id: Int): Call<Event?>?

//    Delete an event by its id
    @DELETE("events/{id}")
    fun delete(@Path("id") id: Int): Call<Event?>?

//    Update event with {id} with @Body event
    @PATCH("events/{id}")
    fun update(
        @Path("id") id: Int,
        @Body event: Event?
    ): Call<Event?>?

//    Get the events of an organization with the organization's {id}
    @GET("events/org/{id}")
    fun getEventsByOrgId(
        @Path("id") orgId: Int
    ): Call<List<Event?>?>?

//    Get the passes of an event with {id}
    @GET("events/passes/{id}")
    fun getPassesByEvent(
        @Path("id") id: Int
    ):Call<List<Pass?>?>?

//    Get the students invited to an event with {id}
    @GET("events/students/{id}")
    fun getStudentsByEvent(
        @Path("id") id: Int
    ): Call<List<Student?>?>?

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
package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface EventService {
//    Get a list of all events sorted by date
    @GET("events")
    suspend fun getall():  List<Event?>?

//    Create a new event
    @POST("events/new")
    suspend fun create(@Body event: Event?):  Event?

//    Get an event object by its id
    @GET("events/{id}")
    suspend fun get(@Path("id") id: Int):  Event?

//    Delete an event by its id
    @DELETE("events/{id}")
    suspend fun delete(@Path("id") id: Int):  Event?

//    Update event with {id} with @Body event
    @PATCH("events/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body event: Event?
    ):  Event?

//    Get the events of an organization with the organization's {id}
    @GET("events/org/{id}")
    suspend fun getEventsByOrgId(
        @Path("id") orgId: Int
    ):  List<Event?>?

//    Get the passes of an event with {id}
    @GET("events/passes/{id}")
    suspend fun getPassesByEvent(
        @Path("id") id: Int
    ): List<Pass?>?

//    Get the students invited to an event with {id}
    @GET("events/students/{id}")
    suspend fun getStudentsByEvent(
        @Path("id") id: Int
    ):  List<Student?>?

    @POST("events/isinvited")
    suspend fun isInvited(
        @Query("eventsid") eventsId: Int,
        @Query("userbarcode") userBarCode: String
    ): Boolean

    @PATCH("events/{id}/bouncer")
    suspend fun addBouncer(
        @Path("id") eventsId: Int,
        @Body netId: String
    ): Event?

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
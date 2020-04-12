package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface OrganizationService {
    @GET("organizations")
    fun getall(): Call<List<Organization?>?>?

    @POST("organizations/new")
    fun create(@Body organization: Organization?): Call<Organization?>?

    @GET("organizations/{id}")
    operator fun get(@Path("id") id: Int): Call<Organization?>?

    @DELETE("organizations/{id}")
    fun delete(@Path("id") id: Int): Call<Organization?>?

    @PATCH("organizations/{id}")
    fun update(
        @Path("id") id: Int,
        @Body organization: Organization?
    ): Call<Organization?>?

    // retrieves all members associated with a given orgid
    @GET("organizations/members/{id}")
    fun getMembers(@Path("id") id: Int): Call<List<Student?>?>?

    companion object {
        var organization: Organization? = null

        fun create(): OrganizationService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit.create(OrganizationService::class.java)
        }
    }
}
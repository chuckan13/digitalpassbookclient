package com.example.digitalpassbook2

import retrofit2.Call
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
}
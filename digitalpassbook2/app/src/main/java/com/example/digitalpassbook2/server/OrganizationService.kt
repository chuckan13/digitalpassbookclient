package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface OrganizationService {
//    Get a list of all organizations
    @GET("organizations")
    fun getall(): Call<List<Organization?>?>?

//    Create a new organization
    @POST("organizations/new")
    fun create(@Body organization: Organization?): Call<Organization?>?

//    Get an organization based on its id
    @GET("organizations/{id}")
    operator fun get(@Path("id") id: Int): Call<Organization?>?

//    Delete an organization based on its id
    @DELETE("organizations/{id}")
    fun delete(@Path("id") id: Int): Call<Organization?>?

//    Update an organization with its {id} with @body organization
    @PATCH("organizations/{id}")
    fun update(
        @Path("id") id: Int,
        @Body organization: Organization?
    ): Call<Organization?>?

    // retrieves all members associated with a given orgid
    @GET("organizations/members/{id}")
    fun getMembers(@Path("id") id: Int): Call<List<Student?>?>?

    // retrieves all events associated with a given orgid
    @GET("organizations/events/{id}")
    fun getEvents(@Path("id") id: Int): Call<List<Event?>?>?

//    Checks if an organization with {name} exists
    @GET("organizations/existance/{name}")
    fun checkOrganizationExist(
        @Path("name") name: String
    ): Call<Boolean?>?

//    Checks if {password} matches the password of {name} organization
    @GET("organizations/passwordcorrectness/{name}/{password}")
    fun checkPasswordOfOrg(
        @Path("name") name: String,
        @Path("password") password: String
    ): Call<Boolean?>?

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
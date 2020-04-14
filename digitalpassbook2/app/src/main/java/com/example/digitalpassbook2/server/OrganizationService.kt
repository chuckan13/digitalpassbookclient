package com.example.digitalpassbook2.server

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface OrganizationService {
//    Get a list of all organizations
    @GET("organizations")
    suspend fun getall(): List<Organization?>

//    Create a new organization
    @POST("organizations/new")
    suspend fun create(@Body organization: Organization?): Organization?

//    Get an organization based on its id
    @GET("organizations/{id}")
    suspend fun get(@Path("id") id: Int): Organization?

//    Delete an organization based on its id
    @DELETE("organizations/{id}")
    suspend fun delete(@Path("id") id: Int): Organization?

//    Update an organization with its {id} with @body organization
    @PATCH("organizations/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body organization: Organization?
    ): Organization?

    // retrieves all members associated with a given orgid
    @GET("organizations/members/{id}")
    suspend fun getMembers(@Path("id") id: Int): List<Student?>

    // retrieves all events associated with a given orgid
    @GET("organizations/events/{id}")
    suspend fun getEvents(@Path("id") id: Int): List<Event?>

//    Checks if an organization with {name} exists
    @GET("organizations/existance/{name}")
    suspend fun checkOrganizationExist(
        @Path("name") name: String
    ): Boolean?

//    Checks if {password} matches the password of {name} organization
    @GET("organizations/passwordcorrectness/{name}/{password}")
    suspend fun checkPasswordOfOrg(
        @Path("name") name: String,
        @Path("password") password: String
    ): Boolean?

//    Returns an organization by its {signin}
    @GET("organizations/signin/{signin}")
    suspend fun getOrganizationBySignin (@Path("signin") signin: String): Organization?

    companion object {
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
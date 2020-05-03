 package com.example.digitalpassbook2.server

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface StudentService {
//    Get a list of all students sorted by Name
    @GET("students")
    suspend fun getall(): List<Student?>?

//    Create a new student
    @POST("students/new")
    suspend fun create(@Body student: Student?): Student?

//    Get a student by its {id}
    @GET("students/{id}")
    suspend fun get(@Path("id") id: Int): Student?

//    Delete a student by its {id}
    @DELETE("students/{id}")
    suspend fun delete(@Path("id") id: Int): Student?

//    Get a student by its {netid}
    @GET("students/netid/{netid}")
    suspend fun getByNetId(@Path ("netid") netid: String): Student?

    @PATCH("students/{id}/barcode")
    suspend fun updateBarcode(
        @Path("id") id: Int,
        @Body barcode: Int
    ): Student?

//    Update a student by its {id} with @body student
    @PATCH("students/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body student: Student?
    ): Student?

//    Update/edit a student's password
    @PATCH("students/{id}/password")
    suspend fun editPassword(
        @Path("id") id: Int,
        @Body password: String
    ): Student?

//    Check if a student with {netid} exists
    @GET("students/existance/{netid}")
    suspend fun checkStudentExist(
        @Path("netid") netId: String
    ): Boolean?

//    Check if {password} matches the password of student of {netid}
    @GET("students/passwordcorrectness/{netid}/{password}")
    suspend fun checkStudentPassword(
        @Path("netid") netId: String,
        @Path("password") password: String
    ): Boolean?

    companion object {

        fun create(): StudentService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build();

            return retrofit.create(StudentService::class.java)
        }
    }
}
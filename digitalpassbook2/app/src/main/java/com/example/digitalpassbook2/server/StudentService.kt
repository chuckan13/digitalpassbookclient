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
    fun getall(): Call<List<Student?>?>?

//    Create a new student
    @POST("students/new")
    fun create(@Body student: Student?): Call<Student?>?

//    Get a student by its {id}
    @GET("students/{id}")
    operator fun get(@Path("id") id: Int): Call<Student?>?

//    Delete a student by its {id}
    @DELETE("students/{id}")
    fun delete(@Path("id") id: Int): Call<Student?>?

//    Get a student by its {netid}
    @GET("students/netid/{netid}")
    fun getByNetId(@Path ("netid") netid: String): Call<Student?>?

//    Update a student by its {id} with @body student
    @PATCH("students/{id}")
    fun update(
        @Path("id") id: Int,
        @Body student: Student?
    ): Call<Student?>?

//    Update/edit a student's password
    @PATCH("students/{id}/password")
    fun editPassword(
        @Path("id") id: Int,
        @Body password: String
    ): Call<Student?>?

//    Check if a student with {netid} exists
    @GET("students/existance/{netid}")
    fun checkStudentExist(
        @Path("netid") netId: String
    ): Call<Boolean?>?

//    Check if {password} matches the password of student of {netid}
    @GET("students/passwordcorrectness/{netid}/{password}")
    fun checkStudentPassword(
        @Path("netid") netId: String,
        @Path("password") password: String
    ): Call<Boolean?>?

    companion object {
        var student: Student? = null

        fun create(): StudentService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build();

            return retrofit.create(StudentService::class.java)
        }
    }
}
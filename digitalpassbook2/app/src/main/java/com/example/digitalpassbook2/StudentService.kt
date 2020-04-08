package com.example.digitalpassbook2

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface StudentService {
    @GET("students")
    fun getall(): Call<List<Student?>?>?

    @POST("students/new")
    fun create(@Body student: Student?): Call<Student?>?

    @GET("students/{id}")
    operator fun get(@Path("id") id: Int): Call<Student?>?

    @DELETE("students/{id}")
    fun delete(@Path("id") id: Int): Call<Student?>?

    @PATCH("students/{id}")
    fun update(
        @Path("id") id: Int,
        @Body student: Student?
    ): Call<Student?>?

    companion object {
        fun create(): StudentService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pure-river-68629.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


            return retrofit.create(StudentService::class.java)
        }
    }
}
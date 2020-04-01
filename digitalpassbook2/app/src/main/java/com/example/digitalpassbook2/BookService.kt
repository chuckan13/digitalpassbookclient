package com.example.digitalpassbook2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookService {
    @GET("books")
    fun getall(): Call<List<Book?>?>?

    @GET("books/{isbn}")
    operator fun get(@Path("isbn") isbn: String?): Call<Book?>?

    @POST("books/new")
    fun create(@Body book: Book?): Call<Book?>?
}
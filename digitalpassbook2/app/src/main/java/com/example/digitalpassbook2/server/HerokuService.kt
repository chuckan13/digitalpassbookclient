package com.example.digitalpassbook2.server

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface HerokuService {
    @GET("hello")
    suspend fun hello(): ResponseBody?
}
package com.example.digitalpassbook2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClubService {
    //    @GET("club")
    //    Call<List<Club>> getAllClubs();
    //
    //    @POST("club")
    //    Call<Club> createClub(@Body Club club);
    //
    //    @GET("club/{clubid}")
    //    Call<Club> getClub(@Path("clubid") int clubid);
    //
    //    @DELETE("club/{clubid}")
    //    Call<Club> removeClub(@Path("clubid") int clubid);
    //
    //    @PATCH("club/{clubid}")
    //    Call<Club> updateClub(@Path("clubid") int clubid, @Body Club club );
    @GET("clubs")
    fun getall(): Call<List<Club?>?>?

    @GET("clubs/{name}")
    operator fun get(@Path("name") name: String?): Call<Club?>?

    @POST("clubs/new")
    fun create(@Body club: Club?): Call<Club?>?
}
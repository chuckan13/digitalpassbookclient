package com.example.digitalpassbook;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
//import retrofit2.http.
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PATCH;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface ClubService {
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
    Call<List<Club>> getall();

    @GET("clubs/{clubname}")
    Call<Club> get(@Path("clubname") String clubname);

    @POST("clubs/new")
    Call<Club> create(@Body Club club);


}

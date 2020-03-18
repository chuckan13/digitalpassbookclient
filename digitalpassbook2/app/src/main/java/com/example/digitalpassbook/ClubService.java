package com.example.digitalpassbook;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PATCH;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface ClubService {
    @GET   ("club")
    Call<List<Club>> allClubs();

    @POST  ("club")
    Call<Club> createClub(@Body Club club);

    @GET   ("club/{clubID}")
    Call<Club> getClub(@Path("clubID") int clubID);

    @DELETE("club/{clubID}")
    Call<Club> removeClub(@Path("clubID") int clubID);

    @PATCH   ("club/{clubID}")
    Call<Club> updateClub(@Path("clubID") int clubID, @Body Club club );



}

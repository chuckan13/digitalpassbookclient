package com.example.digitalpassbook;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrganizationService {
    @GET("organizations")
    Call<List<Organization>> getall();

    @POST("organizations/new")
    Call<Organization> create(@Body Organization organization);

    @GET("organizations/{id}")
    Call<Organization> get(@Path("id") int id);

    @DELETE("organizations/{id}")
    Call<Organization> delete(@Path("id") int id);

    @PATCH("organizations/{id}")
    Call<Organization> update(@Path("id") int id, @Body Organization organization);
}

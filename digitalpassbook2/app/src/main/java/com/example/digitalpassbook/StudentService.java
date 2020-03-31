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

public interface StudentService {
    @GET ("students")
    Call<List<Student>> getall();

    @POST ("students/new")
    Call<Student> create(@Body Student student);

    @GET ("students/{id}")
    Call<Student> get(@Path("id") int id);

    @DELETE ("students/{id}")
    Call<Student> delete(@Path("id") int id);

    @PATCH ("students/{id}")
    Call<Student> update(@Path("id") int id, @Body Student student);

}

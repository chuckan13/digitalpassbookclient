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
    @GET ("student")
    Call<List<Student>> allStudents();

    @POST ("student/new")
    Call<Student> createStudent(@Body Student student);

    @GET ("student/{studentID}")
    Call<Student> getStudent(@Path("studentID") int studentID);

    @DELETE ("student/{studentID}")
    Call<Student> removeStudent(@Path("studentID") int studentID);

    @PATCH ("student/{studentID}")
    Call<Student> updateStudent(@Path("studentID") int studentID, @Body Student student);

}

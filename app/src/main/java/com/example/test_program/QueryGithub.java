package com.example.test_program;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QueryGithub {

    @GET("users/{id}")
    Call<UserGithub> getInfoUser(@Path("id") String idUser);
}

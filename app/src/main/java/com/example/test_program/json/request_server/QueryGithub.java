package com.example.test_program.json.request_server;

import com.example.test_program.json.parsing_class.UserGithub;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QueryGithub {

    @GET("users/{id}")
    Call<UserGithub> getInfoUser(@Path("id") String idUser);
}

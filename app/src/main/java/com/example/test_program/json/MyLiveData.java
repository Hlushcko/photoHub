package com.example.test_program.json;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.test_program.json.parsing_class.UserGithub;
import com.example.test_program.json.request_server.QueryGithub;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyLiveData extends LiveData<UserGithub> {

    private final static String GITHUB_API = "https://api.github.com/";

    private void setValueData(UserGithub userName){
        setValue(userName);
    }

    public void convertJSON(String userName){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QueryGithub query = retrofit.create(QueryGithub.class);
        Call<UserGithub> call = query.getInfoUser(userName);

        call.enqueue(new Callback<UserGithub>() {
            @Override
            public void onResponse(Call<UserGithub> call, Response<UserGithub> response) {
                switch (response.code()){
                    case 200: setValueData(response.body()); break;
                    case 300: Log.i("Error: ", "image load error 300"); break;
                    case 400: Log.i("Error: ", "image load error 400"); break;
                    case 500: Log.i("Error: ", "image load error 500"); break;
                    default: Log.i("Error: ", "unknown error");
                }
            }

            @Override
            public void onFailure(Call<UserGithub> call, Throwable t) {
                Log.i("Error", t.getLocalizedMessage());
            }
        });

    }

}

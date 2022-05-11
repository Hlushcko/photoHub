package com.example.test_program;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAsyncTask extends AsyncTask<String, Void, UserGithub> {


    private final String GITHUB_API = "https://api.github.com/";

    private TextView userName;
    private ImageView imageUser;
    private Context context;

    public MyAsyncTask(TextView UserName, ImageView ImageUser, Context myContext){
        userName = UserName;
        imageUser = ImageUser;
        context = myContext;
    }

    @Override
    protected UserGithub doInBackground(String... strings) {
        return convertJSON(strings[0]);
    }

    public UserGithub convertJSON(String user) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QueryGithub queryGithub = retrofit.create(QueryGithub.class);
        Call<UserGithub> call = queryGithub.getInfoUser(user);
        call.enqueue(new Callback<UserGithub>() {

            @Override
            public void onResponse(@NonNull Call<UserGithub> call, @NonNull Response<UserGithub> response) {
                if (response.code() == 200 && response.body() != null) {
                    onPostExecute(response.body());
                }else{
                    Toast.makeText(context.getApplicationContext(), "Проблеми з сервером або невірне імя користувача", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserGithub> call, @NonNull Throwable throwable) { }
        });
        return null;
    }

    @Override
    protected void onPostExecute(UserGithub user) {

        if (user != null) {
            userName.setText(user.getLogin());

            Glide
                    .with(context)
                    .load(user.getAvatar_url())
                    .error(R.drawable.ic_launcher_background)
                    .into(imageUser);
        }else{
            Toast.makeText(context.getApplicationContext(), "Не получилось отримати інформацію про користувача", Toast.LENGTH_SHORT).show();
        }
    }
}

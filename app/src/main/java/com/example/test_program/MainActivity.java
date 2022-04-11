package com.example.test_program;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private final String GITHUB_API = "https://api.github.com/";

    ImageView userImage;
    TextView userName;
    EditText githubUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void searchGithub(View view) {
        userImage = findViewById(R.id.githubImageUser);
        userName = findViewById(R.id.nameUserGithub);
        githubUserName = findViewById(R.id.nameGithub);

        if(TextUtils.isEmpty(githubUserName.getText().toString())){
            Toast.makeText(this, "Ви не вказали імя користувача", Toast.LENGTH_SHORT).show();
            return;
        }else{
            convertJsonGithub(githubUserName.getText().toString());
        }
    }

    private void convertJsonGithub(String nameSearchUser){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QueryGithub queryGithub = retrofit.create(QueryGithub.class);
        Call<UserGithub> call = queryGithub.getInfoUser(nameSearchUser);
        call.enqueue(new Callback<UserGithub>() {

            @Override
            public void onResponse(@NonNull Call<UserGithub> call, @NonNull Response<UserGithub> response) {
                if (response.code() == 200 && response.body() != null) {
                    UserGithub user = response.body();

                    userName.setText(user.getLogin());

                    Glide
                            .with(getApplicationContext())
                            .load(user.getAvatar_url())
                            .error(R.drawable.ic_launcher_background)
                            .into(userImage);
                }else{
                    Toast.makeText(MainActivity.this, "Проблеми з сервером або невірне імя користувача", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserGithub> call, @NonNull Throwable throwable) { }
        });


    }

}
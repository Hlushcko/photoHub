package com.example.test_program.user_interface;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.test_program.R;
import com.example.test_program.json.parsing_class.UserGithub;
import com.example.test_program.view_models.ViewModelUser;

public class MainActivity extends AppCompatActivity {

    ImageView userImage;
    TextView userName;
    EditText githubUserName;
    ViewModelUser viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        viewModel.userGithubLiveData().observe(this, new Observer<UserGithub>() {
            @Override
            public void onChanged(UserGithub userGithub) {
                setImage(userGithub);
            }
        });
    }

    private void init(){
        userImage = findViewById(R.id.githubImageUser);
        userName = findViewById(R.id.nameUserGithub);
        githubUserName = findViewById(R.id.nameGithub);
        viewModel = new ViewModelProvider(this).get(ViewModelUser.class);
    }


    public void searchGithub(View view) {

        if(TextUtils.isEmpty(githubUserName.getText().toString())){
            Toast.makeText(this, "Ви не вказали імя користувача", Toast.LENGTH_SHORT).show();
            return;
        }else{
            viewModel.searchImageGithub(githubUserName.getText().toString());
        }
    }

    public void setImage(UserGithub user){
        if (user != null) {
            userName.setText(user.getLogin());

            Glide
                    .with(this)
                    .load(user.getAvatar_url())
                    .error(R.drawable.ic_launcher_background)
                    .into(userImage);
        }else{
            Toast.makeText(this.getApplicationContext(), "Не получилось отримати інформацію про користувача", Toast.LENGTH_SHORT).show();
        }
    }

}

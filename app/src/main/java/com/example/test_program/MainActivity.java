package com.example.test_program;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

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
            MyAsyncTask task = new MyAsyncTask(userName, userImage, this);
            task.convertJSON(githubUserName.getText().toString());
        }
    }


}
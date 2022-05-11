package com.example.test_program.view_models;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.test_program.json.MyLiveData;
import com.example.test_program.json.parsing_class.UserGithub;

public class ViewModelUser extends AndroidViewModel {

    private MyLiveData data = new MyLiveData();

    public ViewModelUser(@NonNull Application application) {
        super(application);
    }

    public LiveData<UserGithub> userGithubLiveData(){
        return data;
    }

    @SuppressLint("CheckResult")
    public void searchImageGithub(String userName){
        data.convertJSON(userName);
    }
}

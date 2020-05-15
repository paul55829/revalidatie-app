package com.example.revalidatieapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NavigationViewModel extends AndroidViewModel {
    private String userId;

    public NavigationViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserId(String theUserId){
        userId = theUserId;
    }

    public String getUserId(){
        return userId;
    }
}

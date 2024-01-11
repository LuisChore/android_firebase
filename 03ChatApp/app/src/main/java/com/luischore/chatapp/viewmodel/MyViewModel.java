package com.luischore.chatapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.luischore.chatapp.repository.Repository;

public class MyViewModel extends AndroidViewModel {
    Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository();
    }

    public void signUpAnonymousUser( ){
        Context c = this.getApplication();
        repository.firebaseAnonymousAuthentication(c);
    }

    public String getCurrentUserId(){
        return repository.getCurrentUserId();
    }

    public void singOut(){
        repository.signOut();
    }
}

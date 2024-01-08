package com.luischore.chatapp.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.luischore.chatapp.view.GroupsActivity;

public class Repository {
    public void firebaseAnonymousAuthentication(Context context){
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent i = new Intent(context, GroupsActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
    }

    public String getCurrentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}

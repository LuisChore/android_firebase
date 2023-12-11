package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn,createAccountBtn;
    private EditText emailEt, passEt;

    //Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccountBtn = findViewById(R.id.createAccountBtn);
        loginBtn = findViewById(R.id.emailSigninBtn);
        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.password);

        //Sign Up
        createAccountBtn.setOnClickListener(view ->  {
            Intent i = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(i);

        });

        //Firebase AUth
        firebaseAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(view -> {
            loginEmailPassUser(
                    emailEt.getText().toString().trim(),
                    passEt.getText().toString().trim()
            );
        });

    }

    private void loginEmailPassUser(String email, String pass) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            firebaseAuth.signInWithEmailAndPassword(email,pass).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    Intent i = new Intent(MainActivity.this,JournalListActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}
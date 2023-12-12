package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    EditText passwordCreate,usernameCreate,emailCreate;
    Button createBTN;

    //Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;

    //Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        createBTN = findViewById(R.id.acc_signup_btn);
        passwordCreate = findViewById(R.id.password_create);
        emailCreate = findViewById(R.id.email_create);
        usernameCreate = findViewById(R.id.username_create_et);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    //return to MainActivity?
                }else{

                }
            }
        };

        createBTN.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(emailCreate.getText().toString()) &&
                !TextUtils.isEmpty(passwordCreate.getText().toString()) &&
                !TextUtils.isEmpty(usernameCreate.getText().toString())) {
                String email = emailCreate.getText().toString().trim();
                String pass = passwordCreate.getText().toString().trim();
                String username = usernameCreate.getText().toString().trim();
                createUserEmailAccount(email,pass,username);
            }else{
                Toast.makeText(SignUpActivity.this,
                        "No fields allowed to be empty",
                        Toast.LENGTH_LONG).show();
            }

        });
    }

    private void createUserEmailAccount(String email,String password, String username){
        if(!TextUtils.isEmpty(email) &&
            !TextUtils.isEmpty(password) &&
            !TextUtils.isEmpty(username)){
                firebaseAuth.createUserWithEmailAndPassword(email,password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,
                                    "Account is created successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }
    }
}
package com.example.journalapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddJournalActivity extends AppCompatActivity {
    //Widgets

    private Button saveButton;
    private ImageView addPhotoBtn;
    private ProgressBar progressBar;
    private EditText titleEditText,thoughtsEditText;
    private ImageView imageView;


    //Firebase

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Journal");
    //Storage
    private StorageReference storageReference;

    //Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;
    private String currentUserId,currentUserName;


    ActivityResultLauncher<String> mTakePhoto;

    /*
    1. Get the current user
    2. Upload the post
        Journal fields
        username & userid
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        progressBar = findViewById(R.id.post_progressBar);
        titleEditText = findViewById(R.id.post_title_et);
        thoughtsEditText = findViewById(R.id.post_description_et);
        imageView = findViewById(R.id.post_imageView);
        saveButton = findViewById(R.id.post_save_journal_button);
        addPhotoBtn = findViewById(R.id.postCameraButton);

        // Progress Bar
        progressBar.setVisibility(View.INVISIBLE);
        //make it visible if the user upload a progress bar

        //Firebase
        //Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        //Auth
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseUser != null){
            currentUserId = firebaseUser.getUid();
            currentUserName = firebaseUser.getDisplayName();
        }
        
        saveButton.setOnClickListener(view -> {
            saveJournal();    
        });




        /*
        Used to pick a content from the User Device, such as an image or files.
        Feature that is part of androidx ActivityResult lib introduced to simplify working
        with ActivityResult
         */

        /* registerForActivityResult
        Method used to register an activity result callback. It allows you to specify an activity
        result contract (a predefined contract, or custom one) that describes the type of activity
        result you want to handle and how to handle it
         */

        /*

            1. Get the Image from the Gallery
            2. Display a thumbnail in the ImageView
            3. Prepare to uploading it to the FirebaseStorage
         */
        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                //HANDLE THE SELECTED CONTENT
                imageView.setImageURI(o);
            }
        });

        addPhotoBtn.setOnClickListener( view -> {
            mTakePhoto.launch("image/*");
            // we need to specify what kind of file we need to get
        });

    }

    private void saveJournal() {
    }
}
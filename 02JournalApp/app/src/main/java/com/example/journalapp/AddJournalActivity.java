package com.example.journalapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

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


    private ActivityResultLauncher<String> mTakePhoto;
    private Uri imageUri;

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
        //StorageReference to root location
        storageReference = FirebaseStorage.getInstance().getReference();

        //Auth
        firebaseAuth = FirebaseAuth.getInstance();
        
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
                imageUri = o;
            }
        });

        addPhotoBtn.setOnClickListener( view -> {
            mTakePhoto.launch("image/*");
            // we need to specify what kind of file we need to get
        });

    }

    private void saveJournal() {
        String title = titleEditText.getText().toString().trim();
        String thoughts = thoughtsEditText.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUri !=null){
            //   Storage Reference to a child location
            final StorageReference filePath = storageReference.child("journal_images").
                    child("my_image_" + Timestamp.now().getSeconds());

            // Upload the image --- ***
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            Journal journal = new Journal();
                            journal.setTitle(title);
                            journal.setThoughts(thoughts);
                            journal.setImageUrl(imageUrl);
                            journal.setTimeAdded(new Timestamp(new Date()));
                            journal.setUserName(currentUserName);
                            journal.setUserId(currentUserId);

                            collectionReference.add(journal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent i = new Intent(AddJournalActivity.this,JournalListActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddJournalActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(AddJournalActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AddJournalActivity.this,"Invalid arguments",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUserId = firebaseUser.getUid();
        currentUserName = firebaseUser.getDisplayName();
    }
}
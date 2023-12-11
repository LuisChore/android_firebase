package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends AppCompatActivity {

    // Firebase Auth
    /*
    To acknowledge the current auth state of the user
     */
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;


    //FirebaseFirestore instance

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Firebase Storage Reference
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Journal");

    //List of Journal
    private ArrayList<Journal> data;

    //RecyclerView
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Widgets
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Posts arraylist
        data = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int value = item.getItemId();
        if (value == R.id.action_add) {
           if(firebaseUser != null && firebaseAuth != null){
               Intent i = new Intent(JournalListActivity.this,AddJournalActivity.class);
               startActivity(i);
           }
        }else if(value == R.id.action_signout) {
           if(firebaseUser != null && firebaseAuth != null){
               firebaseAuth.signOut();
               Intent i = new Intent(JournalListActivity.this,MainActivity.class);
               startActivity(i);
           }
        }
        return super.onOptionsItemSelected(item);
    }

}
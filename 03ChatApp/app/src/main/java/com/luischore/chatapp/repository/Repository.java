package com.luischore.chatapp.repository;

import android.content.Context;
import android.content.Intent;
import android.graphics.text.MeasuredText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luischore.chatapp.model.ChatGroup;
import com.luischore.chatapp.model.ChatMessage;
import com.luischore.chatapp.view.GroupsActivity;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    MutableLiveData<List<ChatGroup>> chatGroupsMutableLiveData;
    MutableLiveData<List<ChatMessage>> messagesMutableLiveData;
    FirebaseDatabase database;
    DatabaseReference reference;

    public Repository() {
        chatGroupsMutableLiveData = new MutableLiveData<>();
        messagesMutableLiveData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(); // root
    }

    //Auth Methods
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

    // Realtime Database methods
    public MutableLiveData<List<ChatGroup>> getChatGroupsMutableLiveData() {
        List<ChatGroup> chatGroups = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatGroups.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatGroup group = new ChatGroup(dataSnapshot.getKey());
                    chatGroups.add(group);
                }
                chatGroupsMutableLiveData.postValue(chatGroups)  ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGroupsMutableLiveData;
    }

    public void createNewChatGroup(String groupName){
        reference.child(groupName).setValue(groupName);
    }

    public MutableLiveData<List<ChatMessage>> getMessagesMutableLiveData(String groupName) {
        DatabaseReference groupReference = database.getReference().child(groupName);
        List<ChatMessage> messageList = new ArrayList<>();
        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    messageList.add(chatMessage);
                }
                messagesMutableLiveData.postValue(messageList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messagesMutableLiveData;
    }

    public void sendMessage(String messageText,String chatGroup){
        DatabaseReference groupReference = database.getReference(chatGroup);
        if(!messageText.trim().equals("")){
            ChatMessage msg = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );
            String randomKey = groupReference.push().getKey();
            reference.child(randomKey).setValue(msg);
        }
    }
}

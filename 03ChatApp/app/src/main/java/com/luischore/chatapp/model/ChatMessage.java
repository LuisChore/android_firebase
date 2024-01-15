package com.luischore.chatapp.model;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatMessage {
    String senderId;
    String messageText;
    long time;
    boolean isMine;

    public ChatMessage(String senderId, String messageText, long time) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.time = time;
    }

    private boolean checkOwnMessage(String senderId) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String mySenderId = firebaseAuth.getCurrentUser().getUid();
        return mySenderId.equals(senderId);
    }

    public ChatMessage() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMine() {
        return checkOwnMessage(senderId);
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String convertTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(getTime());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }
}

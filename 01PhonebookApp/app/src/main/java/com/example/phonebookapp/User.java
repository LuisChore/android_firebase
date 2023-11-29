package com.example.phonebookapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {
    String username;
    String phoneNumber;
    String groupUser;

    public User() {

    }

    public User(String username, String phoneNumber, String groupUser) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.groupUser = groupUser;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    @Bindable
    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
        notifyPropertyChanged(BR.groupUser);
    }
}

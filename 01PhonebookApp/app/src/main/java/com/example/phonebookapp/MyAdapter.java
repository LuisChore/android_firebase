package com.example.phonebookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebookapp.databinding.ItemCardBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<User> data;
    Context context;

    public MyAdapter(ArrayList<User> data, Context context) {
        this.data = data;
        this.context = context;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemCardBinding itemCardBinding;
        public ViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding itemCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_card,
                parent,
                false
        );
        return new ViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = data.get(position);
        holder.itemCardBinding.setUser(user);
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size():0;
    }
}

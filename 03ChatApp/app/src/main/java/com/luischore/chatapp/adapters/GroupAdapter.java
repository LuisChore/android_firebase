package com.luischore.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.luischore.chatapp.R;
import com.luischore.chatapp.databinding.ItemCardBinding;
import com.luischore.chatapp.model.ChatGroup;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private ArrayList<ChatGroup> data;

    public GroupAdapter(ArrayList<ChatGroup> data) {
        this.data = data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemCardBinding binding;

        public ViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                int position = getAdapterPosition();

            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatGroup chatGroup = data.get(position);
        holder.binding.setChatGroup(chatGroup);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}

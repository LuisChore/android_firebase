package com.example.journalapp;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //Source Data
    private  ArrayList<Journal> data;
    //Context
    private Context context; //used it with the Glide library

    public MyAdapter(ArrayList<Journal> data, Context context) {
        this.data = data;
        this.context = context;
    }



    //View Holder Class
    /* Object that holds references to the views and that make up an Individual
       item in the RecyclerView list
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title, thoughts, dateAdded, name;
        public ImageView image,shareButton;
        public String userId,username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);

            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);
            shareButton = itemView.findViewById(R.id.journal_row_share_button);

            shareButton.setOnClickListener( view -> {

            });
        }
    }

    /*
    Method responsible for creating a new ViewHolder instance for a new item that needs to be
    displayed on the screen.
        1. It inflates a layout for an ItemView
        2. Returns a new ViewHolder that holds references to the views within the layout
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.journal_row,parent,false );
        return new ViewHolder(view);
    }

    /*
     Method called when RecyclerView wants to bind data to a ViewHolder, specially to populate the
     item views with the data for a particular position in the data ser
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Journal journal = data.get(position);
        holder.title.setText(journal.getTitle());
        holder.thoughts.setText(journal.getThoughts());
        holder.name.setText(journal.getUserName());

        String imageUrl = journal.getImageUrl();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                journal.getTimeAdded().getSeconds()*1000
        );
        holder.dateAdded.setText(timeAgo);

        //Glide
        Glide.with(context).load(imageUrl).
                fitCenter().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}

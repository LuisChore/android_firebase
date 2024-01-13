package com.luischore.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.luischore.chatapp.R;
import com.luischore.chatapp.adapters.GroupAdapter;
import com.luischore.chatapp.databinding.ActivityGroupsBinding;
import com.luischore.chatapp.model.ChatGroup;
import com.luischore.chatapp.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private ArrayList<ChatGroup> chatGroupsArrayList;
    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    private ActivityGroupsBinding binding;
    private MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_groups);
        //define ViewModel
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //RecyclerView
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up an Observer to listen for changes in a Live Data object
        viewModel.getChatGroupsList().observe(this, new Observer<List<ChatGroup>>() {

            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                chatGroupsArrayList = new ArrayList<>();
                chatGroups.addAll(chatGroups);
                adapter = new GroupAdapter(chatGroupsArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
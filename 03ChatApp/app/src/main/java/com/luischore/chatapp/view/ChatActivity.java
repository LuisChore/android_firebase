package com.luischore.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.luischore.chatapp.R;
import com.luischore.chatapp.adapters.ChatAdapter;
import com.luischore.chatapp.databinding.ActivityChatBinding;
import com.luischore.chatapp.model.ChatMessage;
import com.luischore.chatapp.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    //Data Source
    ArrayList<ChatMessage> messages;
    //DataBinding
    private ActivityChatBinding binding;
    //ViewModel
    private MyViewModel viewModel;
    //RecyclerView
    private RecyclerView recyclerView;
    private ChatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Data Source
        messages = new ArrayList<>();
        // Binding
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);
        //ViewModel
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //RecyclerView
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //Bind ViewModel
        binding.setViewModel(viewModel);

        // Get data (groupname) from the GroupsActivity using Intent
        String groupName = getIntent().getStringExtra("GROUP_NAME");
        viewModel.getChatMessageList(groupName).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {

                messages.clear();
                messages.addAll(chatMessages);
                adapter = new ChatAdapter(messages,getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                // Scroll to the last message
                int latestPosition = adapter.getItemCount()-1;
                if(latestPosition > 0){
                    recyclerView.smoothScrollToPosition(latestPosition);
                }
            }
        });

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = binding.editTextChatMessage.getText().toString();
                viewModel.sendMessage(msg,groupName);
                binding.editTextChatMessage.getText().clear();
            }
        });
    }
}
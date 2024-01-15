package com.luischore.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.luischore.chatapp.R;
import com.luischore.chatapp.adapters.GroupAdapter;
import com.luischore.chatapp.databinding.ActivityGroupsBinding;
import com.luischore.chatapp.databinding.DialogLayoutBinding;
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

    //Dialog
    private Dialog chatGroupDialog;
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

        // Add new Group
        binding.fab.setOnClickListener(view -> {
            showDialog();
        });

    }

    public void showDialog(){
        chatGroupDialog = new Dialog(this);
        chatGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);
        chatGroupDialog.setContentView(view);
        Button button = findViewById(R.id.submitGroupButton);
        EditText editText = findViewById(R.id.groupNameEditText);
        chatGroupDialog.show();

        button.setOnClickListener(view1 -> {
            String groupName = editText.getText().toString();
            viewModel.createNewGroup(groupName);
            Toast.makeText(this,"Chat Group: " + groupName,Toast.LENGTH_SHORT).show();
            chatGroupDialog.dismiss();
        });

    }
}
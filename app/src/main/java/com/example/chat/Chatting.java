package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Chatting extends AppCompatActivity {
       RecyclerView  recyclerView;
       chatAdapter chatAdapter;
       Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity (new Intent(Chatting.this, Login.class));
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        FirebaseRecyclerOptions<chat> options =
                new FirebaseRecyclerOptions.Builder<chat>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Chat"), chat.class)
                        .build();
        chatAdapter= new chatAdapter(options);
        recyclerView.setAdapter(chatAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }
    private void  init(){
        back = findViewById(R.id.back);

    }
}
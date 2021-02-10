package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.jar.Attributes;

public class Login extends AppCompatActivity {
       Button btnlogout, btninsert, btnread, btngo;
       EditText name, chat;
       TextView tvread;
       DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       init();
       btninsert.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

             saveDataInFirebase();
           }
       });

       btngo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Login.this, Chatting.class));
           }
       });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }

    private void onReadTimeChange() {
        firebaseDatabase.child("Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String data = "Name: "+snapshot.child("name").getValue(String.class)
                                +"\n"+"Chat: "+snapshot.child("chat").getValue(String.class);

                        tvread.setText(data);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    private void saveDataInFirebase() {
        HashMap< String, String> data = new HashMap<>();
        data.put("name", name.getText().toString().trim());
        data.put("chat", chat.getText().toString().trim());


        firebaseDatabase.push().setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this, "Message sent", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Message failed", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void init(){

       btnlogout = (Button) findViewById(R.id.btnlogout);
       btninsert = findViewById(R.id.btnInsert);
     //  btnread = findViewById(R.id.btnread);
       name = findViewById(R.id.name);
       chat = findViewById(R.id.chat);
      // tvread = findViewById(R.id.tvread);
       btngo = findViewById(R.id.btngo);
       firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Chat");
     }
}
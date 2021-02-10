package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btntosignup, btnlogin;
    private EditText etmail,etpassword;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
mAuth = FirebaseAuth.getInstance();
        btntosignup = findViewById(R.id.btntosignup);
        btntosignup.setOnClickListener(this);
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);
etmail = findViewById(R.id.etmail);
etpassword = findViewById(R.id.etpassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btntosignup:
                startActivity(new Intent(this,Signup.class));
                break;
            case  R.id.btnlogin:
                userbtnlogin();
                break;
        }
    }

    private void userbtnlogin() {
        String email = etmail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (email.isEmpty()){

            etmail.setError("Required");
            etmail.requestFocus();
            return;
        }
        if (password.isEmpty()){

            etpassword.setError("Required");
            etpassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {

                        startActivity(new Intent(MainActivity.this, Login.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your Email to verify your Account", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText etname, etemail, etpass;
    private Button btnreg, btnAC;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();
        etname = findViewById(R.id.etname);
        etemail = findViewById(R.id.etmail);
        etpass = findViewById(R.id.etpassword);
        btnreg = findViewById(R.id.btnreg);
        btnreg.setOnClickListener(this);
        btnAC = findViewById(R.id.btnAC);
        progressBar = findViewById(R.id.progressBar2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnreg:
                btnreg();
                break;

        }

    }

    private void btnreg() {
        String name = etname.getText().toString().trim();
        String etmail= etemail.getText().toString().trim();
        String etpassword = etpass.getText().toString().trim();
        if (name.isEmpty()) {
            etname.setError("Required Field");
            etname.requestFocus();
            return;
        }
        if (etmail.isEmpty()) {
            etemail.setError("Required Field");
           etemail.requestFocus();
           return;

        }
        if ((Patterns.EMAIL_ADDRESS.matcher((CharSequence) etemail).matches())) {
            etemail.setError("Enter Correct Form");
            etemail.requestFocus();
            return;
        }
        if (etpassword.isEmpty()){

            etpass.setError("Required field");
            etpass.requestFocus();
            return;
        }
if (etpassword.length()<6){
    etpass.setError("Password must more than 6 characters");
    etpass.requestFocus();
    return;
}

progressBar.setVisibility(View.VISIBLE);
mAuth.createUserWithEmailAndPassword(etmail, etpassword)

     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
                 User user = new User(name, etmail);
                 FirebaseDatabase.getInstance().getReference("user")
                  .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                         .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){
                             Toast.makeText(Signup.this, "Usr Added Successfully", Toast.LENGTH_LONG).show();
                         progressBar.setVisibility(View.GONE);
                         }

                     }

                 });
             } else {
                 Toast.makeText(Signup.this, "Failed to register user! Try Again", Toast.LENGTH_LONG).show();
                 progressBar.setVisibility(View.GONE);
             }
         }
     });
    }

}


package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    EditText emailEdittext;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEdittext = (EditText) findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();

    }

    public void reset(View view){
        String email = emailEdittext.getText().toString().trim();

        if(email.isEmpty()){
            emailEdittext.setError("This field cannot be empty!");
            emailEdittext.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdittext.setError("Provide valid email");
            emailEdittext.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPassword.this,"reset mail has sent your email",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(forgotPassword.this,"error! try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
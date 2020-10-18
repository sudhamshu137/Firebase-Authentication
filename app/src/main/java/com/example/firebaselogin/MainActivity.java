package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

    }

    public void RegisterFunction(View view){
        Intent i = new Intent(MainActivity.this,RegisterUser.class);
        startActivity(i);
    }

    public void forgotpasswordFunction(View view){
        Intent iii = new Intent(MainActivity.this,forgotPassword.class);
        startActivity(iii);
    }

    public void LoginFunction(View view){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("This field cannot be empty");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("provide a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("This field cannot be empty");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("cannot be less than 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                        Intent ii = new Intent(MainActivity.this,ProfileActivity.class);
                        startActivity(ii);
                        finish();
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Verification email has been sent to your email",Toast.LENGTH_LONG).show();
                    }


                }
                else{
                    Toast.makeText(MainActivity.this, "Signin failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
package com.example.peter_odhiss.firebase_recycler_adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button signin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        toolbar = findViewById(R.id.start_activitybar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Page");

        email = findViewById(R.id.startEmail);
        password = findViewById(R.id.startPassword);
        signin = findViewById(R.id.signin);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin:
                login();
        }
    }

    //Login Activity
    private void login() {
        String emailAddress = email.getText().toString();
        String passwordPhrase = password.getText().toString();

        if(emailAddress.isEmpty()){
            email.setError("Fill an email");
            email.requestFocus();
            return;
        }
        if(passwordPhrase.isEmpty()){
            password.setError("Fill password");
            password.requestFocus();
            return;
        }

        signIn(emailAddress, passwordPhrase);
    }

    private void signIn(String emailAddress, String passwordPhrase) {
        progressDialog.setTitle("Loging In");
        progressDialog.setMessage("Please wait while we verify your details and log you in:");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailAddress, passwordPhrase)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Welcome:" + firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

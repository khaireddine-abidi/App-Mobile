package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {
    TextView btn;
    EditText inputEmail ,inputPassword;
    Button btnLogin;
    private  FirebaseAuth mAuth;
    ProgressDialog mloadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);


        mAuth=FirebaseAuth.getInstance();
        mloadingBar=new ProgressDialog(LoginActivity.this);

        btnLogin=(Button)findViewById(R.id.btnlogin);
        // Initialize Firebase Auth

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCrededentails();

            }
        });

        btn=findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }





    private void checkCrededentails() {

        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();


        if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail,"email invalde ");
        }
        else if (password.isEmpty() || password.length()<7) {
            showError(inputPassword, "motpasse invalide");
        }
        else
        {
            mloadingBar.setTitle("Login");
            mloadingBar.setMessage("Plase wait ");
            mloadingBar.setCanceledOnTouchOutside(false);
            mloadingBar.show();


            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        mloadingBar.dismiss();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    }

                }
            });
        }
    }
    private void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }
    }


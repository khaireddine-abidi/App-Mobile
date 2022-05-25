package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;


import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    TextView btn;
    private EditText inputUsername ,inputPassword , inputEmail,inputConformPassword ;
    Button btnRegiter;
    private FirebaseAuth mAuth;
    private ProgressDialog mloadingBar;

    DatabaseReference clientDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputUsername=findViewById(R.id.inputUsername);
        inputEmail =findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConformPassword=findViewById(R.id.inputConformPassword);
        btnRegiter=findViewById(R.id.btnRegister);
        btn=findViewById(R.id.alreadyHaveAccount);
        mAuth=FirebaseAuth.getInstance();
        clientDB= FirebaseDatabase.getInstance().getReference();



        mloadingBar=new ProgressDialog(RegisterActivity.this);

        btnRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checkCrededentails();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }



    private void checkCrededentails() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String conformpassword  =inputConformPassword.getText().toString();
        if (username.isEmpty()|| username.length()<7)
        {
            showError(inputUsername,"nom utilisateur invalide ");
        }
        else if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail,"email invalde ");
        }
        else if (password.isEmpty() || password.length()<7)
        {
            showError(inputPassword,"motpasse invalide");
        }else if (conformpassword.isEmpty() || !conformpassword.equals(password))
        {
            showError(inputConformPassword,"conforme  motpasse invalide ");
        }else
        {

            mloadingBar.setTitle("Reistertion");
            mloadingBar.setMessage("Please wait");
            mloadingBar.setCanceledOnTouchOutside(false);
            mloadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "bien enregister", Toast.LENGTH_SHORT).show();
                        mloadingBar.dismiss();
                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                        ClientModel user2 = new ClientModel(username, email, "visiteur");

                        clientDB.child("users").push().setValue(user2);
                        addDefaultHistory(user2.getEmail());
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            
        }
    }

    private void addDefaultHistory(String myemail) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/users");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String email = dataSnapshot.child("email").getValue().toString();
                if (email.toLowerCase().equals(myemail.toLowerCase())){
                    String key = dataSnapshot.getKey().toString();
                    myRef.child(key).child("historique").push().child("action").setValue("Compte cree avec sucees.");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void showError(EditText input, String s)
    {
        input.setError(s);
        input.requestFocus();
    }
}

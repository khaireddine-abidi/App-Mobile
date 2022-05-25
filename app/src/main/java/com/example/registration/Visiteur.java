package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Visiteur extends AppCompatActivity {
    Button logout1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiteur);
        logout1=findViewById(R.id.btnLogout1);
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(in);
                finish();
            }
        });

    }
}
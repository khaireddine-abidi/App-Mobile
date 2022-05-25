package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Client extends AppCompatActivity {
    Button historique, ajoutappareil, showsensors;
    Button logoutclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        logoutclient=findViewById(R.id.logoutclient);
        logoutclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(in);
                finish();
            }
        });

        historique = findViewById(R.id.historique);
        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext() ,Historique.class);
                startActivity(in);
            }
        });
        ajoutappareil = findViewById(R.id.ajoutappareil);
        ajoutappareil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext() ,Appareils.class);
                startActivity(in);
            }
        });
        showsensors = findViewById(R.id.showcapteurs);
        showsensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext() ,Capteurs.class);
                startActivity(in);
            }
        });
    }
}
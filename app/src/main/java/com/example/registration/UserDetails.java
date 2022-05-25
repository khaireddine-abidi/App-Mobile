package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetails extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase database;
    TextView email, name, role;
    Button del, toadmin, touser;
    String clickeduseremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/users");

        name = findViewById(R.id.nom);
        email = findViewById(R.id.email);
        role = findViewById(R.id.role);
        del = findViewById(R.id.deleteBtn);
        toadmin = findViewById(R.id.toadminBtn);
        touser = findViewById(R.id.touser);

        Intent intent = getIntent();
        clickeduseremail = intent.getStringExtra("email");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userdbemail = dataSnapshot.child("email").getValue().toString();
                    if (userdbemail.toLowerCase().equals(clickeduseremail.toLowerCase())){
                        name.setText(dataSnapshot.child("nom").getValue().toString());
                        Log.i("oooooooooooo", dataSnapshot.child("nom").getValue().toString());
                        email.setText(dataSnapshot.child("email").getValue().toString());
                        role.setText(dataSnapshot.child("role").getValue().toString());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        toadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRole("admin", clickeduseremail);
            }
        });
        touser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeRole("user", clickeduseremail);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(clickeduseremail);
            }
        });


    }

    private void deleteUser(String clickeduseremail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userdbemail = dataSnapshot.child("email").getValue().toString();

                    if (userdbemail.toLowerCase().equals(clickeduseremail.toLowerCase())){
                        String key = dataSnapshot.getKey().toString();
                        myRef.child(key).removeValue();
                        Intent in = new Intent(getApplicationContext() ,Admin.class);
                        startActivity(in);
                        finish();



                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void changeRole(String newRole, String clickeduseremail) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userdbemail = dataSnapshot.child("email").getValue().toString();
                    if (userdbemail.toLowerCase().equals(clickeduseremail.toLowerCase())){
                        String key = dataSnapshot.getKey().toString();
                        myRef.child(key).child("role").setValue(newRole);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}
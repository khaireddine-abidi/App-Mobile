package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {
    DatabaseReference myRef;
    FirebaseDatabase database;
    ArrayAdapter<String> arrayAdapter;

    int index=0;
    ArrayList<String> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/users");
        films = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.listview);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, films);
        listView.setAdapter(arrayAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String myemail = currentUser.getEmail().toString().toLowerCase();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String email = dataSnapshot.child("email").getValue().toString();
                if (email.toLowerCase().equals(myemail)){
                    String key = dataSnapshot.getKey().toString();
                    getHistorique(key);
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

    private void getHistorique(String key) {


        myRef.child(key).child("historique").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.child("action").getValue().toString();
                films.add(value);
                arrayAdapter.notifyDataSetChanged();
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
}
package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/users");
        currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            String email = currentUser.getEmail().toLowerCase().toString();
            checkRole(email);
        }else{
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }






    }

    private void checkRole(String email) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String bd_email = dataSnapshot.child("email").getValue().toString();
                    String role = dataSnapshot.child("role").getValue().toString();
                    Log.i("ooooooooo",role);
                    Log.i("ooooooooo",role);
                    Log.i("ooooooooo",role);
                    if (email.toLowerCase().equals(bd_email.toLowerCase())){

                        if(role.toLowerCase().equals("user")){
                            Intent intent=new Intent(MainActivity.this,Client.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else if (role.toLowerCase().equals("visiteur")){
                            Intent intent=new Intent(MainActivity.this,Visiteur.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else if (role.toLowerCase().equals("admin")){
                            Intent intent=new Intent(MainActivity.this,Admin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


}

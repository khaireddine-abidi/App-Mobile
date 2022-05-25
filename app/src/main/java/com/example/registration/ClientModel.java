package com.example.registration;

public class ClientModel {
    String nom ;
    String email, role ;

    public ClientModel() {
    }

    public ClientModel(String nom, String email, String role ) {
        this.nom = nom;
        this.email = email ;
        this.role = role ;


    }




    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }


}

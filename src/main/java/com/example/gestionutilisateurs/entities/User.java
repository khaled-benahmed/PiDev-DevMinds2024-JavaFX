package com.example.gestionutilisateurs.entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String username;
    private String role;
    private int tel ;
    private String image;

    public User() {
    }

    public User(int id, String email, String password, String firstname,String username, String lastname, String role, int tel, String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.role = role;
        this.tel = tel;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

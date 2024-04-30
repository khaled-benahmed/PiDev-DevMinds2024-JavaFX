package com.example.gestionutilisateurs.entities;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String username;
    private String role;
    private int tel;
    private String image;
    private boolean is_verified;
    private boolean is_blocked;
    private String diplome;
    private Date is_blocked_until;
    private String block_reason;

    public User() {
    }

    public User(int id, String email, String password, String firstname, String username, String lastname, String role, int tel, String image, boolean is_verified, boolean is_blocked, String diplome, Date is_blocked_until, String block_reason) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.role = role;
        this.tel = tel;
        this.image = image;
        this.is_verified = is_verified;
        this.is_blocked = is_blocked;
        this.diplome = diplome;
        this.is_blocked_until = is_blocked_until;
        this.block_reason = block_reason;
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

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public Date getIs_blocked_until() {
        return is_blocked_until;
    }

    public void setIs_blocked_until(Date is_blocked_until) {
        this.is_blocked_until = is_blocked_until;
    }

    public String getBlock_reason() {
        return block_reason;
    }

    public void setBlock_reason(String block_reason) {
        this.block_reason = block_reason;
    }
}

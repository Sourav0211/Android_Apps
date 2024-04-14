package com.example.assignment11.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class User implements Serializable {


    String name;
    String email;

    String Uid;
    HashMap<String,Boolean>  blocked;


    public User() {
    }

    public User(String name, String email, String Uid) {
        this.name = name;
        this.email = email;
        this.Uid = Uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String,Boolean> getBlocked() {
        return blocked;
    }

    public void setBlocked(HashMap<String,Boolean> blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", blocked=" + blocked + '\'' +
                ",Uid=" + Uid +
                '}';
    }
}

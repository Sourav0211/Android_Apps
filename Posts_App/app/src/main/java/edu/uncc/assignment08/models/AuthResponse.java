package edu.uncc.assignment08.models;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    String user_id, user_fullname, token;

    public AuthResponse() {
    }
    public AuthResponse(String user_id,String user_fullname,String token)
    {
        this.user_id = user_id ;
        this.user_fullname = user_fullname;
        this.token = token;

    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "user_id='" + user_id + '\'' +
                ", user_fullname='" + user_fullname + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
/*

    {
    "status": "ok",
    "token": "wcUJRckNsODlsbkgyZUJS",
    "user_id": 1,
    "user_fullname": "Alice Smith"
    }
     */
}

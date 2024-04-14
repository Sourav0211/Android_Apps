package edu.uncc.gradesapp.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;

public class Review implements Serializable {

    public String description;
    public String userName;
    public Timestamp createdAt;

    public String docId;
    public String userId;




    public Review(String description, String userName, Timestamp createdAt, String docId, String userId) {
        this.description = description;
        this.userName = userName;
        this.createdAt = createdAt;
        this.docId = docId;
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Review() {
    }
}

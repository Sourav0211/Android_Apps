package com.example.assignment03;

import java.io.Serializable;

public class Person implements Serializable {
    String name;
    String email;
    String role,education;

    String livingStatus;
    String maritalStatus;
    String income;

    public String getLivingStatus() {
        return livingStatus;
    }

    public void setLivingStatus(String livingStatus) {
        this.livingStatus = livingStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public Person(String name, String email, String role, String education, String livingStatus, String maritalStatus, String income) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.education = education;
        this.maritalStatus = maritalStatus;
        this.income = income;
        this.livingStatus = livingStatus;
    }


    public Person() {

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}

package edu.uncc.gradesapp.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    public String courseId;
    public String name;
    public String docId;
    public String userId;
    public String number;
    public double hours;


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

    public Course() {
    }

    public Course(JSONObject json) {
        this.courseId = json.optString("courseId");
        this.name = json.optString("name");
        this.number = json.optString("number");
        this.hours = json.optDouble("hours");
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", docId='" + docId + '\'' +
                ", userId='" + userId + '\'' +
                ", number='" + number + '\'' +
                ", hours=" + hours +
                '}';
    }
    /*
      {
            "courseId": "e3a6ef8a-b1a8-4bc2-8f86-fb7f5c276c0b",
            "name": "Introduction to Computer Science I",
            "number": "ITSC 1212",
            "hours": 4
        },
     */
}

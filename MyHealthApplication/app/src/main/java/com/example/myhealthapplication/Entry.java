package com.example.myhealthapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "entry")
public class Entry {
//number of sleep hours, number of
//exercise minutes, and weight.

    @PrimaryKey(autoGenerate = true)
    int _id;
    Double sleepTime;
    Double exerciseTime;
    Double weight;
    int sleepRating;
//    Date date;


    public Entry(Double sleepTime, Double exerciseTime, Double weight, int sleepRating) {
        this.sleepTime = sleepTime;
        this.exerciseTime = exerciseTime;
        this.weight = weight;
        this.sleepRating = sleepRating;
    }

    public Entry() {
    }

    public Double getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Double sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Double getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(Double exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getSleepRating() {
        return sleepRating;
    }

    public void setSleepRating(int sleepRating) {
        this.sleepRating = sleepRating;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    @Override
    public String toString() {
        return "Entries{" +
                "sleepTime=" + sleepTime +
                ", exerciseTime=" + exerciseTime +
                ", weight=" + weight +
                ", sleepRating=" + sleepRating +
//                ", date=" + date +
                '}';
    }
}

package com.example.assignment12;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.anychart.scales.DateTime;

import java.util.Date;



@Entity(tableName = "details")
@TypeConverters(DateConverter.class)
public class DetailsModel {
    @PrimaryKey(autoGenerate = true)
    public int id;


    Date date;
    String sleepHrs;
    String sleepQuality;
    String exerciseTime;
    int weight;

    public DetailsModel(Date  date, String sleepHrs, String sleepQuality, String exerciseTime, int weight) {
        this.date = date;
        this.sleepHrs = sleepHrs;
        this.sleepQuality = sleepQuality;
        this.exerciseTime = exerciseTime;
        this.weight = weight;
    }

    public DetailsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date  date) {
        this.date = date;
    }

    public String getSleepHrs() {
        return sleepHrs;
    }

    public void setSleepHrs(String sleepHrs) {
        this.sleepHrs = sleepHrs;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public String getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(String exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

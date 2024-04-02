package edu.uncc.gradesapp.models;

import java.util.ArrayList;

public class CourseReview {

    String courseId;
    int numberOfReviews = 0;
    ArrayList<String> favoriteBy = new ArrayList<>();

    @Override
    public String toString() {
        return "CourseReview{" +
                "courseId='" + courseId + '\'' +
                ", numberOfReviews=" + numberOfReviews +
                ", favoriteBy=" + favoriteBy +
                '}';
    }

    public CourseReview() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public ArrayList<String> getFavoriteBy() {
        return favoriteBy;
    }

    public void setFavoriteBy(ArrayList<String> favoriteBy) {
        this.favoriteBy = favoriteBy;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }
}

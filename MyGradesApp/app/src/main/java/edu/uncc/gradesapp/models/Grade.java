package edu.uncc.gradesapp.models;

import java.sql.Timestamp;

public class Grade{
    public String courseId,courseName,courseNumber,docId
            ,gradeLetter,ownerId,ownerName,semesterName;
    public int courseHours,gradeValue,semesterMonth,semesterYear;
//    public Timestamp createdAt;

    public Grade(String courseId, String courseName, String courseNumber, String docId, String gradeLetter, String ownerId, String ownerName, String semesterName, int courseHours, int gradeValue, int semesterMonth, int semesterYear) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.docId = docId;
        this.gradeLetter = gradeLetter;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.semesterName = semesterName;
        this.courseHours = courseHours;
        this.gradeValue = gradeValue;
        this.semesterMonth = semesterMonth;
        this.semesterYear = semesterYear;
//        this.createdAt = createdAt;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }

    public void setGradeLetter(String gradeLetter) {
        this.gradeLetter = gradeLetter;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public int getGradeValue() {
        return courseHours * gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    public int getSemesterMonth() {
        return semesterMonth;
    }

    public void setSemesterMonth(int semesterMonth) {
        this.semesterMonth = semesterMonth;
    }

    public int getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(int semesterYear) {
        this.semesterYear = semesterYear;
    }

//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }

    public Grade() {
    }

    @Override
    public String toString() {
        return "Grade{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", docId='" + docId + '\'' +
                ", gradeLetter='" + gradeLetter + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", semesterName='" + semesterName + '\'' +
                ", courseHours=" + courseHours +
                ", gradeValue=" + gradeValue +
                ", semesterMonth=" + semesterMonth +
                ", semesterYear=" + semesterYear +
                '}';
    }
}

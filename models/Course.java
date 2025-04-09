package models;

import java.io.Serializable;

public class Course implements Serializable {
    private static int count = 1;
    private int courseId;
    private String courseName;

    public Course(String courseName) {
        this.courseName = courseName;
        this.courseId = count++;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String toString() {
        return courseId + ": " + courseName;
    }
}

package services;

import models.Course;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courseList;
    private static final String FILE_NAME = "courses.dat";

    public CourseService() {
        courseList = loadCourses();
    }

    public void addCourse(Course course) {
        courseList.add(course);
        saveCourses();
    }

    public void deleteCourse(int id) {
        courseList.removeIf(c -> c.getCourseId() == id);
        saveCourses();
    }

    public List<Course> getAllCourses() {
        return courseList;
    }

    public Course getCourseById(int id) {
        for (Course c : courseList) {
            if (c.getCourseId() == id) return c;
        }
        return null;
    }

    private void saveCourses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(courseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public CourseService() {
        courseList = loadCourses();
        if (courseList.isEmpty()) {
            courseList.add(new Course("Java Fundamentals"));
            courseList.add(new Course("Data Structures"));
            courseList.add(new Course("Web Development"));
            saveCourses();
        }
    }    

    private List<Course> loadCourses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Course>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}

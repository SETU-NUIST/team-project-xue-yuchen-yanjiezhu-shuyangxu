package entity.dao.impl;
import dao.CourseDao;
import entity.Course;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseFileDao implements CourseDao {
    private static final String FILE_PATH = "data/courses.json";

    @Override
    public boolean addCourse(Course course) {
        List<Course> courses = JsonUtil.readListFromFile(FILE_PATH, Course.class);
        if (courses == null) {
            courses = new ArrayList<>();
        }
        boolean exists = courses.stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()));
        if (exists) {
            return false;
        }
        courses.add(course);
        JsonUtil.writeObjectToFile(courses, FILE_PATH);
        return true;
    }
    @Override
    public boolean updateCourse(Course course) {
        List<Course> courses = JsonUtil.readListFromFile(FILE_PATH, Course.class);
        if (courses == null) {
            return false;
        }
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getCourseId().equals(course.getCourseId())) {
                courses.set(i, course);
                JsonUtil.writeObjectToFile(courses, FILE_PATH);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean deleteCourse(String courseId) {
        List<Course> courses = JsonUtil.readListFromFile(FILE_PATH, Course.class);
        if (courses == null) {
            return false;
        }
        List<Course> newCourses = courses.stream()
                .filter(c -> !c.getCourseId().equals(courseId))
                .collect(Collectors.toList());
        if (courses.size() == newCourses.size()) {
            return false;
        }
        JsonUtil.writeObjectToFile(newCourses, FILE_PATH);
        return true;
    }

    @Override
    public Course getCourseById(String courseId) {
        List<Course> courses = JsonUtil.readListFromFile(FILE_PATH, Course.class);
        if (courses == null) {
            return null;
        }
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Course> listAllCourses() {
        List<Course> courses = JsonUtil.readListFromFile(FILE_PATH, Course.class);
        return courses == null ? new ArrayList<>() : courses;
    }

    @Override
    public List<Course> listCoursesByName(String name) {
        List<Course> courses = JsonUtil.readListFromFile(FILE_PATH, Course.class);
        if (courses == null || name.isEmpty()) {
            return listAllCourses();
        }
        return courses.stream()
                .filter(c -> c.getCourseName().contains(name))
                .collect(Collectors.toList());
    }
}
package entity.service;
import entity.Course;
import java.util.List;

public interface CourseService {
    String addCourse(Course course);
    String updateCourse(Course course);
    String deleteCourse(String courseId);
    Course getCourseById(String courseId);
    List<Course> listAllCourses();
    List<Course> listCoursesByName(String name);
}




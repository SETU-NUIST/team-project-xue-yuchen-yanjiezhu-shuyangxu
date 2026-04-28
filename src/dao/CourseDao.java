package dao;

    import entity.Course;

    import java.util.List;

    public interface CourseDao {
        boolean addCourse(Course course);
        boolean updateCourse(Course course);
        boolean deleteCourse(String courseId);
        Course getCourseById(String courseId);
        List<Course> listAllCourses();
        List<Course> listCoursesByName(String name);
    }


package util;

import java.util.regex.Pattern;

public class DataValidateUtil {
    //student
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,12}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]{2,10}$");

    public static boolean validateStudentId(String studentId) {
        return studentId != null && STUDENT_ID_PATTERN.matcher(studentId.trim()).matches();
    }

    public static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty() && NAME_PATTERN.matcher(name.trim()).matches();
    }

    public static boolean validateGender(String gender) {
        return "man".equals(gender) || "woman".equals(gender);
    }
  //course
    private static final Pattern COURSE_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,8}$");
    private static final Pattern COURSE_NAME_PATTERN = Pattern.compile("^.{2,20}$");
    private static final Pattern CREDIT_PATTERN = Pattern.compile("^([0-9]|10)(\\.\\d+)?$");

    public static boolean validateCourseId(String courseId) {
        return courseId != null && COURSE_ID_PATTERN.matcher(courseId.trim()).matches();
    }

    public static boolean validateCourseName(String courseName) {
        return courseName != null && !courseName.trim().isEmpty() && COURSE_NAME_PATTERN.matcher(courseName.trim()).matches();
    }

    public static boolean validateCredit(String credit) {
        return credit != null && CREDIT_PATTERN.matcher(credit.trim()).matches();
    }
}



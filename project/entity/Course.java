package entity.entity;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseId;   // 课程编号（唯一）
    private String courseName; // 课程名称
    private String credit;     // 学分（字符串，后续Service校验是否是数字）

    public Course() {}

    public Course(String courseId, String courseName, String credit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
    }

    // Getter & Setter
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

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit='" + credit + '\'' +
                '}';
    }
}

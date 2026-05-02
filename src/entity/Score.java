package entity;

import java.io.Serializable;

public class Score implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId; // 学号（关联学生）
    private String courseId;  // 课程编号（关联课程）
    private String score;     // 分数（0-100）
    private String examTime;  // 考试时间（格式：2025-01-01）

    // 主键：学号+课程编号（唯一）
    public Score() {}

    public Score(String studentId, String courseId, String score, String examTime) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.examTime = examTime;
    }

    // Getter & Setter
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    @Override
    public String toString() {
        return "Score{" +
                "studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", score='" + score + '\'' +
                ", examTime='" + examTime + '\'' +
                '}';
    }
}

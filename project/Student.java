package entity;

import java.io.Serializable;

/**
 * 学生实体类
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId;  // 学号（唯一）
    private String name;       // 姓名
    private String gender;     // 性别
    private String grade;      // 年级
    private String className;  // 班级

    // 无参构造
    public Student() {}

    // 全参构造
    public Student(String studentId, String name, String gender, String grade, String className) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.grade = grade;
        this.className = className;
    }

    // Getter & Setter
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", grade='" + grade + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}


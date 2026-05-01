package service;


import entity.Student;

import java.util.List;

/**
 * 学生业务逻辑接口：封装学生相关业务规则
 */
public interface StudentService {
    /**
     * 新增学生（含数据校验）
     */
    String addStudent(Student student);

    /**
     * 删除学生
     */
    String deleteStudent(String studentId);

    /**
     * 修改学生信息（含数据校验）
     */
    String updateStudent(Student student);

    /**
     * 根据学号查询学生
     */
    Student getStudentById(String studentId);

    /**
     * 查询所有学生
     */
    List<Student> listAllStudents();

    /**
     * 根据姓名模糊查询学生
     */
    List<Student> listStudentsByName(String name);
}
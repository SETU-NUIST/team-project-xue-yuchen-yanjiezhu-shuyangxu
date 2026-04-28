package dao;

import entity.Student;

import java.util.List;

/**
 * 学生数据访问接口：定义学生数据的CRUD操作
 */
public interface StudentDao {
    /**
     * 新增学生
     */
    boolean addStudent(Student student);

    /**
     * 根据学号删除学生
     */
    boolean deleteStudent(String studentId);

    /**
     * 修改学生信息
     */
    boolean updateStudent(Student student);

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
package dao.impl;

import dao.StudentDao;
import entity.Student;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生数据访问实现类：基于本地JSON文件存储
 */
public class StudentFileDao implements StudentDao {
    private static final String FILE_PATH = "data/students.json";

    @Override
    public boolean addStudent(Student student) {
        List<Student> students = JsonUtil.readListFromFile(FILE_PATH, Student.class);
        if (students == null) {
            students = new ArrayList<>();
        }
        // 校验学号是否已存在
        if (students.stream().anyMatch(s -> s.getStudentId().equals(student.getStudentId()))) {
            return false;
        }
        students.add(student);
        JsonUtil.writeObjectToFile(students, FILE_PATH);
        return true;
    }

    @Override
    public boolean deleteStudent(String studentId) {
        List<Student> students = JsonUtil.readListFromFile(FILE_PATH, Student.class);
        if (students == null || students.isEmpty()) {
            return false;
        }
        long count = students.stream().filter(s -> s.getStudentId().equals(studentId)).count();
        if (count == 0) {
            return false;
        }
        List<Student> newStudents = students.stream()
                .filter(s -> !s.getStudentId().equals(studentId))
                .collect(Collectors.toList());
        JsonUtil.writeObjectToFile(newStudents, FILE_PATH);
        return true;
    }

    @Override
    public boolean updateStudent(Student student) {
        List<Student> students = JsonUtil.readListFromFile(FILE_PATH, Student.class);
        if (students == null || students.isEmpty()) {
            return false;
        }
        // 查找并替换原有学生信息
        boolean updated = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(student.getStudentId())) {
                students.set(i, student);
                updated = true;
                break;
            }
        }
        if (updated) {
            JsonUtil.writeObjectToFile(students, FILE_PATH);
        }
        return updated;
    }

    @Override
    public Student getStudentById(String studentId) {
        List<Student> students = JsonUtil.readListFromFile(FILE_PATH, Student.class);
        if (students == null) {
            return null;
        }
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Student> listAllStudents() {
        List<Student> students = JsonUtil.readListFromFile(FILE_PATH, Student.class);
        return students == null ? new ArrayList<>() : students;
    }

    @Override
    public List<Student> listStudentsByName(String name) {
        List<Student> students = JsonUtil.readListFromFile(FILE_PATH, Student.class);
        if (students == null || name == null || name.isEmpty()) {
            return new ArrayList<>();
        }
        return students.stream()
                .filter(s -> s.getName().contains(name))
                .collect(Collectors.toList());
    }
}
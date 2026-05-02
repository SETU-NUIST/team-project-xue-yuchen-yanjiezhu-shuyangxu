package service;

import dao.StudentDao;
import dao.StudentFileDao;
import entity.Student;
import util.DataValidateUtil;

import java.util.List;

/**
 * 学生业务逻辑实现类：处理业务规则和数据校验
 */
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao = new StudentFileDao();

    @Override
    public String addStudent(Student student) {
        // 数据校验
        if (!DataValidateUtil.validateStudentId(student.getStudentId())) {
            return "学号格式错误（仅支持字母+数字，长度6-12位）";
        }
        if (!DataValidateUtil.validateName(student.getName())) {
            return "姓名格式错误（仅支持中文/英文，长度2-10位）";
        }
        if (!DataValidateUtil.validateGender(student.getGender())) {
            return "性别格式错误（仅支持：男/女）";
        }
        if (student.getGrade() == null || student.getGrade().isEmpty()) {
            return "年级不能为空";
        }
        if (student.getClassName() == null || student.getClassName().isEmpty()) {
            return "班级不能为空";
        }

        // 调用DAO层新增
        boolean success = studentDao.addStudent(student);
        return success ? "新增学生成功" : "学号已存在，新增失败";
    }

    @Override
    public String deleteStudent(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return "学号不能为空";
        }
        boolean success = studentDao.deleteStudent(studentId);
        return success ? "删除学生成功" : "学号不存在，删除失败";
    }

    @Override
    public String updateStudent(Student student) {
        // 复用新增的校验规则
        String validateMsg = addStudent(student);
        if (!validateMsg.equals("新增学生成功")) {
            return validateMsg;
        }
        boolean success = studentDao.updateStudent(student);
        return success ? "修改学生信息成功" : "学号不存在，修改失败";
    }

    @Override
    public Student getStudentById(String studentId) {
        if (!DataValidateUtil.validateStudentId(studentId)) {
            return null;
        }
        return studentDao.getStudentById(studentId);
    }

    @Override
    public List<Student> listAllStudents() {
        return studentDao.listAllStudents();
    }

    @Override
    public List<Student> listStudentsByName(String name) {
        return studentDao.listStudentsByName(name);
    }
}
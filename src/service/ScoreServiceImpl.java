package service;

import dao.ScoreDao;
import dao.ScoreFileDao;
import dao.StudentDao;
import dao.CourseDao;
import dao.StudentFileDao;
import dao.CourseFileDao;
import entity.Score;
import util.DataValidateUtil;

import java.util.List;

public class ScoreServiceImpl implements ScoreService {
    private final ScoreDao scoreDao = new ScoreFileDao();
    private final StudentDao studentDao = new StudentFileDao();
    private final CourseDao courseDao = new CourseFileDao();

    @Override
    public String addScore(Score score) {
        if (studentDao.getStudentById(score.getStudentId()) == null) {
            return "学生不存在，新增成绩失败";
        }
        if (courseDao.getCourseById(score.getCourseId()) == null) {
            return "课程不存在，新增成绩失败";
        }
        if (!DataValidateUtil.validateScore(score.getScore())) {
            return "分数格式错误（必须是0-100之间的数字）";
        }
        if (!DataValidateUtil.validateExamTime(score.getExamTime())) {
            return "考试时间格式错误（正确格式：2025-01-01）";
        }
        boolean success = scoreDao.addScore(score);
        return success ? "新增成绩成功" : "该学生已选该课程，成绩重复";
    }

    @Override
    public String updateScore(Score score) {
        if (scoreDao.getScoreByStudentAndCourse(score.getStudentId(), score.getCourseId()) == null) {
            return "成绩不存在，修改失败";
        }
        if (!DataValidateUtil.validateScore(score.getScore())) {
            return "分数格式错误（必须是0-100之间的数字）";
        }
        if (!DataValidateUtil.validateExamTime(score.getExamTime())) {
            return "考试时间格式错误（正确格式：2025-01-01）";
        }
        boolean success = scoreDao.updateScore(score);
        return success ? "修改成绩成功" : "修改成绩失败";
    }

    @Override
    public String deleteScore(String studentId, String courseId) {
        if (scoreDao.getScoreByStudentAndCourse(studentId, courseId) == null) {
            return "成绩不存在，删除失败";
        }
        boolean success = scoreDao.deleteScore(studentId, courseId);
        return success ? "删除成绩成功" : "删除成绩失败";
    }

    @Override
    public Score getScoreByStudentAndCourse(String studentId, String courseId) {
        return scoreDao.getScoreByStudentAndCourse(studentId, courseId);
    }

    @Override
    public List<Score> listAllScores() {
        return scoreDao.listAllScores();
    }

    @Override
    public List<Score> listScoresByStudentId(String studentId) {
        return scoreDao.listScoresByStudentId(studentId);
    }
}
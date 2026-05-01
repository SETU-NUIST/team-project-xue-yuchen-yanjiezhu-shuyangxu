package dao;

import entity.Score;
import java.util.List;

public interface ScoreDao {
    boolean addScore(Score score);
    boolean updateScore(Score score);
    boolean deleteScore(String studentId, String courseId);
    Score getScoreByStudentAndCourse(String studentId, String courseId);
    List<Score> listAllScores();
    List<Score> listScoresByStudentId(String studentId);
}

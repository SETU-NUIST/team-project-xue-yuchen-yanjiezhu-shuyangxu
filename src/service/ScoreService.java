package service;

import entity.Score;
import java.util.List;

public interface ScoreService {
    String addScore(Score score);
    String updateScore(Score score);
    String deleteScore(String studentId, String courseId);
    Score getScoreByStudentAndCourse(String studentId, String courseId);
    List<Score> listAllScores();
    List<Score> listScoresByStudentId(String studentId);
}

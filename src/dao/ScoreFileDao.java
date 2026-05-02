package dao;

import entity.Score;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreFileDao implements ScoreDao {
    private static final String FILE_PATH = "data/scores.json";

    @Override
    public boolean addScore(Score score) {
        List<Score> scores = JsonUtil.readListFromFile(FILE_PATH, Score.class);
        if (scores == null) {
            scores = new ArrayList<>();
        }
        boolean exists = scores.stream().anyMatch(s ->
                s.getStudentId().equals(score.getStudentId())
                        && s.getCourseId().equals(score.getCourseId())
        );
        if (exists) {
            return false;
        }
        scores.add(score);
        JsonUtil.writeObjectToFile(scores, FILE_PATH);
        return true;
    }

    @Override
    public boolean updateScore(Score score) {
        List<Score> scores = JsonUtil.readListFromFile(FILE_PATH, Score.class);
        if (scores == null) {
            return false;
        }
        for (int i = 0; i < scores.size(); i++) {
            Score s = scores.get(i);
            if (s.getStudentId().equals(score.getStudentId()) && s.getCourseId().equals(score.getCourseId())) {
                scores.set(i, score);
                JsonUtil.writeObjectToFile(scores, FILE_PATH);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteScore(String studentId, String courseId) {
        List<Score> scores = JsonUtil.readListFromFile(FILE_PATH, Score.class);
        if (scores == null) {
            return false;
        }
        List<Score> newScores = scores.stream()
                .filter(s -> !(s.getStudentId().equals(studentId) && s.getCourseId().equals(courseId)))
                .collect(Collectors.toList());
        if (scores.size() == newScores.size()) {
            return false;
        }
        JsonUtil.writeObjectToFile(newScores, FILE_PATH);
        return true;
    }

    @Override
    public Score getScoreByStudentAndCourse(String studentId, String courseId) {
        List<Score> scores = JsonUtil.readListFromFile(FILE_PATH, Score.class);
        if (scores == null) {
            return null;
        }
        for (Score score : scores) {
            if (score.getStudentId().equals(studentId) && score.getCourseId().equals(courseId)) {
                return score;
            }
        }
        return null;
    }

    @Override
    public List<Score> listAllScores() {
        List<Score> scores = JsonUtil.readListFromFile(FILE_PATH, Score.class);
        return scores == null ? new ArrayList<>() : scores;
    }

    @Override
    public List<Score> listScoresByStudentId(String studentId) {
        List<Score> scores = JsonUtil.readListFromFile(FILE_PATH, Score.class);
        if (scores == null || studentId.isEmpty()) {
            return listAllScores();
        }
        return scores.stream()
                .filter(s -> s.getStudentId().contains(studentId))
                .collect(Collectors.toList());
    }
}

package viewfx;

import entity.Course;
import entity.Score;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.CourseService;
import service.ScoreService;
import service.StudentService;
import service.impl.CourseServiceImpl;
import service.impl.ScoreServiceImpl;
import service.impl.StudentServiceImpl;

import java.util.List;

/**
 * JavaFX version of ScoreManagePanel.
 */
public class ScoreManageView extends BorderPane {
    private final ScoreService scoreService = new ScoreServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();

    private final ComboBox<String> cbStudent = new ComboBox<>();
    private final ComboBox<String> cbCourse = new ComboBox<>();
    private final TextField tfScore = new TextField();
    private final TextField tfExamTime = new TextField();
    private final TextField tfSearchStudentId = new TextField();

    private final TableView<ScoreTableRow> tableView = new TableView<>();
    private final ObservableList<ScoreTableRow> tableItems = FXCollections.observableArrayList();

    public ScoreManageView() {
        initUI();
        loadStudentToComboBox();
        loadCourseToComboBox();
        loadScoreData();
    }

    private void initUI() {
        setPadding(new Insets(10));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        cbStudent.setPrefWidth(170);
        cbCourse.setPrefWidth(170);
        tfExamTime.setPromptText("yyyy-MM-dd");

        form.add(new Label("Select Student:"), 0, 0);
        form.add(cbStudent, 1, 0);
        form.add(new Label("Select Course:"), 2, 0);
        form.add(cbCourse, 3, 0);
        form.add(new Label("Score:"), 4, 0);
        form.add(tfScore, 5, 0);
        form.add(new Label("Exam Time:"), 6, 0);
        form.add(tfExamTime, 7, 0);

        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Modify");
        Button btnDelete = new Button("Delete");
        HBox actionBox = new HBox(10, btnAdd, btnUpdate, btnDelete);
        form.add(actionBox, 0, 1, 4, 1);

        form.add(new Label("Student ID Search:"), 4, 1);
        form.add(tfSearchStudentId, 5, 1, 2, 1);
        Button btnSearch = new Button("Search");
        Button btnReset = new Button("Reset");
        HBox searchBox = new HBox(10, btnSearch, btnReset);
        form.add(searchBox, 7, 1);

        TableColumn<ScoreTableRow, String> colStudentId = new TableColumn<>("Student ID");
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<ScoreTableRow, String> colStudentName = new TableColumn<>("Name");
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<ScoreTableRow, String> colCourseId = new TableColumn<>("Course Code");
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        TableColumn<ScoreTableRow, String> colCourseName = new TableColumn<>("Course Name");
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<ScoreTableRow, String> colScore = new TableColumn<>("Score");
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<ScoreTableRow, String> colExamTime = new TableColumn<>("Exam Time");
        colExamTime.setCellValueFactory(new PropertyValueFactory<>("examTime"));

        tableView.getColumns().addAll(colStudentId, colStudentName, colCourseId, colCourseName, colScore, colExamTime);
        tableView.setItems(tableItems);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                setComboBoxSelectedItem(cbStudent, selected.getStudentId());
                setComboBoxSelectedItem(cbCourse, selected.getCourseId());
                tfScore.setText(selected.getScore());
                tfExamTime.setText(selected.getExamTime());
            }
        });

        btnAdd.setOnAction(e -> addScore());
        btnUpdate.setOnAction(e -> updateScore());
        btnDelete.setOnAction(e -> deleteScore());
        btnSearch.setOnAction(e -> searchScoreByStudentId());
        btnReset.setOnAction(e -> resetForm());

        setTop(new VBox(10, form));
        setCenter(tableView);
    }

    private void loadStudentToComboBox() {
        List<Student> students = studentService.listAllStudents();
        cbStudent.getItems().clear();
        for (Student student : students) {
            cbStudent.getItems().add(student.getStudentId() + "-" + student.getName());
        }
        if (!cbStudent.getItems().isEmpty()) {
            cbStudent.getSelectionModel().selectFirst();
        }
    }

    private void loadCourseToComboBox() {
        List<Course> courses = courseService.listAllCourses();
        cbCourse.getItems().clear();
        for (Course course : courses) {
            cbCourse.getItems().add(course.getCourseId() + "-" + course.getCourseName());
        }
        if (!cbCourse.getItems().isEmpty()) {
            cbCourse.getSelectionModel().selectFirst();
        }
    }

    private void loadScoreData() {
        List<Score> scores = scoreService.listAllScores();
        tableItems.clear();
        for (Score score : scores) {
            Student student = studentService.getStudentById(score.getStudentId());
            String studentName = student == null ? "" : student.getName();
            Course course = courseService.getCourseById(score.getCourseId());
            String courseName = course == null ? "" : course.getCourseName();
            tableItems.add(new ScoreTableRow(
                    score.getStudentId(),
                    studentName,
                    score.getCourseId(),
                    courseName,
                    score.getScore(),
                    score.getExamTime()
            ));
        }
    }

    private void addScore() {
        Score score = buildScoreFromForm();
        if (score == null) {
            return;
        }
        String result = scoreService.addScore(score);
        showInfo(result);
        if (isSuccess(result)) {
            loadScoreData();
            resetForm();
        }
    }

    private void updateScore() {
        Score score = buildScoreFromForm();
        if (score == null) {
            return;
        }
        String result = scoreService.updateScore(score);
        showInfo(result);
        if (isSuccess(result)) {
            loadScoreData();
            resetForm();
        }
    }

    private void deleteScore() {
        String studentItem = cbStudent.getValue();
        String courseItem = cbCourse.getValue();
        if (studentItem == null || courseItem == null) {
            showWarn("Please select the score to delete");
            return;
        }
        String studentId = studentItem.split("-", 2)[0];
        String courseId = courseItem.split("-", 2)[0];

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this score?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirm Deletion");
        confirm.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                String result = scoreService.deleteScore(studentId, courseId);
                showInfo(result);
                if (isSuccess(result)) {
                    loadScoreData();
                    resetForm();
                }
            }
        });
    }

    private void searchScoreByStudentId() {
        String studentId = tfSearchStudentId.getText().trim();
        List<Score> scores = scoreService.listScoresByStudentId(studentId);
        tableItems.clear();
        for (Score score : scores) {
            Student student = studentService.getStudentById(score.getStudentId());
            String studentName = student == null ? "" : student.getName();
            Course course = courseService.getCourseById(score.getCourseId());
            String courseName = course == null ? "" : course.getCourseName();
            tableItems.add(new ScoreTableRow(
                    score.getStudentId(),
                    studentName,
                    score.getCourseId(),
                    courseName,
                    score.getScore(),
                    score.getExamTime()
            ));
        }
    }

    private void resetForm() {
        if (!cbStudent.getItems().isEmpty()) {
            cbStudent.getSelectionModel().selectFirst();
        }
        if (!cbCourse.getItems().isEmpty()) {
            cbCourse.getSelectionModel().selectFirst();
        }
        tfScore.clear();
        tfExamTime.clear();
        tfSearchStudentId.clear();
        loadScoreData();
    }

    private Score buildScoreFromForm() {
        String studentItem = cbStudent.getValue();
        if (studentItem == null) {
            showWarn("Please select the student");
            return null;
        }
        String studentId = studentItem.split("-", 2)[0];

        String courseItem = cbCourse.getValue();
        if (courseItem == null) {
            showWarn("Please select the course");
            return null;
        }
        String courseId = courseItem.split("-", 2)[0];

        Score score = new Score();
        score.setStudentId(studentId);
        score.setCourseId(courseId);
        score.setScore(tfScore.getText().trim());
        score.setExamTime(tfExamTime.getText().trim());
        return score;
    }

    private void setComboBoxSelectedItem(ComboBox<String> comboBox, String targetId) {
        for (String item : comboBox.getItems()) {
            if (item.startsWith(targetId + "-")) {
                comboBox.setValue(item);
                break;
            }
        }
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarn(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isSuccess(String result) {
        return result != null && (result.contains("success") || result.contains("成功"));
    }

    public static class ScoreTableRow {
        private final String studentId;
        private final String studentName;
        private final String courseId;
        private final String courseName;
        private final String score;
        private final String examTime;

        public ScoreTableRow(String studentId, String studentName, String courseId, String courseName, String score, String examTime) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.courseId = courseId;
            this.courseName = courseName;
            this.score = score;
            this.examTime = examTime;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getScore() {
            return score;
        }

        public String getExamTime() {
            return examTime;
        }
    }
}
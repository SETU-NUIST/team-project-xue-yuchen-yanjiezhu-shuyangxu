package viewfx;

import entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import service.CourseService;
import service.impl.CourseServiceImpl;

import java.util.List;

/**
 * JavaFX version of CourseManagePanel.
 */
public class CourseManageView extends BorderPane {
    private final CourseService courseService = new CourseServiceImpl();

    private final TextField tfCourseId = new TextField();
    private final TextField tfCourseName = new TextField();
    private final TextField tfCredit = new TextField();
    private final TextField tfSearchName = new TextField();

    private final TableView<Course> tableView = new TableView<>();
    private final ObservableList<Course> tableItems = FXCollections.observableArrayList();

    public CourseManageView() {
        initUI();
        loadCourseData();
    }

    private void initUI() {
        setPadding(new Insets(10));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        tfCourseId.setPromptText("3-8 letters or digits");
        tfCourseName.setPromptText("2-20 chars");
        tfCredit.setPromptText("0-10");

        form.add(new Label("Course Number:"), 0, 0);
        form.add(tfCourseId, 1, 0);
        form.add(new Label("Course Name:"), 2, 0);
        form.add(tfCourseName, 3, 0);
        form.add(new Label("Credit:"), 4, 0);
        form.add(tfCredit, 5, 0);

        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Modify");
        Button btnDelete = new Button("Delete");
        HBox actionBox = new HBox(10, btnAdd, btnUpdate, btnDelete);
        form.add(actionBox, 0, 1, 3, 1);

        form.add(new Label("Course Name Search:"), 3, 1);
        form.add(tfSearchName, 4, 1);
        Button btnSearch = new Button("Search");
        Button btnReset = new Button("Reset");
        HBox searchBox = new HBox(10, btnSearch, btnReset);
        form.add(searchBox, 5, 1);

        TableColumn<Course, String> colId = new TableColumn<>("Course Number");
        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        TableColumn<Course, String> colName = new TableColumn<>("Course Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Course, String> colCredit = new TableColumn<>("Credit");
        colCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));

        tableView.getColumns().addAll(colId, colName, colCredit);
        tableView.setItems(tableItems);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                tfCourseId.setText(selected.getCourseId());
                tfCourseName.setText(selected.getCourseName());
                tfCredit.setText(selected.getCredit());
            }
        });

        btnAdd.setOnAction(e -> addCourse());
        btnUpdate.setOnAction(e -> updateCourse());
        btnDelete.setOnAction(e -> deleteCourse());
        btnSearch.setOnAction(e -> searchCourseByName());
        btnReset.setOnAction(e -> resetForm());

        VBox topBox = new VBox(10, form);
        setTop(topBox);
        setCenter(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    private void loadCourseData() {
        List<Course> courses = courseService.listAllCourses();
        tableItems.setAll(courses);
    }

    private void addCourse() {
        Course course = buildCourseFromForm();
        String result = courseService.addCourse(course);
        showInfo(result);
        if (isSuccess(result)) {
            loadCourseData();
            resetForm();
        }
    }

    private void updateCourse() {
        Course course = buildCourseFromForm();
        String result = courseService.updateCourse(course);
        showInfo(result);
        if (isSuccess(result)) {
            loadCourseData();
            resetForm();
        }
    }

    private void deleteCourse() {
        String courseId = tfCourseId.getText().trim();
        if (courseId.isEmpty()) {
            showWarn("Please select the course you want to delete!");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the course?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirm Deletion");
        confirm.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                String result = courseService.deleteCourse(courseId);
                showInfo(result);
                if (isSuccess(result)) {
                    loadCourseData();
                    resetForm();
                }
            }
        });
    }

    private void searchCourseByName() {
        String name = tfSearchName.getText().trim();
        List<Course> courses = courseService.listCoursesByName(name);
        tableItems.setAll(courses);
    }

    private void resetForm() {
        tfCourseId.clear();
        tfCourseName.clear();
        tfCredit.clear();
        tfSearchName.clear();
        loadCourseData();
    }

    private Course buildCourseFromForm() {
        Course course = new Course();
        course.setCourseId(tfCourseId.getText().trim());
        course.setCourseName(tfCourseName.getText().trim());
        course.setCredit(tfCredit.getText().trim());
        return course;
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
}

package viewfx;

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
import service.StudentService;
import service.impl.StudentServiceImpl;

import java.util.List;

/**
 * JavaFX version of StudentManagePanel.
 */
public class StudentManageView extends BorderPane {
    private final StudentService studentService = new StudentServiceImpl();

    private final TextField tfStudentId = new TextField();
    private final TextField tfName = new TextField();
    private final ComboBox<String> cbGender = new ComboBox<>();
    private final TextField tfGrade = new TextField();
    private final TextField tfClassName = new TextField();
    private final TextField tfSearchName = new TextField();

    private final TableView<Student> tableView = new TableView<>();
    private final ObservableList<Student> tableItems = FXCollections.observableArrayList();

    public StudentManageView() {
        initUI();
        loadStudentData();
    }

    private void initUI() {
        setPadding(new Insets(10));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        cbGender.getItems().addAll("man", "woman");
        cbGender.getSelectionModel().selectFirst();

        form.add(new Label("Student ID:"), 0, 0);
        form.add(tfStudentId, 1, 0);
        form.add(new Label("Name:"), 2, 0);
        form.add(tfName, 3, 0);
        form.add(new Label("Gender:"), 4, 0);
        form.add(cbGender, 5, 0);

        form.add(new Label("Grade:"), 0, 1);
        form.add(tfGrade, 1, 1);
        form.add(new Label("Class:"), 2, 1);
        form.add(tfClassName, 3, 1);

        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Modify");
        Button btnDelete = new Button("Delete");
        HBox actionBox = new HBox(10, btnAdd, btnUpdate, btnDelete);
        form.add(actionBox, 4, 1, 2, 1);

        form.add(new Label("Name Search:"), 0, 2);
        form.add(tfSearchName, 1, 2, 2, 1);
        Button btnSearch = new Button("Search");
        Button btnReset = new Button("Reset");
        HBox searchBox = new HBox(10, btnSearch, btnReset);
        form.add(searchBox, 3, 2, 3, 1);

        TableColumn<Student, String> colId = new TableColumn<>("Student ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<Student, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> colGender = new TableColumn<>("Gender");
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Student, String> colGrade = new TableColumn<>("Grade");
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        TableColumn<Student, String> colClass = new TableColumn<>("Class");
        colClass.setCellValueFactory(new PropertyValueFactory<>("className"));

        tableView.getColumns().addAll(colId, colName, colGender, colGrade, colClass);
        tableView.setItems(tableItems);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                tfStudentId.setText(selected.getStudentId());
                tfName.setText(selected.getName());
                cbGender.setValue(selected.getGender());
                tfGrade.setText(selected.getGrade());
                tfClassName.setText(selected.getClassName());
            }
        });

        btnAdd.setOnAction(e -> addStudent());
        btnUpdate.setOnAction(e -> updateStudent());
        btnDelete.setOnAction(e -> deleteStudent());
        btnSearch.setOnAction(e -> searchStudentByName());
        btnReset.setOnAction(e -> resetForm());

        setTop(new VBox(10, form));
        setCenter(tableView);
    }

    private void loadStudentData() {
        List<Student> students = studentService.listAllStudents();
        tableItems.setAll(students);
    }

    private void addStudent() {
        Student student = buildStudentFromForm();
        String result = studentService.addStudent(student);
        showInfo(result);
        if (isSuccess(result)) {
            loadStudentData();
            resetForm();
        }
    }

    private void updateStudent() {
        Student student = buildStudentFromForm();
        String result = studentService.updateStudent(student);
        showInfo(result);
        if (isSuccess(result)) {
            loadStudentData();
            resetForm();
        }
    }

    private void deleteStudent() {
        String studentId = tfStudentId.getText().trim();
        if (studentId.isEmpty()) {
            showWarn("Please select the student to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this student?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirm Deletion");
        confirm.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                String result = studentService.deleteStudent(studentId);
                showInfo(result);
                if (isSuccess(result)) {
                    loadStudentData();
                    resetForm();
                }
            }
        });
    }

    private void searchStudentByName() {
        String name = tfSearchName.getText().trim();
        List<Student> students = studentService.listStudentsByName(name);
        tableItems.setAll(students);
    }

    private void resetForm() {
        tfStudentId.clear();
        tfName.clear();
        cbGender.getSelectionModel().selectFirst();
        tfGrade.clear();
        tfClassName.clear();
        tfSearchName.clear();
        loadStudentData();
    }

    private Student buildStudentFromForm() {
        Student student = new Student();
        student.setStudentId(tfStudentId.getText().trim());
        student.setName(tfName.getText().trim());
        student.setGender(cbGender.getValue());
        student.setGrade(tfGrade.getText().trim());
        student.setClassName(tfClassName.getText().trim());
        return student;
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
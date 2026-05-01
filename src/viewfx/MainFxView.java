package viewfx;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * JavaFX main container: keeps same 3-tab structure as Swing MainFrame.
 */
public class MainFxView extends BorderPane {
    public MainFxView() {
        setPadding(new Insets(10));

        TabPane tabPane = new TabPane();

        Tab studentTab = new Tab("Student Management");
        studentTab.setClosable(false);
        studentTab.setContent(new StudentManageView());

        Tab courseTab = new Tab("Course Management");
        courseTab.setClosable(false);
        courseTab.setContent(new CourseManageView());

        Tab scoreTab = new Tab("Grading Management");
        scoreTab.setClosable(false);
        scoreTab.setContent(new ScoreManageView());

        tabPane.getTabs().addAll(studentTab, courseTab, scoreTab);
        setCenter(tabPane);
    }
}

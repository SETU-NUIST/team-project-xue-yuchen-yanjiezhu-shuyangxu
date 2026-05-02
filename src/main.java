import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewfx.MainFxView;

/**
 * JavaFX entry point. Keep Swing Main.java for gradual migration.
 */
public class FxMainApp extends Application {
    @Override
    public void start(Stage stage) {
        System.out.println("[FX] start() entered");
        try {
            MainFxView root = new MainFxView();
            System.out.println("[FX] MainFxView created");

            Scene scene = new Scene(root, 1000, 700);
            System.out.println("[FX] Scene created");

            stage.setTitle("Student Performance Management System - JavaFX");
            stage.setScene(scene);
            stage.show();
            System.out.println("[FX] Stage shown");
        } catch (Throwable t) {
            System.err.println("[FX] Startup failed: " + t.getMessage());
            t.printStackTrace();
            throw t;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

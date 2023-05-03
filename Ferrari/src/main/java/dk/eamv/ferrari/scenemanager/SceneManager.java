package dk.eamv.ferrari.scenemanager;

import java.lang.Object.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import java.util.Objects;
import dk.eamv.ferrari.sidebar.SidebarController;
import dk.eamv.ferrari.sidebar.SidebarView;

public class SceneManager {
    private static Parent root = new BorderPane();
    private static Stage stage;
    private static Scene scene;

    public static void init(Stage stage) {
        root.getStylesheets().add(Objects.requireNonNull(SceneManager.class.getResource("/style.css")).toExternalForm());

        scene = new Scene(root);

        SceneManager.stage = stage;
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Ferrari");
        stage.getIcons().add(new Image("file:src/main/resources/media/ferrari-emoji.png"));
        stage.show();
    }

    public static void changeScene(Parent root) {
        stage.getScene().setRoot(root);
    }
}

package dk.eamv.ferrari.scenemanager;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.Objects;
import dk.eamv.ferrari.scenes.login.LoginView;

public class SceneManager {
    private static Stage stage;

    public static void init(Stage initialStage) {
        init(stage, LoginView.getScene());
    }

    public static void init(Stage initialStage, Parent root) {
        stage = initialStage;
        changeScene(root);
        stage.setMaximized(true);
        stage.setTitle("Ferrari");
        stage.getIcons().add(new Image("file:src/main/resources/media/ferrari-emoji.png"));
        stage.show();
    }

    public static void changeScene(Parent root) {
        root.getStylesheets().add(Objects.requireNonNull(SceneManager.class.getResource("/style.css")).toExternalForm());
        Scene scene = stage.getScene();
        if (scene == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }
    }
}

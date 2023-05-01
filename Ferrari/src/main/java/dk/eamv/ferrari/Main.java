package dk.eamv.ferrari;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private final BorderPane root = new BorderPane();

    @Override
    public void start (Stage stage){

        init(stage, root);
    }

    private void init(Stage stage, Parent root) {
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        Scene scene = new Scene(root);

        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Ferrari");
        stage.getIcons().add(new Image("file:src/main/resources/media/ferrari-emoji.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
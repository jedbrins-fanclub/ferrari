package dk.eamv.ferrari;

import dk.eamv.ferrari.sidebar.SidebarController;
import dk.eamv.ferrari.sidebar.SidebarView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    private final BorderPane root = new BorderPane();

    @Override
    public void start (Stage stage) {
        //Database.init();

        new SidebarController(SidebarView.getSidebarView());
        root.setLeft(SidebarView.getSidebarView());

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
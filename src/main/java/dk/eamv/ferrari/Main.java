package dk.eamv.ferrari;

import dk.eamv.ferrari.scenes.login.LoginView;
import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.scenemanager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Database.init();
        SceneManager.init(stage, LoginView.getScene());
    }

    public static void main(String[] args) {
        launch();
    }
}
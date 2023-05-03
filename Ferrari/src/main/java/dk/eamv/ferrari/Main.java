package dk.eamv.ferrari;

import dk.eamv.ferrari.login.LoginView;
import dk.eamv.ferrari.scenemanager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        //Database.init();
        SceneManager.init(stage);
        SceneManager.changeScene(LoginView.getScene());
    }

    public static void main(String[] args) {
        launch();
    }
}
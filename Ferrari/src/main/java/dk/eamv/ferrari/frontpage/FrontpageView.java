package dk.eamv.ferrari.frontpage;

import javafx.scene.layout.AnchorPane;

// TODO: Add sidebar
// TODO: Switch to this scene on successful login
public class FrontpageView {
    public static AnchorPane getScene() {
        AnchorPane scene = new AnchorPane();
        scene.setStyle("-fx-background-color: red;");
        return scene;
    }
}

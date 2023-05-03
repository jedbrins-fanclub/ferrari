package dk.eamv.ferrari.frontpage;

import dk.eamv.ferrari.sidebar.SidebarView;
import javafx.scene.layout.BorderPane;

// TODO: Add sidebar
// TODO: Switch to this scene on successful login
public class FrontpageView {
    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();
        scene.setLeft(SidebarView.getSidebarView());
        return scene;
    }
}

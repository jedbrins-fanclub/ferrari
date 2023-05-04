package dk.eamv.ferrari.scenes.frontpage;

import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import javafx.scene.layout.BorderPane;

public class FrontpageView {
    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();
        scene.setLeft(SidebarView.getSidebarView());
        return scene;
    }
}

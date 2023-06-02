package dk.eamv.ferrari.scenes.frontpage;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// Made by Benjamin and Stefan
public class FrontpageView {
    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();
        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.DASHBOARD);
        scene.setCenter(getFrontPageView());
        return scene;
    }

    private static BorderPane getFrontPageView() {
        BorderPane bPane = new BorderPane();
        ImageView imageView = new ImageView(new Image("file:src/main/resources/media/ferrari-logo.png"));
        imageView.setFitHeight(500);
        imageView.setFitWidth(300);
        bPane.setCenter(imageView);

        Label welcome = new Label("Velkommen til Ferrari Herning");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 34));
        VBox wvbox = new VBox();
        wvbox.getChildren().add(welcome);
        bPane.setTop(wvbox);
        wvbox.setAlignment(Pos.CENTER);

        return bPane;
    }

}

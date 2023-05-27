package dk.eamv.ferrari.scenes.frontpage;

import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class FrontpageView {
    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();
        scene.setLeft(SidebarView.getSidebarView());
        scene.setCenter(getFrontPageView());
        return scene;
    }
    
        private static BorderPane getFrontPageView() {
        BorderPane bPane = new BorderPane();
        Image image = new Image("C:/Users/Brozat/Documents/1.ÅrsProject/ferrari/src/main/resources/media/ferrari-logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(300);
        bPane.setCenter(imageView);

        
        Label welcome = new Label("Velkommen til Ferrarie Herning");
        bPane.setBottom(welcome);

        return bPane;
        }
        
        


    }

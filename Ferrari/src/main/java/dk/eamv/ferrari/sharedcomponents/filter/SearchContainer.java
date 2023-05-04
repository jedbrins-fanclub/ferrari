package dk.eamv.ferrari.sharedcomponents.filter;

import dk.eamv.ferrari.resources.SVGResources;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class SearchContainer extends HBox {

    public SearchContainer(TextField textField) {
        SVGPath icon = new SVGPath();
        icon.setContent(SVGResources.getSearchIcon());

        VBox iconContainer = new VBox(icon);
        iconContainer.setAlignment(Pos.CENTER);

        getStyleClass().add("search-container");
        getChildren().addAll(iconContainer, textField);
    }
}

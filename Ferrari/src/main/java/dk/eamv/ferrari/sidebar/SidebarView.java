package dk.eamv.ferrari.sidebar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.*;

public class SidebarView extends VBox {

    private final Button dashboard = new Button("Forside");
    private final Button loans = new Button("Lån");
    private final Button reports = new Button("Rapporter");
    private final Button cars = new Button("Biler");
    private final Button customers = new Button("Kunder");
    private final Button sellers = new Button("Sælgere");
    private final Button settings = new Button("Indstillinger");
    private final Map<Button, String> buttonsWithIcons = new HashMap<>() {{
        put(dashboard, SVGResources.getDashboardIcon());
        put(loans, SVGResources.getLoansIcon());
        put(reports, SVGResources.getReportsIcon());
        put(cars, SVGResources.getCarsIcon());
        put(customers, SVGResources.getCustomersIcon());
        put(sellers, SVGResources.getSellersIcon());
        put(settings, SVGResources.getSettingsIcon());
    }};

    public SidebarView() {
        getStyleClass().add("sidebar");

        configureButtons();

        setMinWidth(200);
        setSpacing(50);
        getChildren().addAll(getHeader(), getButtons());
    }

    private HBox getHeader() {
        HBox header = new HBox();

        ImageView logo = new ImageView(new Image("file:src/main/resources/media/ferrari-logo.png"));
        logo.setFitHeight(70);
        logo.setPreserveRatio(true);

        Label text = new Label("Ferrari");

        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 0, 0, 0));
        header.getChildren().addAll(logo, text);

        return header;
    }

    private void configureButtons() {
        for (Map.Entry<Button, String> entry : buttonsWithIcons.entrySet()) {

            Button button = entry.getKey();

            SVGPath icon = new SVGPath();
            icon.setContent(entry.getValue());
            icon.getStyleClass().add("sidebar-icon");
            //icon.setScaleX(16 / icon.prefWidth(-1));
            //icon.setScaleY(16 / icon.prefHeight(-1));

            button.setGraphic(icon);
            button.setAlignment(Pos.CENTER_LEFT);
            button.getStyleClass().add("sidebar-button");
        }
    }

    private VBox getButtons() {
        VBox buttonsContainer = new VBox();

        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setSpacing(12);
        buttonsContainer.getChildren().addAll(dashboard, loans, reports, cars, customers, sellers, settings);

        return buttonsContainer;
    }
}

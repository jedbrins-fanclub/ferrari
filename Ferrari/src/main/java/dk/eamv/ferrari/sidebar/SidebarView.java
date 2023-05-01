package dk.eamv.ferrari.sidebar;

import dk.eamv.ferrari.resources.SVGResources;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        setMinWidth(250);
        setSpacing(50);
        getChildren().addAll(getHeader(), getButtons());
    }

    private HBox getHeader() {
        HBox header = new HBox();

        ImageView logo = new ImageView(new Image("file:src/main/resources/media/ferrari-logo.png"));
        logo.setFitHeight(64);
        logo.setPreserveRatio(true);

        Label text = new Label("Ferrari");

        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 0, 0, 0));
        header.setSpacing(10);
        header.getChildren().addAll(logo, text);

        return header;
    }

    private void configureButtons() {
        for (Map.Entry<Button, String> entry : buttonsWithIcons.entrySet()) {

            Button button = entry.getKey();

            SVGPath icon = new SVGPath();
            icon.setContent(entry.getValue());
            icon.getStyleClass().add("sidebar-icon");

            HBox iconContainer = new HBox(icon);
            iconContainer.setPadding(new Insets(0, 6, 0, 0)); // set padding between icon and text

            button.setGraphic(iconContainer);
            button.setAlignment(Pos.CENTER_LEFT);
            button.getStyleClass().add("sidebar-button");
        }
    }

    private VBox getButtons() {
        VBox buttonsContainer = new VBox();

        VBox buttonGroupOne = new VBox();
        buttonGroupOne.getChildren().addAll(dashboard, loans, reports);
        buttonGroupOne.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupOne.setSpacing(12);

        VBox buttonGroupTwo = new VBox();
        buttonGroupTwo.getChildren().addAll(cars, customers, sellers);
        buttonGroupTwo.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupTwo.setSpacing(16);

        VBox buttonGroupThree = new VBox(settings);
        buttonGroupThree.setAlignment(Pos.CENTER_RIGHT);

        //buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.setSpacing(50);
        buttonsContainer.getChildren().addAll(buttonGroupOne, buttonGroupTwo, buttonGroupThree);

        return buttonsContainer;
    }
}

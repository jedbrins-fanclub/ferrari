package dk.eamv.ferrari.sidebar;

import dk.eamv.ferrari.resources.SVGResources;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import java.util.*;

public class SidebarView extends VBox {

    protected final ToggleButton dashboard = new ToggleButton("Forside");
    protected final ToggleButton loans = new ToggleButton("Lån");
    protected final ToggleButton reports = new ToggleButton("Rapporter");
    protected final ToggleButton cars = new ToggleButton("Biler");
    protected final ToggleButton customers = new ToggleButton("Kunder");
    protected final ToggleButton sellers = new ToggleButton("Sælgere");
    protected final ToggleButton settings = new ToggleButton("Indstillinger");
    private final Map<ToggleButton, String> buttonsWithIcons = new HashMap<>() {{
        put(dashboard, SVGResources.getDashboardIcon());
        put(loans, SVGResources.getLoansIcon());
        put(reports, SVGResources.getReportsIcon());
        put(cars, SVGResources.getCarsIcon());
        put(customers, SVGResources.getCustomersIcon());
        put(sellers, SVGResources.getSellersIcon());
        put(settings, SVGResources.getSettingsIcon());
    }};

    public SidebarView() {

        configureButtons();

        setMinWidth(250);
        setSpacing(50);
        getStyleClass().add("sidebar");

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
        ToggleGroup toggleGroup = new ToggleGroup();

        for (Map.Entry<ToggleButton, String> entry : buttonsWithIcons.entrySet()) {

            ToggleButton button = entry.getKey();

            SVGPath icon = new SVGPath();
            icon.setContent(entry.getValue()); // set content as the related svg resource
            icon.getStyleClass().add("sidebar-icon");

            HBox iconContainer = new HBox(icon);
            iconContainer.setPadding(new Insets(0, 6, 0, 0)); // set padding between icon and text

            button.setGraphic(iconContainer);
            button.setAlignment(Pos.CENTER_LEFT);
            button.getStyleClass().add("sidebar-togglebutton");
            button.setToggleGroup(toggleGroup);
        }
    }

    private VBox getButtons() {
        VBox buttonsContainer = new VBox();

        VBox buttonGroupOne = new VBox();
        buttonGroupOne.getChildren().addAll(dashboard, loans, reports);
        buttonGroupOne.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupOne.setSpacing(16);

        VBox buttonGroupTwo = new VBox();
        buttonGroupTwo.getChildren().addAll(cars, customers, sellers);
        buttonGroupTwo.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupTwo.setSpacing(16);

        VBox buttonGroupThree = new VBox(settings);
        buttonGroupThree.setAlignment(Pos.CENTER_RIGHT);

        buttonsContainer.setSpacing(50); // buttons are grouped visually as they best relate

        buttonsContainer.getChildren().addAll(buttonGroupOne, buttonGroupTwo, buttonGroupThree);

        return buttonsContainer;
    }
}

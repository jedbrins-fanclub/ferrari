package dk.eamv.ferrari.sidebar;

import java.util.EnumMap;
import java.util.Map;

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

public class SidebarView extends VBox {
    private static final SidebarView sidebarView = new SidebarView();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Map<SidebarButton, String> icons = new EnumMap<>(SidebarButton.class);
    protected final Map<SidebarButton, ToggleButton> buttons = new EnumMap<>(SidebarButton.class);

    public SidebarView() {
        setButtonMap();
        configureButtons();

        setMinWidth(250);
        setSpacing(50);
        getStyleClass().add("sidebar");

        getChildren().addAll(getHeader(), getButtons());
    }

    private void setButtonMap() {
        for (SidebarButton button : SidebarButton.values()) {
            ToggleButton toggleButton = new ToggleButton(button.getLabel());
            buttons.put(button, toggleButton);
            icons.put(button, button.getIcon());
        }
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

        for (Map.Entry<SidebarButton, String> entry : icons.entrySet()) {

            ToggleButton button = buttons.get(entry.getKey());

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
        buttonGroupOne.getChildren().addAll(
                buttons.get(SidebarButton.DASHBOARD),
                buttons.get(SidebarButton.LOANS),
                buttons.get(SidebarButton.REPORTS));
        buttonGroupOne.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupOne.setSpacing(16);

        VBox buttonGroupTwo = new VBox();
        buttonGroupTwo.getChildren().addAll(
                buttons.get(SidebarButton.CARS),
                buttons.get(SidebarButton.CUSTOMERS),
                buttons.get(SidebarButton.SELLERS));
        buttonGroupTwo.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupTwo.setSpacing(16);

        VBox buttonGroupThree = new VBox(
                buttons.get(SidebarButton.SETTINGS));
        buttonGroupThree.setAlignment(Pos.CENTER_RIGHT);

        buttonsContainer.setSpacing(50); // buttons are grouped visually as they best relate

        buttonsContainer.getChildren().addAll(buttonGroupOne, buttonGroupTwo, buttonGroupThree);

        return buttonsContainer;
    }

    public void setActiveToggleButton(SidebarButton button) {
        toggleGroup.selectToggle(buttons.get(button));
    }

    public static SidebarView getSidebarView() {
        return sidebarView;
    }
}

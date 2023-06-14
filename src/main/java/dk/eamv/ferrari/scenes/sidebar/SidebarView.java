package dk.eamv.ferrari.scenes.sidebar;

import java.util.EnumMap;

import dk.eamv.ferrari.managers.SessionManager;
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

// Made by: Mikkel

/**
 * This class represents the SidebarView of the application.
 * It serves as a container for the sidebar elements.
 */
public class SidebarView extends VBox {
    private static final SidebarView sidebarView = new SidebarView();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final EnumMap<SidebarButton, String> icons = new EnumMap<>(SidebarButton.class);
    protected final EnumMap<SidebarButton, ToggleButton> buttons = new EnumMap<>(SidebarButton.class);

    public SidebarView() {}

    /**
     * Updates the SidebarView.
     * Configures buttons, attaches listeners and updates layout.
     */
    public static void update() {
        sidebarView.setButtonMap();
        sidebarView.configureButtons();
        sidebarView.attachToggleButtonListeners();

        sidebarView.setMinWidth(250);
        sidebarView.setSpacing(50);
        sidebarView.getStyleClass().add("sidebar");
        sidebarView.getChildren().clear();
        sidebarView.getChildren().addAll(sidebarView.getHeader(), sidebarView.getButtons());
    }

    /**
     * Sets the button map.
     * Populates the buttons and icons maps with the SidebarButtons and their corresponding labels and icons.
     */
    private void setButtonMap() {
        buttons.clear();
        for (SidebarButton button : SidebarButton.values()) {
            ToggleButton toggleButton = new ToggleButton(button.getLabel());
            buttons.put(button, toggleButton);
            icons.put(button, button.getIcon());
        }
    }

    /**
     * Attaches the appropriate listeners to the ToggleButtons in the SidebarView.
     */
    protected void attachToggleButtonListeners() {
        buttons.get(SidebarButton.DASHBOARD).setOnAction(SidebarController::onDashboardButtonClick);
        buttons.get(SidebarButton.LOANS).setOnAction(SidebarController::onLoansButtonClick);
        buttons.get(SidebarButton.REPORTS).setOnAction(SidebarController::onReportsButtonClick);
        buttons.get(SidebarButton.CARS).setOnAction(SidebarController::onCarsButtonClick);
        buttons.get(SidebarButton.CUSTOMERS).setOnAction(SidebarController::onCustomersButtonClick);
        buttons.get(SidebarButton.SELLERS).setOnAction(SidebarController::onSellersButtonClick);
        buttons.get(SidebarButton.SETTINGS).setOnAction(SidebarController::onSettingsButtonClick);
        buttons.get(SidebarButton.LOGOUT).setOnAction(SidebarController::onLogOutButtonClick);
    }

    /**
     * Creates and returns the header for the SidebarView.
     * The header includes a logo and a title.
     * @return HBox - the header of the SidebarView
     */
    private HBox getHeader() {
        ImageView logo = new ImageView(new Image("file:src/main/resources/media/ferrari-logo.png"));
        logo.setFitHeight(64);
        logo.setPreserveRatio(true);

        Label text = new Label("Ferrari");

        HBox header = new HBox(logo, text);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 0, 0, 0));
        header.setSpacing(10);

        return header;
    }

    /**
     * Configures the ToggleButtons in the SidebarView.
     * Sets their icon, alignment, style class, and toggle group.
     */
    private void configureButtons() {
        for (EnumMap.Entry<SidebarButton, String> entry : icons.entrySet()) {
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

    /**
     * Creates and returns a VBox containing the buttons for the SidebarView.
     * Organizes the buttons into groups and configures their layout.
     * @return VBox - a container with the SidebarView's buttons
     */
    private VBox getButtons() {
        VBox buttonGroupOne = new VBox(
            buttons.get(SidebarButton.DASHBOARD),
            buttons.get(SidebarButton.LOANS),
            buttons.get(SidebarButton.REPORTS)
        );
        buttonGroupOne.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupOne.setSpacing(16);

        VBox buttonGroupTwo = new VBox(
            buttons.get(SidebarButton.CARS),
            buttons.get(SidebarButton.CUSTOMERS),
            // Add employee button if the sales manager is logged in
            SessionManager.getUser().isSalesManager() ? buttons.get(SidebarButton.SELLERS) : null
        );
        buttonGroupTwo.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupTwo.setSpacing(16);

        VBox buttonGroupThree = new VBox(
            buttons.get(SidebarButton.SETTINGS),
            buttons.get(SidebarButton.LOGOUT)
        );
        buttonGroupThree.setAlignment(Pos.CENTER_RIGHT);
        buttonGroupThree.setSpacing(16);

        VBox buttonsContainer = new VBox(buttonGroupOne, buttonGroupTwo, buttonGroupThree);
        buttonsContainer.setSpacing(50); // Buttons are grouped visually as they best relate

        return buttonsContainer;
    }

    /**
     * Sets the active toggle button in the SidebarView.
     * @param button - the SidebarButton to be set active
     */
    public void setActiveToggleButton(SidebarButton button) {
        toggleGroup.selectToggle(buttons.get(button));
    }

    public static SidebarView getSidebarView() {
        update();
        return sidebarView;
    }
}

package dk.eamv.ferrari.scenes.settings;
import dk.eamv.ferrari.scenemanager.SceneManager;
import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Made by: Benjamin og Stefan
 * Checked by:
 * Modified by:
 */
public class SettingsView {

    public static BorderPane getScene(){
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.SETTINGS);

        scene.setCenter(getSettingsView());

        return scene;
    }

    private static StackPane getSettingsView() {
        Label rettelse = new Label("Indtast en ny adgangskode!");
        rettelse.setVisible(false);

        Label indstilling = new Label("Indstillinger");
        Label rediger = new Label("Rediger oplysninger");

        Label email = new Label("email");
        TextField emailInput = new TextField();

        Label tlf = new Label("Telefonnummer");
        TextField tlfInput = new TextField();

        Label updateKode = new Label("Opdater adgangskode");

        Label glKode = new Label("Nuværende adgangskode");
        PasswordField glKodeInput = new PasswordField();

        Label nyKode = new Label("Ny adgangskode");
        PasswordField nyKodeInput = new PasswordField();

        Label bekræftKode = new Label("Gentag kode");
        PasswordField bekræftInput = new  PasswordField();

        Button update = new Button("Opdater oplysninger");
        update.setOnAction((event) -> {
            if(bekræftInput.getText().equals(nyKodeInput.getText())){

                System.out.println("Koden er ok");
            } else {
                System.out.println("Koden er ikke ok");
            }

            if(glKodeInput.getText().equals(nyKodeInput.getText())){

                rettelse.setVisible(true);
            } else {
                System.out.println("koden ok");
            }

        });


        VBox vbox  = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                indstilling, rediger, email, emailInput,
                tlf, tlfInput, updateKode, rettelse,
                glKode, glKodeInput, nyKode, nyKodeInput,
                bekræftKode, bekræftInput, update

        );
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setPadding(new Insets(25));
        vbox.setSpacing(25);
        vbox.getStyleClass().add("table-view-container");
        vbox.getStyleClass().add("settings");

        // Apply drop shadow to parentContainer to avoid applying it to VBox children
        StackPane parentContainer = new StackPane(vbox);
        parentContainer.getStyleClass().add("drop-shadow-effect");


        StackPane window = new StackPane(parentContainer);
        window.setPadding(new Insets(50));
        window.setStyle("-fx-background-color: lightgrey");

        return window;
    }
}

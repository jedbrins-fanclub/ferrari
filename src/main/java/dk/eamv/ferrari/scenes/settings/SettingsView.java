package dk.eamv.ferrari.scenes.settings;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sessionmanager.SessionManager;
import dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Made by: Benjamin og Stefan
 * Checked by:
 * Modified by: Mikkel og Stefan
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
        NumericTextField tlfInput = new NumericTextField();

        VBox tlfvbox = new VBox();
        tlfvbox.setAlignment(Pos.CENTER);
        tlfvbox.getChildren().addAll(
        email, emailInput, 
        tlf, tlfInput
        );
        tlfvbox.setSpacing(-2);

        
        Label updateKode = new Label("Opdater adgangskode");

        Label glKode = new Label("Nuværende adgangskode");
        PasswordField glKodeInput = new PasswordField();

        Label nyKode = new Label("Ny adgangskode");
        PasswordField nyKodeInput = new PasswordField();

        Label bekræftKode = new Label("Gentag kode");
        PasswordField bekræftInput = new  PasswordField();

        VBox kodevbox = new VBox();
        kodevbox.setAlignment(Pos.CENTER);
        kodevbox.getChildren().addAll(
            glKode, glKodeInput, nyKode,
            nyKodeInput, bekræftKode,
            bekræftInput
        );
            kodevbox.setSpacing(-2);

        Button update = new Button("Opdater oplysninger");
        update.setOnAction((event) -> {
        
            if(!glKodeInput.getText().equals(SessionManager.getUser().getPassword())){

                rettelse.setText("Koden er IKKE ok");
                rettelse.setVisible(true);
                return;
            }
        
                if(nyKodeInput.getText().equals(bekræftInput.getText())){

                SessionManager.getUser().setPassword(bekræftInput.getText());
                rettelse.setVisible(false);
                EmployeeModel.update(SessionManager.getUser());
                rettelse.setText("Koden er nu ændret");
                rettelse.setVisible(true);

            } else {
                rettelse.setVisible(true);
                rettelse.setText("Forkert kode prøv igen");
            }
        

            
        });


        VBox vbox  = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                indstilling, rediger, tlfvbox,
                rettelse,
                kodevbox, update

        );
    
        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setPadding(new Insets(25));
        vbox.setSpacing(15);
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

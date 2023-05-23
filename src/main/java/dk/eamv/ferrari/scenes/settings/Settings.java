package dk.eamv.ferrari.scenes.settings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Settings{

    
    public static VBox getScene(){ 
    
        Label indstilling = new Label("Indstillinger");
        Label rediger = new Label("Rediger oplysninger");
        
        Label email = new Label("email");
        TextField emailInput = new TextField();
        
        Label tlf = new Label("Telefonnummer");
        TextField tlfInput = new TextField();
        
        Label updateKode = new Label("Opdater adgangskode");
        PasswordField updateKodeInput = new PasswordField();
        
        Label glKode = new Label("Nuværende adgangskode");
        PasswordField glKodeInput = new PasswordField();
        
        Label nyKode = new Label("Ny adgangskode");
        PasswordField nyKodeInput = new PasswordField();
        
        Label bekræftKode = new Label("Gentag kode");
        PasswordField bekræftInput = new  PasswordField();
        
        Button update = new Button("Opdater oplysninger");
        update.setOnAction((event) -> {
                System.out.println(bekræftInput.getText());
        });

        VBox vbox  = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
            indstilling, rediger, email, emailInput,
            tlf, tlfInput, updateKode, updateKodeInput,
            glKode, glKodeInput, nyKode, nyKodeInput,
            bekræftKode, bekræftInput, update
        );

    
        return vbox;
        





    }
}

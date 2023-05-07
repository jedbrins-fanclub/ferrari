package dk.eamv.ferrari;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormView extends GridPane {

    public FormView(){

        
       setAlignment(Pos.CENTER);
        setVgap(10);
        setHgap(20);
        setGridLinesVisible(false);
       
        //fName label
        Label fTitel = new Label("Oprettelsesformular til kunder");
        add(fTitel, 1, 0);
        
        //fName label
    //    Label fName = new Label("Fornavn");
    //    add(fName, 0, 1);

        //fName input
        TextField fNameInput = new TextField();
        fNameInput.setPromptText("Fornavn");
        add(fNameInput, 0, 2);

        //eName label
    //    Label eName = new Label("Efternavn");
    //    add(eName, 1, 1);

        //eName input
        TextField eNameInput = new TextField();
        eNameInput.setPromptText("Efternavn");
        add(eNameInput, 1, 2);

        //bDay label
    //    Label bDay = new Label("Fødselsdag");
    //    add(bDay, 2, 1);

        //bDay input
        TextField bDayInput = new TextField();
        bDayInput.setPromptText("Fødselsdag");
        add(bDayInput, 2, 2);
        
        //Cpr
    //    Label cpr = new Label("CPR-nummer");
    //    add(cpr, 0, 3);

        //CprIndput
        TextField cprInput = new TextField();
        cprInput.setPromptText("CPR-nummer");
        add(cprInput, 0, 4);

        //post
    //    Label postNr = new Label("Postnummer");
    //    add(postNr, 1, 3);

        //PostInput
        TextField postInput = new TextField();
        postInput.setPromptText("Postnummer");
        add(postInput, 1, 4);

        //By
    //    Label by = new Label("By");
    //    add(by, 2, 3);

        //ByIndput
        TextField byInput = new TextField();
        byInput.setPromptText("By");
        add(byInput, 2, 4); 
        
        //Vej
    //    Label vejNavn = new Label("Vejnavn");
    //    add(vejNavn, 0, 5);

        //VejIndput
        TextField vejInput = new TextField();
        vejInput.setPromptText("Vejnavn");
        add(vejInput, 0, 6); 

        //Email
    //    Label email = new Label("Email");
    //    add(email, 1, 5);

        //EmailInput
        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");
        add(emailInput, 1, 6); 

        //Telefonnummer
    //    Label tlf = new Label("Telefonnummer");
    //    add(tlf, 2, 5);

        //TlfIndput
        TextField tlfInput = new TextField();
        tlfInput.setPromptText("Telefonnummer");
        add(tlfInput, 2, 6); 
    }
    
    VBox vbox = new VBox();
    
    Button okButton = new Button();
    Button noButton = new Button();

   /*  vbox.getChildren().addAll(okButton, noButton);
*/
    
}

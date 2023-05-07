package dk.eamv.ferrari;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private static int[] createFieldsString(GridPane gridPane, int column, int row, String... input) {
        for (String i : input) {
            VBox vBox = new VBox();
            Label heading = new Label(i);
            TextField textField = new TextField();
            textField.setPromptText(i);
            vBox.getChildren().addAll(heading, textField);
            if (column > 2) {
                column = 0;
                row++;
            }
            gridPane.add(vBox, column, row);
            column++;
        }
        return new int[] { column, row };
    }

    private static int[] createFieldsInt(GridPane gridPane, int column, int row, String... input) {
        for (String i : input) {
            VBox vBox = new VBox();
            Label heading = new Label(i);
            NumericTextField textField = new NumericTextField();
            textField.setPromptText(i);
            vBox.getChildren().addAll(heading, textField);
            if (column > 2) {
                column = 0;
                row++;
            }
            gridPane.add(vBox, column, row);
            column++;
        }
        
        return new int[] { column, row };
    }

    public static GridPane createCustomerForm() {
        GridPane customerForm = createGridPane();
        int[] fields = createFieldsString(customerForm, 0, 0, "Fornavn", "Efternavn", "Email", "Adresse");
        createFieldsInt(customerForm, fields[0], fields[1], "Telefonnummer", "CPR");
        return customerForm;
    }
    
    public static GridPane createCarForm() {
        GridPane carForm = createGridPane();
        int[] fields = createFieldsInt(carForm, 0, 0, "Årgang", "Pris", "Stelnummer");
        createFieldsString(carForm, fields[0], fields[1], "Model");
        return carForm;
    }
    
    public static GridPane createLoanForm() {
        GridPane loanForm = createGridPane();
        createFieldsInt(loanForm, 0, 0, "Stelnummer", "Kunde CPR", "Lånets størrelse", "Udbetaling", "Rente", "Start dato", "Forfaldsdag");
        return loanForm;
    }
    
    private static GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(25);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }
}

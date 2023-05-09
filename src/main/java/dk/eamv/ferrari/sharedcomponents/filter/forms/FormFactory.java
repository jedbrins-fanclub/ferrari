package dk.eamv.ferrari.sharedcomponents.filter.forms;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private static int[] createFieldsString(GridPane gridPane, ArrayList<TextField> fieldsList, int column, int row, String... input) {
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
            fieldsList.add(textField);
            column++;
        }
        return new int[] { column, row };
    }

    private static int[] createFieldsInt(GridPane gridPane, ArrayList<TextField> fieldsList, int column, int row, String... input) {
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
            fieldsList.add(textField);
            column++;
        }

        return new int[] { column, row };
    }
    
    private static GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(25);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    private static Form createCustomerForm() {
        Form customerForm = new Form(createGridPane(), new ArrayList<TextField>());
        int[] fields = createFieldsString(customerForm.getGridPane(), customerForm.getFieldsList(), 0, 0, "Fornavn", "Efternavn", "Email", "Adresse");
        createFieldsInt(customerForm.getGridPane(), customerForm.getFieldsList(), fields[0], fields[1], "Telefonnummer", "CPR");
        return customerForm;
    }

    private static Form createCarForm() {
        Form carForm = new Form(createGridPane(), new ArrayList<TextField>());
        int[] fields = createFieldsInt(carForm.getGridPane(), carForm.getFieldsList(), 0, 0, "Årgang", "Pris", "Stelnummer");
        createFieldsString(carForm.getGridPane(), carForm.getFieldsList(), fields[0], fields[1], "Model");
        return carForm;
    }
    
    private static Form createLoanForm() {
        Form loanForm = new Form(createGridPane(), new ArrayList<TextField>());
        createFieldsInt(loanForm.getGridPane(), loanForm.getFieldsList(),  0, 0, "Stelnummer", "Kunde CPR", "Lånets størrelse", "Udbetaling", "Rente",
                "Start dato", "Forfaldsdag");
        return loanForm;
    }

    private static Dialog wrap(Form form) {
        Dialog dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(form.getGridPane());
        dialog.setResizable(true);
        return dialog;
    }

    public static void createCustomerFormDialogBox() {
        Dialog dialog = wrap(createCustomerForm());
        dialog.setTitle("Opret kunde");
        dialog.show();
    }

    public static void createCarFormDialogBox() {
        Dialog dialog = wrap(createCarForm());
        dialog.setTitle("Opret bil");
        dialog.show();
    }

    public static void createLoanFormDialogBox() {
        Dialog dialog = wrap(createLoanForm());
        dialog.setTitle("Opret lån");
        dialog.show();
    }
}

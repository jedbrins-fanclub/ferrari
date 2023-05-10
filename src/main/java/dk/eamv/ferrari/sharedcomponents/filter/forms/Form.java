package dk.eamv.ferrari.sharedcomponents.filter.forms;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/*
 * Lavet af: Christian
 * Wrapper class forms, linking a GridPane to an ArrayList of its fields.
 * Made so that we can iterate over the list of fields to check if theres content, when "OK" button is clicked.
 */
public class Form {
    private GridPane gridPane;
    private ArrayList<TextField> fieldsList;
    private int column;
    private int row;

    private Form() {
        gridPane = new GridPane();
        fieldsList = new ArrayList<TextField>();
        column = 0;
        row = 0;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ArrayList<TextField> getFieldsList() {
        return fieldsList;
    }

    public boolean hasFilledFields() {
        for (TextField i : fieldsList) {
            if (i.getText().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private void setColumn(int value) {
        column = value;
    }

    private void setRow(int value) {
        row = value;
    }

    private int getColumn() {
        return column;
    }

    private int getRow() {
        return row;
    }

    public static class Builder {
        private Form form; 

        public Builder() {
            form = new Form();
        }

        protected Builder withFieldsString(GridPane gridPane, ArrayList<TextField> fieldsList, int column, int row, String... input) {
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

            return this;
        }

        protected Builder withFieldsInt(GridPane gridPane, ArrayList<TextField> fieldsList, int column, int row,
                String... input) {
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

            return this;
        }

        protected Builder withFieldsUneditable(GridPane gridPane, ArrayList<TextField> fieldsList, int column,
                int row,
                String... input) {
            for (String i : input) {
                VBox vBox = new VBox();
                Label heading = new Label(i);
                TextField textField = new TextField();
                textField.setDisable(true);
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

            return this;
        }

        private static GridPane createGridPane() {
            GridPane gridPane = new GridPane();
            gridPane.setVgap(25);
            gridPane.setHgap(50);
            gridPane.setAlignment(Pos.CENTER);

            return gridPane;
        }

        private static Form createCustomerForm() {
            Form customerForm = new Form.Builder()
                    .withFieldsInt(null, null, 0, 0, null);
            int[] fields = createFieldsString(customerForm.getGridPane(), customerForm.getFieldsList(), 0, 0, "Fornavn",
                    "Efternavn", "Email", "Adresse");
            createFieldsInt(customerForm.getGridPane(), customerForm.getFieldsList(), fields[0], fields[1],
                    "Telefonnummer", "CPR");
            return customerForm;
        }

        private static Form createCarForm() {
            Form carForm = new Form(createGridPane(), new ArrayList<TextField>());
            int[] fields = createFieldsInt(carForm.getGridPane(), carForm.getFieldsList(), 0, 0, "Årgang", "Pris",
                    "Stelnummer");
            createFieldsString(carForm.getGridPane(), carForm.getFieldsList(), fields[0], fields[1], "Model");
            return carForm;
        }

        private static Form createLoanForm() {
            Form loanForm = new Form(createGridPane(), new ArrayList<TextField>());
            createFieldsInt(loanForm.getGridPane(), loanForm.getFieldsList(), 0, 0, "Stelnummer", "Kunde CPR",
                    "Lånets størrelse", "Udbetaling", "Rente",
                    "Start dato", "Forfaldsdag");
            return loanForm;
        }
    }
}
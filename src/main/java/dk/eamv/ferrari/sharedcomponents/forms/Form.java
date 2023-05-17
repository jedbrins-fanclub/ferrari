package dk.eamv.ferrari.sharedcomponents.forms;

import java.util.ArrayList;

import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField;
import javafx.collections.ObservableList;
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
    private ArrayList<AutoCompleteComboBox<?>> boxList;
    private int column;
    private int row;

    private Form() {
        gridPane = createGridPane();
        fieldsList = new ArrayList<TextField>();
        boxList = new ArrayList<AutoCompleteComboBox<?>>();
        column = 0;
        row = 0;
    }

    private static GridPane createGridPane() {
            GridPane gridPane = new GridPane();
            gridPane.setVgap(25);
            gridPane.setHgap(50);
            gridPane.setAlignment(Pos.CENTER);

            return gridPane;
        }

    protected GridPane getGridPane() {
        return gridPane;
    }

    protected ArrayList<TextField> getFieldsList() {
        return fieldsList;
    }

    protected ArrayList<AutoCompleteComboBox<?>> getBoxlist() {
        return boxList;
    }

    protected boolean verifyFilledFields() {
        String redStyle = """
            -fx-prompt-text-fill: F50000;
            -fx-background-color: #f7adb1;
            -fx-border-color: F50000;
        """;
        
        boolean fieldsAreValid = true;
        for (TextField widget : fieldsList) {
            if (widget.getText().isEmpty()) {
                widget.setStyle(redStyle);
                fieldsAreValid = false;
            } else {
                widget.setStyle(null);
            }
        }

        for (AutoCompleteComboBox<?> widget : boxList) {
            if (widget.getSelectionModel().getSelectedItem() == null) {
                widget.setStyle(redStyle);
                fieldsAreValid = false;
            } else {
                widget.setStyle(null);
            }
        }

        return fieldsAreValid;
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

        private Builder withFieldsString(String... input) {
            int row = form.getRow();
            int column = form.getColumn();
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
                this.form.getGridPane().add(vBox, column, row);
                this.form.getFieldsList().add(textField);
                column++;
            }

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        private Builder withFieldsInt(String... input) {
            int row = form.getRow();
            int column = form.getColumn();
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
                this.form.getGridPane().add(vBox, column, row);
                this.form.getFieldsList().add(textField);
                column++;
            }

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        private Builder withFieldsUneditable(String... input) {
            int row = form.getRow();
            int column = form.getColumn();
            for (String i : input) {
                VBox vBox = new VBox();
                Label heading = new Label(i);
                TextField textField = new TextField();
                textField.setDisable(true);
                vBox.getChildren().addAll(heading, textField);
                if (column > 2) {
                    column = 0;
                    row++;
                }
                this.form.getGridPane().add(vBox, column, row);
                this.form.getFieldsList().add(textField);
                column++;
            }

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        private <E> Builder withDropDownBox(ObservableList<E> content, String input) {
            int row = form.getRow();
            int column = form.getColumn();
            VBox vBox = new VBox();
            Label heading = new Label(input);
            AutoCompleteComboBox<E> dropDown = new AutoCompleteComboBox<E>(content);
            vBox.getChildren().addAll(heading, dropDown);
            if (column > 2) {
                column = 0;
                row++;
            }
            this.form.getGridPane().add(vBox, column, row);
            this.form.boxList.add(dropDown);
            column++;

            form.setColumn(column);
            form.setRow(row);
            return this;
        }


        
        private Form build() {
            return form;
        }

        protected Form buildCustomerForm() {
            form = new Form.Builder()
                .withFieldsString("Fornavn", "Efternavn")
                .withFieldsInt("Telefonnummer")
                .withFieldsString("Email", "Adresse")
                .withFieldsInt("CPR")
                .build();
            return form;
        }

        protected Form buildCarForm() {
            form = new Form.Builder()
                .withFieldsInt("Årgang", "Pris")
                .withFieldsString("Model")
                .build();
            return form;
        }

        protected Form buildLoanForm() {
            form = new Form.Builder()
                    .withDropDownBox(CarController.getCars(), "Bil")
                    .withDropDownBox(CustomerController.getCustomers(), "CPR & Kunde")
                    .withDropDownBox(EmployeeController.getEmployees(), "Medarbejder")
                    .withFieldsUneditable("Model", "Fornavn", "Fornavn")
                    .withFieldsUneditable("Årgang", "Efternavn", "Efternavn")
                    .withFieldsUneditable("Pris", "CPR", "ID")
                    .withFieldsUneditable("Stelnummer", "Telefon nr.", "Telefon nr.")
                    .withFieldsUneditable("Kundens Adresse", "Email", "Email")
                    .withFieldsInt("Lånets størrelse", "Udbetaling")
                    .withFieldsInt("Rente", "Start dato", "Forfaldsdag")
                    .build();
            return form;
        }
    }
}
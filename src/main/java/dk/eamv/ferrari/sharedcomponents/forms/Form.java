package dk.eamv.ferrari.sharedcomponents.forms;

import java.time.LocalDate;
import java.util.ArrayList;

import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * Lavet af: Christian
 * Wrapper class forms, linking a GridPane to an ArrayList of its fields.
 * Made so that we can iterate over the list of fields to check if theres content, when "OK" button is clicked.
 */
public class Form {
    private GridPane gridPane;
    private ArrayList<Control> fieldsList;
    private int column;
    private int row;

    private Form() {
        gridPane = createGridPane();
        fieldsList = new ArrayList<Control>();
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

    protected ArrayList<Control> getFieldsList() {
        return fieldsList;
    }

    protected boolean verifyHasFilledFields() {
        String redStyle = """
            -fx-prompt-text-fill: F50000;
            -fx-background-color: #f7adb1;
            -fx-border-color: F50000;
        """;
        
        boolean hasFilledFields = true;
        for (Control widget : fieldsList) {
            if (widget instanceof TextField) {
                hasFilledFields = !((TextField) widget).getText().isEmpty();
            } else if (widget instanceof ComboBox) {
                hasFilledFields = !((AutoCompleteComboBox) widget).isEmpty();
            } else if (widget instanceof DatePicker) {
                DatePicker dp = ((DatePicker) widget);
                hasFilledFields = !(((DatePicker) widget).getValue() == null);
                System.out.println(dp.getValue());
                System.out.println(dp.getValue() == null);
            }

            if (!hasFilledFields) {
                widget.setStyle(redStyle);
                hasFilledFields = false;
            } else {
                widget.setStyle(null);
            }
        }
        return hasFilledFields;
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

        private Builder addFieldToForm(String labelText, Control control) {
            int row = form.getRow();
            int column = form.getColumn();

            VBox vBox = new VBox();
            Label heading = new Label(labelText);
            vBox.getChildren().addAll(heading, control);
            if (column > 2) {
                column = 0;
                row++;
            }
            form.getGridPane().add(vBox, column, row);
            form.getFieldsList().add(control);
            column++;

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        private Builder withFieldsString(String... input) {
            for (String i : input) {
                TextField textField = new TextField(i);
                addFieldToForm(i, textField);
            }

            return this;
        }

        private Builder withFieldsInt(String... input) {
            for (String i : input) {
                NumericTextField numberField = new NumericTextField();
                numberField.setPromptText(i);
                addFieldToForm(i, numberField);
            }
            
            return this;
        }

        private Builder withFieldsUneditable(String... input) {
            for (String i : input) {
                TextField textField = new TextField();
                textField.setDisable(true);
                addFieldToForm(i, textField);
            }

            return this;
        }

        private <E> Builder withDropDownBox(ObservableList<E> content, String input) {
            AutoCompleteComboBox dropDown = new AutoCompleteComboBox<>(content);
            addFieldToForm(input, dropDown);
        
            return this;
        }

        private Builder withFieldsDatePicker(Form form) {
            DatePicker startDatePicker = new DatePicker();
            DatePicker endDatePicker = new DatePicker();

            addFieldToForm("Start dato DD/MM/ÅÅÅÅ", startDatePicker);
            addFieldToForm("Slut dato DD/MM/ÅÅÅÅ", endDatePicker);

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
                .withFieldsInt("Lånets størrelse", "Udbetaling", "Rente")
                .withFieldsDatePicker(form)
                .build();
            return form;
        }
    }
}
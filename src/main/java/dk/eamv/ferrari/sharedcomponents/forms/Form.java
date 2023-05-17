package dk.eamv.ferrari.sharedcomponents.forms;

import java.util.ArrayList;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.customer.CustomerController;
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

        protected Builder withFieldsString(Form form, int column, int row, String... input) {
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

        protected Builder withFieldsInt(Form form, int column, int row, String... input) {
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

        protected Builder withFieldsUneditable(Form form, int column, int row, String... input) {
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
                this.form.getGridPane().add(vBox, column, row);
                this.form.getFieldsList().add(textField);
                column++;
            }

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        protected <E> Builder withDropDownBoxes(Form form, ObservableList<E> content, int column, int row, String... input) {
            for (String i : input) {
                VBox vBox = new VBox();
                Label heading = new Label(i);
                AutoCompleteComboBox<E> dropDown = new AutoCompleteComboBox<E>(content);
                vBox.getChildren().addAll(heading, dropDown);
                if (column > 2) {
                    column = 0;
                    row++;
                }
                this.form.getGridPane().add(vBox, column, row);
                this.form.boxList.add(dropDown);
                column++;
            }

            form.setColumn(column);
            form.setRow(row);
            return this;
        }
        
        protected Form build() {
            return form;
        }

        protected Form buildCustomerForm() {
            form = new Form.Builder()
                .withFieldsString(form, 0, 0, "Fornavn", "Efternavn")
                .withFieldsInt(form, form.getColumn(), form.getRow(), "Telefonnummer")
                .withFieldsString(form, form.getColumn(), form.getRow(), "Email", "Adresse")
                .withFieldsInt(form, form.getColumn(), form.getRow(), "CPR")
                .build();
            return form;
        }

        protected Form buildCarForm() {
            form = new Form.Builder()
                .withFieldsInt(form, 0, 0, "Årgang", "Pris")
                .withFieldsString(form, form.getColumn(), form.getRow(), "Model")
                .build();
            return form;
        }

        protected Form buildLoanForm() {
            form = new Form.Builder()
                    //.withFieldsUneditable(form, 0, 0, "Stelnummer", "Kundens CPR")
                    .withFieldsInt(form, form.getColumn(), form.getRow(), "Lånets størrelse", "Udbetaling")
                    .withFieldsInt(form, form.getColumn(), form.getRow(), "Rente", "Start dato", "Forfaldsdag")
                    .withDropDownBoxes(form, CarController.getCars(), form.getColumn(), form.getRow(),"Bil")
                    .withDropDownBoxes(form, CustomerController.getCustomers(), form.getColumn(), form.getRow(), "CPR & Kunde")
                    .build();
            return form;
        }
    }
}
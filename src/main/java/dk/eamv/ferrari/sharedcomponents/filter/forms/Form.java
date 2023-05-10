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
                form.getGridPane().add(vBox, column, row);
                form.getFieldsList().add(textField);
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
                form.getGridPane().add(vBox, column, row);
                form.getFieldsList().add(textField);
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
                form.getGridPane().add(vBox, column, row);
                form.getFieldsList().add(textField);
                column++;
            }

            form.setColumn(column);
            form.setRow(row);
            return this;
        }

        protected Form build() {
            return form;
        }

        private static GridPane createGridPane() {
            GridPane gridPane = new GridPane();
            gridPane.setVgap(25);
            gridPane.setHgap(50);
            gridPane.setAlignment(Pos.CENTER);

            return gridPane;
        }

        public Form createCustomerForm() {
            form = new Form.Builder()
                .withFieldsString(form, 0, 0, "Fornavn", "Efternavn", "Email", "Adresse")
                .withFieldsInt(form, form.getColumn(), form.getRow(), "Telefonnummer", "CPR")
                .build();
            return form;
        }

        public Form createCarForm() {
            form = new Form.Builder()
                .withFieldsInt(form, 0, 0, "Årgang", "Pris", "Stelnummer")
                .withFieldsString(form, form.getColumn(), form.getRow(), "Model")
                .build();
            return form;
        }

        public Form createLoanForm() {
            form = new Form.Builder()
                .withFieldsInt(form, 0, 0, "Stelnummer", "Kunde CPR", "Lånets størrelse", "Udbetaling")
                .withFieldsInt(form, form.getColumn(), form.getRow(), "Rente", "Start dato", "Forfaldsdag")
                .build();
            return form;
        }
    }
}
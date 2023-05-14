package dk.eamv.ferrari.sharedcomponents.forms;

import java.util.ArrayList;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteCB;
import dk.eamv.ferrari.sharedcomponents.nodes.NumericTextField;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
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
    private ArrayList<ComboBox<?>> boxList;
    private int column;
    private int row;

    private Form() {
        gridPane = createGridPane();
        fieldsList = new ArrayList<TextField>();
        boxList = new ArrayList<ComboBox<?>>();
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

    protected ArrayList<ComboBox<?>> getBoxlist() {
        return boxList;
    }

    protected boolean hasFilledFields(Form form) {
        for (TextField i : fieldsList) {
            if (i.getText().isEmpty()) {
                FormWrapper.setFieldsRed(form);
                return false;
            }
        }
        
        for (ComboBox i : boxList) {
            if (i.getSelectionModel().getSelectedItem() == null) {
                FormWrapper.setFieldsRed(form);
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

        protected Builder withDropDownBoxes(Form form, ObservableList<?> content, int column, int row, String... input) {
            for (String i : input) {
                VBox vBox = new VBox();
                Label heading = new Label(i);
                ComboBox<?> dropDown = AutoCompleteCB.create(content);
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
                .withFieldsString(this.form, 0, 0, "Fornavn", "Efternavn", "Email", "Adresse")
                .withFieldsInt(this.form, this.form.getColumn(), this.form.getRow(), "Telefonnummer", "CPR")
                .build();
            return form;
        }

        protected Form buildCarForm() {
            form = new Form.Builder()
                .withFieldsInt(this.form, 0, 0, "Årgang", "Pris")
                .withFieldsString(this.form, this.form.getColumn(), this.form.getRow(), "Model")
                .build();
            return form;
        }

        protected Form buildLoanForm() {
            form = new Form.Builder()
                    .withFieldsInt(this.form, 0, 0, "Stelnummer", "Kunde CPR", "Lånets størrelse", "Udbetaling")
                    .withFieldsInt(this.form, this.form.getColumn(), this.form.getRow(), "Rente", "Start dato", "Forfaldsdag")
                    .withDropDownBoxes(this.form, CarController.getCars(), this.form.getColumn(), this.form.getRow(),"Bil")
                    .withDropDownBoxes(this.form, CustomerController.getCustomers(), this.form.getColumn(), this.form.getRow(), "CPR & Kunde")
                    .build();
            return form;
        }
    }
}
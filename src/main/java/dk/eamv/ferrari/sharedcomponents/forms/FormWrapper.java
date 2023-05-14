package dk.eamv.ferrari.sharedcomponents.forms;

import java.util.ArrayList;
import java.sql.Date;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.scenes.loan.LoanStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public final class FormWrapper {
    /*
     * Lavet af: Christian
     * Wraps the entire form into a dialog box, then adds buttons and mouselisteners to those buttons.
     * Checks if all fields are full, then ok button runs the query into the database.
     */

    protected static Dialog wrapCreate(Form form, CRUDType type) {
        Dialog dialog = new Dialog<>();

        Label missingInput = new Label("Fejl: manglede input");
        missingInput.setVisible(false);
        missingInput.setPadding(new Insets(0, 0, 0, 100));
        Button buttonOK = new Button("OK");
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(e -> {
            dialog.setResult(true);
            dialog.close();
        });

        switch (type) {
            case LOAN:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields(form)) {
                        dialog.setResult(true);
                        //TODO: Actual implementation of selection from AutoCompleteCB
                        int carID = (int) Math.random() * 100000;
                        int customerID = (int) Math.random() * 100000;
                        int employeeID = (int) Math.random() * 100000;
                        int loanSize = getInt(form, 2);
                        double downPayment = getDouble(form, 3);
                        double interestRate = getDouble(form, 4);
                        //TODO: Figure out how to select a data in the dialog. Placeholders for now.
                        Date startDate = new Date(2025, 1, 1);
                        Date endDate = new Date(2025, 1, 1);
                        LoanStatus loanStatus = new LoanStatus(3);
                        Loan loan = new Loan(carID, customerID, employeeID, loanSize, downPayment, interestRate, startDate, endDate, loanStatus);
                        LoanController.getLoans().add(loan);
                        LoanModel.create(loan);
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                    }
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields(form)) {
                        dialog.setResult(true);
                        String firstName = getString(form, 0);
                        String lastName = getString(form, 1);
                        String phoneNumber = getString(form, 4);
                        String email = getString(form, 2);
                        String address = getString(form, 3);
                        String cpr = getString(form, 5);
                        Customer customer = new Customer(firstName, lastName, phoneNumber, email, address, cpr);
                        CustomerController.getCustomers().add(customer); //add to TableView
                        CustomerModel.create(customer); //add to DB
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                    }
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields(form)) {
                        dialog.setResult(true);
                        int frameNumber = getInt(form, 2);
                        String model = getString(form, 3);
                        int year = getInt(form, 0);
                        double price = getDouble(form, 1);
                        Car car = new Car(frameNumber, model, year, price);
                        dialog.close();
                        CarController.getCars().add(car); //add to TableView
                        CarModel.create(car); //add to DB
                    } else {
                        missingInput.setVisible(true);
                    }
                });
                break;

            default:
                break;
        }

        HBox buttons = new HBox(buttonCancel, buttonOK, missingInput);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);

        return dialog;
    }

    protected static Dialog wrapUpdate(Form form, Car car) {
        Dialog dialog = new Dialog<>();

        Label missingInput = new Label("Fejl: manglede input");
        missingInput.setVisible(false);
        missingInput.setPadding(new Insets(0, 0, 0, 100));
        Button buttonOK = new Button("OK");
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(e -> {
            dialog.setResult(true);
            dialog.close();
        });
        //Fill fields here
        form.getFieldsList().get(0).setText(Integer.toString(car.getYear()));
        form.getFieldsList().get(1).setText(Double.toString(car.getPrice()));
        form.getFieldsList().get(2).setText(car.getModel());
        buttonOK.setOnMouseClicked(e -> {
            if (form.hasFilledFields(form)) {
                Car newCar = getFieldsCar(form, dialog); //create new object based on updated fields.
                newCar.setId(car.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                CarModel.update(newCar); //update in DB     
            } else {
                missingInput.setVisible(true);
            }
        });

        HBox buttons = new HBox(buttonCancel, buttonOK, missingInput);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);

        return dialog;
    }
    
    protected static void setFieldsRed(Form form) {
        String redStyle = """
                    -fx-prompt-text-fill: F50000;
                    -fx-background-color: #f7adb1;
                    -fx-border-color: F50000;
                """;
        ArrayList<TextField> fieldsList = form.getFieldsList();
        ArrayList<ComboBox<?>> boxList = form.getBoxlist();
        for (TextField textField : fieldsList) {
            if (textField.getText().isEmpty()) {
                textField.setStyle(redStyle);
            }
        }

        for (ComboBox comboBox : boxList) {
            if (comboBox.getSelectionModel().getSelectedItem() == null) {
                comboBox.setStyle(redStyle);
            }
        }
    }

    private static Car getFieldsCar(Form form, Dialog dialog) {
        dialog.setResult(true);
        String model = getString(form, 2);
        int year = getInt(form, 0);
        double price = getDouble(form, 1);
        Car car = new Car(model, year, price);
        dialog.close();

        return car;
    }
    
    private static String getString(Form form, int index) {
        return form.getFieldsList().get(index).getText();
    }

    private static int getInt(Form form, int index) {
        return Integer.valueOf(form.getFieldsList().get(index).getText());
    }

    private static double getDouble(Form form, int index) {
        return Double.valueOf(form.getFieldsList().get(index).getText());
    }
}

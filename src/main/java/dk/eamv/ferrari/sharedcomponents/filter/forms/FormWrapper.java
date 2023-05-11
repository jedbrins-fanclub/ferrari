package dk.eamv.ferrari.sharedcomponents.filter.forms;

import java.util.ArrayList;
import java.sql.Date;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.scenes.loan.LoanStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

    protected static Dialog wrap(Form form, CRUDType type) {
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
            //TODO: Loan should be considered a placeholder until MVP is done, then think of a better implementation.
            case LOAN:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields()) {
                        dialog.setResult(true);
                        //TODO: The ID's should be gotten from a dropdown menu that queries the relative table, instead of random.
                        //TODO: Consider if this should be autoincremented in DB instead, else add a field for manual ID input.
                        //EDIT: THEY NEED TO. Due to foreignkey constraints the INSERT statement will not execute. This is on hold until MVP is done.
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
                        LoanModel.create(loan);
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                        setFieldsRed(form);
                    }
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields()) {
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
                        setFieldsRed(form);
                    }
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.hasFilledFields()) {
                        dialog.setResult(true);
                        int frameNumber = getInt(form, 2);
                        String model = getString(form, 3);
                        int year = getInt(form, 0);
                        double price = getDouble(form, 1);
                        Car car = new Car(frameNumber, model, year, price);
                        CarController.getCars().add(car); //add to TableView
                        CarModel.create(car); //add to DB
                        dialog.close();
                    } else {
                        missingInput.setVisible(true);
                        setFieldsRed(form);
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
    
    private static void setFieldsRed(Form form) {
        ArrayList<TextField> fieldsList = form.getFieldsList();
        for (TextField textField : fieldsList) {
            if (textField.getText().isEmpty()) {
                textField.setStyle("""
                            -fx-prompt-text-fill: F50000;
                            -fx-background-color: #f7adb1;
                            -fx-border-color: F50000;
                        """);
            }
        }
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

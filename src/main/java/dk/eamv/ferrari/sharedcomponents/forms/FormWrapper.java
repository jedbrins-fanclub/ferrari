package dk.eamv.ferrari.sharedcomponents.forms;

import java.util.ArrayList;
import java.sql.Date;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.customer.CustomerView;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.scenes.loan.LoanStatus;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public final class FormWrapper {
    /*
     * Lavet af: Christian
     * Wraps the entire form into a dialog box, then adds buttons and mouselisteners to those buttons.
     * Checks if all fields are full, then ok button runs the query into the database.
     */

    private static Label missingInput = new Label("Fejl: manglede input");

    protected static Dialog wrapCreate(Form form, CRUDType type) {
        Button buttonOK = new Button("OK");
        Dialog dialog = createStandardDialog(form, buttonOK);

        setCreateMouseListener(type, buttonOK, form, dialog);

        return dialog;
    }

    protected static Dialog wrapUpdate(Form form, Car car) {
        Button buttonOK = new Button("OK");
        Dialog dialog = createStandardDialog(form, buttonOK);
        setFieldsCar(form, car);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyFilledFields()) {
                Car newCar = getFieldsCar(form, dialog); //create new object based on updated fields.
                newCar.setId(car.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                CarModel.update(newCar); //update in DB  
                
                //update in TableView
                ObservableList<Car> cars = CarController.getCars();
                int index = cars.indexOf(car);
                cars.remove(index);
                cars.add(index, newCar);   
            } 
        });

        return dialog;
    }

    protected static Dialog wrapUpdate(Form form, Customer customer) {
        Button buttonOK = new Button("OK");
        Dialog dialog = createStandardDialog(form, buttonOK);
        setFieldsCustomer(form, customer);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyFilledFields()) {
                Customer newCustomer = getFieldsCustomer(form, dialog); //create new object based on updated fields.
                newCustomer.setId(customer.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                CustomerModel.update(newCustomer); //update in DB   

                //update in TableView 
                ObservableList<Customer> customers = CustomerController.getCustomers();
                int index = customers.indexOf(customer);
                customers.remove(index);
                customers.add(index, newCustomer); 
            }
        });

        return dialog;
    }

    private static Dialog createStandardDialog(Form form, Button buttonOK) {
        Dialog dialog = new Dialog<>();

        // Close the dialog when pressing X
        // https://stackoverflow.com/a/36262208
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        missingInput.setVisible(false);
        missingInput.setPadding(new Insets(0, 0, 0, 100));
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(e -> {
            dialog.setResult(true);
            dialog.close();
        });
        HBox buttons = new HBox(buttonCancel, buttonOK, missingInput);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);

        return dialog;
    }

    private static void setCreateMouseListener(CRUDType type, Button buttonOK, Form form, Dialog dialog) {
        switch (type) {
            case LOAN:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.verifyFilledFields()) {
                        dialog.setResult(true);
                        Customer customer = FormWrapper.getComboBox(form, 1);
                        System.out.printf("FIRSTNAME %s, PHONE NUMBER %s\n", customer.getFirstName(), customer.getPhoneNumber());

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
                    } 
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.verifyFilledFields()) {
                        Customer customer = getFieldsCustomer(form, dialog);
                        CustomerController.getCustomers().add(customer);
                        CustomerModel.create(customer);
                    } 
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.verifyFilledFields()) {
                        Car car = getFieldsCar(form, dialog);
                        CarController.getCars().add(car);
                        CarModel.create(car);
                    }
                });
                break;

            default:
                break;
        }
    }

    private static void setFieldsCar(Form form, Car car) {
        ArrayList<String> input = car.getPropperties();
        ArrayList<TextField> fieldsList = form.getFieldsList();
        for (int i = 0; i < fieldsList.size(); i++) {
            fieldsList.get(i).setText(input.get(i));
        }
    }

    private static Car getFieldsCar(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        Car car = new Car(getString(form, 2), getInt(form, 0), getDouble(form, 1));

        return car;
    }

    private static void setFieldsCustomer(Form form, Customer customer) {
        ArrayList<String> input = customer.getPropperties();
        ArrayList<TextField> fieldsList = form.getFieldsList();
        for (int i = 0; i < fieldsList.size(); i++) {
            fieldsList.get(i).setText(input.get(i));
        }
    }

    private static Customer getFieldsCustomer(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        Customer customer = new Customer(getString(form, 0), getString(form, 1), getString(form, 2), getString(form, 3), getString(form, 4), getString(form, 5));

        return customer;
    }

    private static <E> E getComboBox(Form form, int index) {
        return (E)form.getBoxlist().get(index).getSelectedItem();
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

    protected static Label getMissingInput() {
        return missingInput;
    }
}

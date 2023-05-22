package dk.eamv.ferrari.sharedcomponents.forms;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import dk.api.rki.CreditRator;
import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.scenes.loan.LoanStatus;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import java.lang.Runnable;

public final class FormWrapper {
    /*
     * Lavet af: Christian
     * Wraps the entire form into a dialog box, then adds buttons and mouselisteners to those buttons.
     * Checks if all fields are full, then ok button runs the query into the database.
     */

    private static Dialog<Object> dialog;
    private static Label errorLabel = new Label();
    private static Button buttonOK = new Button("OK");
    private static Button buttonCancel = new Button("Fortryd");
    private static Rating creditRating = null;

    public static Dialog<Object> getDialog() {
        return dialog;
    }

    protected static void wrapCreate(Form form, CRUDType type) {
        setDialog(form);
        setCreateMouseListener(type, form, dialog);
    }

    protected static void wrapUpdate(Form form, Car car) {
        setDialog(form);
        setFieldsCar(form, car);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
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
    }

    protected static void wrapUpdate(Form form, Customer customer) {
        setDialog(form);
        setFieldsCustomer(form, customer);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Customer newCustomer = getFieldsCustomer(form); //create new object based on updated fields.
                newCustomer.setId(customer.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                CustomerModel.update(newCustomer); //update in DB   

                //update in TableView 
                ObservableList<Customer> customers = CustomerController.getCustomers();
                int index = customers.indexOf(customer);
                customers.remove(index);
                customers.add(index, newCustomer); 
            }
        });
    }

    private static void setDialog(Form form) {
        dialog = new Dialog<>();

        // Close the dialog when pressing X
        // https://stackoverflow.com/a/36262208
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add("dialog.css");
        dialogPane.getStyleClass().add("dialog");
        errorLabel.setVisible(false);
        errorLabel.setPadding(new Insets(0, 0, 0, 100));
        errorLabel.getStyleClass().add("errorLabel");
        buttonCancel.setOnMouseClicked(e -> {
            dialog.setResult(true);
            dialog.close();
        }); 
        HBox buttons = new HBox(buttonCancel, buttonOK, errorLabel);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);
    }

    private static void showCreditRatingError() {
        errorLabel.setText("Kunde har kreditværdighed D");
        errorLabel.setVisible(true);
    }

    private static void checkRKI(Form form) {
        Customer customer = getFromComboBox(form, "CPR & Kunde");
        if (customer == null) {
            return;
        }

        Window window = dialog.getDialogPane().getScene().getWindow();
        EventHandler<WindowEvent> prev = window.getOnCloseRequest();
        Platform.runLater(() -> {
            buttonOK.setDisable(true);
            window.setOnCloseRequest(event -> {});

            errorLabel.setText("Finder kreditværdighed for kunde");
            errorLabel.setVisible(true);
        });

        String cpr = customer.getCpr();
        creditRating = CreditRator.i().rate(cpr);

        Platform.runLater(() -> {
            if (creditRating.equals(Rating.D)) {
                showCreditRatingError();
            } else {
                errorLabel.setVisible(false);
            }

            window.setOnCloseRequest(prev);
            buttonOK.setDisable(false);
        });
    }

    private static void setCreateMouseListener(CRUDType type, Form form, Dialog dialog) {
        switch (type) {
            case LOAN:
                bindLoanSize(form);
                bindFieldsCar(form);
                bindFieldsCustomer(form);
                bindFieldsEmployee(form);

                buttonOK.setOnMouseClicked(e -> {
                    if (creditRating.equals(Rating.D)) {
                        showCreditRatingError();
                        return;
                    }

                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }

                    Customer customer = getFromComboBox(form, "CPR & Kunde");
                    if (CreditRator.i().rate(customer.getCpr()).equals(Rating.D)) {
                        displayErrorMessage("Kunden har kreditværdighed D");
                        return;
                    }

                    if (Double.valueOf(calculateLoanSize(form)) < 0) {
                        displayErrorMessage("Lånets størrelse kan ikke være mindre end det udbetalte beløb");
                        return;
                    }

                    Employee employee = getFromComboBox(form, "Medarbejder");
                    if (employee.getMaxLoan() < getDouble(form, "Lånets størrelse")) {
                        displayErrorMessage("Lånets størrelse overskrider medarbejderens beføjelser.");
                        return;
                    }
                    
                    Loan loan = getFieldsLoan(form, dialog);
                    LoanController.getLoans().add(loan);
                    LoanModel.create(loan);
                    dialog.close();
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");getErrorLabel().setText("Mangler input i markerede felter");
                        return;
                    }
                
                    Customer customer = getFieldsCustomer(form);
                    CustomerController.getCustomers().add(customer);
                    CustomerModel.create(customer);
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }
                    
                    Car car = getFieldsCar(form, dialog);
                    CarController.getCars().add(car);
                    CarModel.create(car);
                });
                break;

            default:
                break;
        }
    }

    private static void setFieldsCar(Form form, Car car) {
        ArrayList<String> input = car.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }


    private static Car getFieldsCar(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        Car car = new Car(getString(form, "Model"), getInt(form, "Årgang"), getDouble(form, "Pris"));

        return car;
    }

    private static void setFieldsCustomer(Form form, Customer customer) {
        ArrayList<String> input = customer.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }

    private static Customer getFieldsCustomer(Form form) {
        dialog.setResult(true);
        dialog.close();
        Customer customer = new Customer(getString(form, "Fornavn"), getString(form, "Efternavn"), getString(form, "Telefonnummer"), getString(form, "Email"), getString(form, "Adresse"), getString(form, "CPR"));

        return customer;
    }
    
    private static Loan getFieldsLoan(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        //TODO: Implement date, employee, loanstatus.
        Car car = getFromComboBox(form, "Bil");
        Customer customer = getFromComboBox(form, "CPR & Kunde");
        Employee employee = getFromComboBox(form, "Medarbejder");
        Loan loan = new Loan(car.getId(), customer.getId(), employee.getId(), getDouble(form, "Lånets størrelse"), getDouble(form, "Udbetaling"), getDouble(form, "Rente"), getSelectedDate(form, "Start dato DD/MM/ÅÅÅÅ"), getSelectedDate(form, "Slut dato DD/MM/ÅÅÅÅ"), new LoanStatus(3));
        return loan;
    }

    //TODO: Implement this.
    private static void setFieldsLoan() {

    }

    private static void bindFieldsCar(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        AutoCompleteComboBox<Car> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Bil");
        comboBox.setOnAction(e -> {
            Car car = getFromComboBox(form, "Bil");
            if (car != null) {
                setText(form, "Model", car.getModel());
                setText(form, "Årgang", String.valueOf(car.getYear()));
                setText(form, "Pris", String.valueOf(car.getPrice()));
                setText(form, "Stelnummer", String.valueOf(car.getId()));
                loanSize.setText(calculateLoanSize(form));
            }
        });
    }
    
    private static void bindFieldsCustomer(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("CPR & Kunde");
        comboBox.setOnAction(e -> {
            Customer customer = getFromComboBox(form, "CPR & Kunde");
            if (customer != null) {
                new Thread(() -> checkRKI(form)).start();
                setText(form, "Kundens Fornavn", customer.getFirstName());
                setText(form, "Kundens Efternavn", customer.getLastName());
                setText(form, "Kundens CPR", customer.getCpr());
                setText(form, "Kundens Telefon nr.", customer.getPhoneNumber());
                setText(form, "Kundens Adresse", customer.getAddress());
                setText(form, "Kundens Email", customer.getEmail());
            }
        });
    }

    private static void bindFieldsEmployee(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Medarbejder");
        comboBox.setOnAction(e -> {
            Employee employee = getFromComboBox(form, "Medarbejder");
            if (employee != null) {
                setText(form, "Medarbejderens Fornavn", employee.getFirstName());
                setText(form, "Medarbejderens Efternavn", employee.getLastName());
                setText(form, "Medarbejderens ID", String.valueOf(employee.getId()));
                setText(form, "Medarbejderens Telefon nr.", employee.getPhoneNumber());
                setText(form, "Medarbejderens Email", employee.getEmail());
            }
        });
    }
    
    private static void bindLoanSize(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        ((TextField) form.getFieldMap().get("Udbetaling")).setOnKeyPressed(e -> loanSize.setText(calculateLoanSize(form)));
    }
    
    private static String calculateLoanSize(Form form) {
        double price = 0;
        double downpayment = 0;
        
        Car car = getFromComboBox(form, "Bil");
        if (car != null) {
            price = car.getPrice();
        } 

        TextField textField = ((TextField) form.getFieldMap().get("Udbetaling"));
        if (!textField.getText().isEmpty()) {
            downpayment = Double.valueOf(textField.getText());
        } 

        return String.valueOf(price - downpayment);
    }

    private static <E> E getFromComboBox(Form form, String key) {
        AutoCompleteComboBox<E> acb = ((AutoCompleteComboBox) form.getFieldMap().get(key));
        return acb.getSelectedItem();
    }
    
    private static void setText(Form form, String key, String text) {
        TextField textField = (TextField) form.getFieldMap().get(key);
        textField.setText(text);
    }

    private static void setChoice(Form form, String key, String choice) {

    }

    private static void setDate(Form form, String key, String date) {

    }

    private static String getString(Form form, String key) {
        return ((TextField)form.getFieldMap().get(key)).getText();
    }

    private static int getInt(Form form, String key) {
        return Integer.valueOf(getString(form, key));
    }

    private static double getDouble(Form form, String key) {
        return Double.valueOf(getString(form, key));
    }

    private static Date getSelectedDate(Form form, String key) {
        DatePicker datePicker = ((DatePicker) form.getFieldMap().get(key));
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    protected static Label getErrorLabel() {
        return errorLabel;
    }

    private static void displayErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}

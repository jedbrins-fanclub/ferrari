package dk.eamv.ferrari.sharedcomponents.forms;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
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

    private static Label errorLabel = new Label();

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

        return dialog;
    }

    protected static Dialog wrapUpdate(Form form, Customer customer) {
        Button buttonOK = new Button("OK");
        Dialog dialog = createStandardDialog(form, buttonOK);
        setFieldsCustomer(form, customer);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
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

        errorLabel.setVisible(false);
        errorLabel.setPadding(new Insets(0, 0, 0, 100));
        Button buttonCancel = new Button("Fortryd");
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

        return dialog;
    }

    private static void setCreateMouseListener(CRUDType type, Button buttonOK, Form form, Dialog dialog) {
        switch (type) {
            case LOAN:
                bindFieldsCar(form);
                bindFieldsCustomer(form);
                bindFieldsEmployee(form);
                buttonOK.setOnMouseClicked(e -> {

                    if (!form.verifyHasFilledFields()) {
                        getErrorLabel().setText("Mangler input i markerede felter");
                        getErrorLabel().setVisible(true);
                        return;
                    }

                    Customer customer = getComboBox(form, 1);
                    if (CreditRator.i().rate(customer.getCpr()).equals(Rating.D)) {
                        getErrorLabel().setText("Kunde her kreditværdighed D");
                        getErrorLabel().setVisible(true);
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
                    if (form.verifyHasFilledFields()) {
                        Customer customer = getFieldsCustomer(form, dialog);
                        CustomerController.getCustomers().add(customer);
                        CustomerModel.create(customer);
                    } 
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (form.verifyHasFilledFields()) {
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
        ArrayList<Control> fieldsList = form.getFieldsList();
        for (int i = 0; i < fieldsList.size(); i++) {
            ((TextField) fieldsList.get(i)).setText(input.get(i));
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
        ArrayList<Control> fieldsList = form.getFieldsList();
        for (int i = 0; i < fieldsList.size(); i++) {
            ((TextField) fieldsList.get(i)).setText(input.get(i));
        }
    }

    private static Customer getFieldsCustomer(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        Customer customer = new Customer(getString(form, 0), getString(form, 1), getString(form, 2), getString(form, 3), getString(form, 4), getString(form, 5));

        return customer;
    }
    
    private static Loan getFieldsLoan(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        //TODO: Implement date, employee, loanstatus.
        Car car = getComboBox(form, 0);
        Customer customer = getComboBox(form, 1);
        Employee employee = getComboBox(form, 2);
        Loan loan = new Loan(car.getId(), customer.getId(), employee.getId(), getDouble(form, 18), getDouble(form, 19), getDouble(form, 20), getSelectedDate(form, 21), getSelectedDate(form, 22), new LoanStatus(3));
        return loan;
    }

    //TODO: Implement this.
    private static void setFieldsLoan() {

    }

    private static void bindFieldsCar(Form form) {
        ArrayList<Control> fields = form.getFieldsList();
        AutoCompleteComboBox<Car> comboBox = (AutoCompleteComboBox) fields.get(0);
        comboBox.setOnAction(e -> {
            Car car = getComboBox(form, 0);
            if (car != null) {
                ((TextField) fields.get(3)).setText(car.getModel());
                ((TextField) fields.get(6)).setText(String.valueOf(car.getYear()));
                ((TextField) fields.get(9)).setText(String.valueOf(car.getPrice()));
                ((TextField) fields.get(12)).setText(String.valueOf(car.getId()));
            }
        });
    }
    
    private static void bindFieldsCustomer(Form form) {
        ArrayList<Control> fields = form.getFieldsList();
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) fields.get(1);
        comboBox.setOnAction(e -> {
            Customer customer = getComboBox(form, 1);
            if (customer != null) {
                ((TextField) fields.get(4)).setText(customer.getFirstName());
                ((TextField) fields.get(7)).setText(customer.getLastName());
                ((TextField) fields.get(10)).setText(customer.getCpr());
                ((TextField) fields.get(13)).setText(customer.getPhoneNumber());
                ((TextField) fields.get(15)).setText(customer.getAddress());
                ((TextField) fields.get(16)).setText(customer.getEmail());
            }
        });
    }

    private static void bindFieldsEmployee(Form form) {
        ArrayList<Control> fields = form.getFieldsList();
        AutoCompleteComboBox<Employee> comboBox = (AutoCompleteComboBox<Employee>) fields.get(2);
        comboBox.setOnAction(e -> {
            Employee employee = getComboBox(form, 2);
            if (employee != null) {
                ((TextField) fields.get(5)).setText(employee.getFirstName());
                ((TextField) fields.get(8)).setText(employee.getLastName());
                ((TextField) fields.get(11)).setText(String.valueOf(employee.getId()));
                ((TextField) fields.get(14)).setText(employee.getPhoneNumber());
                ((TextField) fields.get(17)).setText(employee.getEmail());
            }
        });
    }

    private static <E> E getComboBox(Form form, int index) {
        //Hej Flemming den her vil vi ikke have til eksamen.
        AutoCompleteComboBox<E> acb = ((AutoCompleteComboBox)form.getFieldsList().get(index));
        return acb.getSelectedItem();
    }
    
    private static String getString(Form form, int comboBoxIndex) {
        return ((TextField) form.getFieldsList().get(comboBoxIndex)).getText();
    }

    private static int getInt(Form form, int index) {
        return Integer.valueOf(((TextField) form.getFieldsList().get(index)).getText());
    }

    private static double getDouble(Form form, int index) {
        return Double.valueOf(((TextField) form.getFieldsList().get(index)).getText());
    }

    private static Date getSelectedDate(Form form, int index) {
        DatePicker datePicker = ((DatePicker) form.getFieldsList().get(index));
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    protected static Label getErrorLabel() {
        return errorLabel;
    }
}

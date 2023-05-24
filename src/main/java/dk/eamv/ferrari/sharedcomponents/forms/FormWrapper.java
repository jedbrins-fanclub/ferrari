package dk.eamv.ferrari.sharedcomponents.forms;

import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public final class FormWrapper {
    /*
     * Lavet af: Christian
     * Wraps the entire form into a dialog box, then adds buttons and mouselisteners to those buttons.
     * Checks if all fields are full, then ok button runs the query into the database.
     */

    private static Dialog<Object> dialog;
    private static Form form;
    private static Label errorLabel = new Label();
    private static Button buttonOK = new Button("OK");
    private static Button buttonCancel = new Button("Fortryd");
    private static Rating creditRating = null;
    private static double interestRate;

    public static Dialog<Object> getDialog() {
        return dialog;
    }
    
    protected static void wrapCreate(Form form, CRUDType type) {
        initDialog(form);
        setCreateMouseListener(type, form, dialog);
        if (type == CRUDType.LOAN) {
            checkRate(form);
        }
    }

    protected static void wrapUpdate(Form form, Car car) {
        initDialog(form);
        FormInputHandler.setFieldsCar(form, car);
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

    protected static void wrapUpdate(Form form, Employee employee) {
        initDialog(form);
        setFieldsEmployee(form, employee);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Employee newEmployee = getFieldsEmployee(form, dialog); //create new object based on updated fields.
                newEmployee.setId(employee.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                EmployeeModel.update(newEmployee); //update in DB  
                
                //update in TableView
                ObservableList<Employee> employees = EmployeeController.getEmployees();
                int index = employees.indexOf(employee);
                employees.remove(index);
                employees.add(index, newEmployee);   
            } 
        });
    }

    protected static void wrapUpdate(Form form, Loan loan) {
        initDialog(form);
        Car car = CarModel.read(loan.getCar_id());
        Customer customer = CustomerModel.read(loan.getCustomer_id());
        Employee employee = EmployeeModel.read(loan.getEmployee_id());
        setFieldsLoan(form, car, customer, employee, loan);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Loan newLoan = getFieldsLoan(form); //create new object based on updated fields.
                newLoan.setId(loan.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                LoanModel.update(newLoan); //update in DB   

                //update in TableView 
                ObservableList<Loan> loans = LoanController.getLoans();
                int index = loans.indexOf(loan);
                loans.remove(index);
                loans.add(index, newLoan); 
            }
        });
    }

    protected static void wrapUpdate(Form form, Customer customer) {
        initDialog(form);
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

    private static void initDialog(Form form) {
        dialog = new Dialog<>();

        // Close the dialog when pressing X
        // https://stackoverflow.com/a/36262208
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        interestRate = 0.0;

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
        HBox buttons = new HBox(buttonCancel, buttonOK, form.getForwardBoss(), errorLabel);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);
    }
}

package dk.eamv.ferrari.sharedcomponents.forms;

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
    private static Button buttonOK = new Button("OK");

    /**
     * Closes the active dialog.
     */
    protected static void closeDialog(Dialog<Object> dialog) {
        dialog.setResult(true);
        dialog.close();
    }

    /**
     * Shows the active dialog.
     * @param title - the title that will be displayed in the dialog Header.
     */
    protected static void showDialog(String title) {
        dialog.setTitle(title);
        dialog.show();
    }

    protected static Dialog<Object> getDialog() {
        return dialog;
    }

    /**
     * Sets the active form in this class, and the other relevant helper classes.
     * @param form - the form of the active dialog.
     */
    protected static void initForm(Form form) {
        FormWrapper.form = form;
        FormInputHandler.setForm(form);
        FormBinder.setForm(form);
    }
    
    /**
     * Creates a dialog based on the CRUD type argument passed, then sets the mouselisteners for it.
     * @param form - the form of the active dialog.
     * @param type - (CRUDType. Car, Customer, Employee, Loan)
     */
    protected static void wrapCreate(Form form, CRUDType type) {
        initForm(form);
        initDialog();
        FormBinder.setCreateMouseListener(type);
        if (type == CRUDType.LOAN) {
            FormBinder.applyLoanFormBinds();
            FormThreadHandler.checkRate();
        }
    }

    /**
     * Opens a dialog, then fills it with the properties of the Car object, allowing the user to change the fields.
     * @param form - the form of the active dialog.
     * @param car - the Car object to be updated.
     */
    protected static void wrapUpdate(Form form, Car car) {
        initForm(form);
        initDialog();
        FormInputHandler.setFieldsCar(car);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Car newCar = FormInputHandler.getFieldsCar();
                newCar.setId(car.getId());
                CarModel.update(newCar);

                //update in TableView
                ObservableList<Car> cars = CarController.getCars();
                int index = cars.indexOf(car);
                cars.remove(index);
                cars.add(index, newCar);

                closeDialog(dialog);
            }
        });
    }
    
    /**
     * Opens a dialog, then fills it with the properties of the Customer object, allowing the user to change the fields.
     * @param form - the form of the active dialog.
     * @param customer - the Customer object to be updated.
     */
    protected static void wrapUpdate(Form form, Customer customer) {
        initForm(form);
        initDialog();
        FormInputHandler.setFieldsCustomer(customer);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Customer newCustomer = FormInputHandler.getFieldsCustomer();
                newCustomer.setId(customer.getId());
                CustomerModel.update(newCustomer);

                //update in TableView 
                ObservableList<Customer> customers = CustomerController.getCustomers();
                int index = customers.indexOf(customer);
                customers.remove(index);
                customers.add(index, newCustomer);

                closeDialog(dialog);
            }
        });
    }

    /**
     * Opens a dialog, then fills it with the properties of the Employee object, allowing the user to change the fields.
     * @param form - the form of the active dialog.
     * @param employee - the Employee object to be updated.
     */
    protected static void wrapUpdate(Form form, Employee employee) {
        initForm(form);
        initDialog();
        FormInputHandler.setFieldsEmployee(employee);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Employee newEmployee = FormInputHandler.getFieldsEmployee();
                newEmployee.setId(employee.getId());
                EmployeeModel.update(newEmployee);

                //update in TableView
                ObservableList<Employee> employees = EmployeeController.getEmployees();
                int index = employees.indexOf(employee);
                employees.remove(index);
                employees.add(index, newEmployee);

                closeDialog(dialog);
            }
        });
    }

    /**
     * Opens a dialog, then fills it with the properties of the Loan object, allowing the user to change the fields.
     * @param form - the form of the active dialog.
     * @param loan - the Loan object to be updated.
     */
    protected static void wrapUpdate(Form form, Loan loan) {
        initForm(form);
        initDialog();

        //query DB for objects matching ID.
        Car car = CarModel.read(loan.getCar_id());
        Customer customer = CustomerModel.read(loan.getCustomer_id());
        Employee employee = EmployeeModel.read(loan.getEmployee_id());

        FormBinder.applyLoanFormBinds();
        FormInputHandler.setFieldsLoan(car, customer, employee, loan);
        FormBinder.refreshLoanFormBinds(loan);

        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Loan newLoan = FormInputHandler.getFieldsLoan();
                newLoan.setId(loan.getId());
                LoanModel.update(newLoan);

                //update in TableView 
                ObservableList<Loan> loans = LoanController.getLoans();
                loans.remove(loan);
                loans.add(newLoan);
            }
        });
    }

    /**
     * Sets dialog to be the standard dialog to be wrapped.
     */
    private static void initDialog() {
        dialog = new Dialog<>();

        // Close the dialog when pressing X
        // https://stackoverflow.com/a/36262208
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add("dialog.css");
        dialogPane.getStyleClass().add("dialog");

        Button buttonCancel = new Button("Fortryd");
        buttonCancel.setOnMouseClicked(e -> {
            closeDialog(dialog);
        });

        Label statusLabel = FormStatusHandler.getStatusLabel();
        statusLabel.setVisible(false);

        HBox buttons = new HBox(buttonCancel, buttonOK, form.getForwardBoss(), statusLabel);
        buttons.setSpacing(25);

        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        
        dialog.getDialogPane().setContent(vBox);
    }

    protected static Button getButtonOK() {
        return buttonOK;
    }
}

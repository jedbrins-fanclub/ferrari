package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.loan.Loan;

/*
 * Lavet af: Christian, Stefan og Benjamin
 */

/**
 * Utilizes the Factory pattern to create an interface of the available dialogs.
 * It calls the FormWrapper class, which wraps the form returned by the FormBuilder.
 * Holds CREATE and UPDATE forms.
 */
public final class FormFactory {
    private static Form.Builder builder = new Form.Builder();

    public static void createCarFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildCarForm(), CRUDType.CAR);
        FormWrapper.showDialog("Opret bil");
    }

    public static void createCustomerFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildCustomerForm(), CRUDType.CUSTOMER);
        FormWrapper.showDialog("Opret kunde");
    }

    public static void createEmployeeFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildEmployeeForm(), CRUDType.EMPLOYEE);
        FormWrapper.showDialog("Opret medarbejder");
    }

    public static void createLoanFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildLoanForm(), CRUDType.LOAN);
        FormWrapper.showDialog("Opret lån");
    }

    public static void updateCarFormDialogBox(Car car) {
        FormWrapper.wrapUpdate(builder.buildCarForm(), car);
        FormWrapper.showDialog("Opdater bil");
    }

    public static void updateCustomerFormDialogBox(Customer customer) {
        FormWrapper.wrapUpdate(builder.buildCustomerForm(), customer);
        FormWrapper.showDialog("Opdater Kunde");
    }

    public static void updateEmployeeFormDialogBox(Employee employee) {
        FormWrapper.wrapUpdate(builder.buildEmployeeForm(), employee);
        FormWrapper.showDialog("Opdater medarbejder");
    }

    public static void updateLoanFormDialogBox(Loan loan) {
        FormWrapper.wrapUpdate(builder.buildLoanForm(), loan);
        FormWrapper.showDialog("Opdater lån");
    }
}

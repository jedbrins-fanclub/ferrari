package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.loan.Loan;

// Lavet af: Christian, Stefan og Benjamin

/**
 * Utilizes the Factory pattern to create an interface of the available dialogs.
 * It calls the FormWrapper class, which wraps the form returned by the FormBuilder.
 * Holds CREATE and UPDATE forms.
 */
public final class FormFactory {
    private static Form.Builder builder = new Form.Builder();

    public static void createDialogBox(CRUDType type, String title) {
        switch (type) {
            case CAR -> FormWrapper.wrapCreate(builder.buildCarForm(), type);
            case CUSTOMER -> FormWrapper.wrapCreate(builder.buildCustomerForm(), type);
            case EMPLOYEE -> FormWrapper.wrapCreate(builder.buildEmployeeForm(), type);
            case LOAN -> FormWrapper.wrapCreate(builder.buildLoanForm(), type);
        }

        FormWrapper.showDialog(title);
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

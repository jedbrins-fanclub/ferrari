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

    public static void updateDialogBox(CRUDType type, String title, Object object) {
        switch (type) {
            case CAR -> FormWrapper.wrapUpdate(builder.buildCarForm(), (Car)object);
            case CUSTOMER -> FormWrapper.wrapUpdate(builder.buildCustomerForm(), (Customer)object);
            case EMPLOYEE -> FormWrapper.wrapUpdate(builder.buildEmployeeForm(), (Employee)object);
            case LOAN -> FormWrapper.wrapUpdate(builder.buildLoanForm(), (Loan)object);
        }
        FormWrapper.showDialog(title);
    }
}

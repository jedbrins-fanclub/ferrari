package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.loan.Loan;
import javafx.scene.control.Dialog;

public final class FormFactory {
    /*
     * Lavet af: Christian, Stefan og Benjamin
     */

    private static Form.Builder builder = new Form.Builder();

    public static void createCustomerFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildCustomerForm(), CRUDType.CUSTOMER);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret kunde");
        dialog.show();
    }

    public static void createCarFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildCarForm(), CRUDType.CAR);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret bil");
        dialog.show();
    }

    public static void createLoanFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildLoanForm(), CRUDType.LOAN);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret lån");
        dialog.show();
    }

    public static void createEmployeeFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildEmployeeForm(), CRUDType.EMPLOYEE);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret sælger");
        dialog.show();
    }

    public static void updateCarFormDialogBox(Car car) {
        FormWrapper.wrapUpdate(builder.buildCarForm(), car);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opdater bil");
        dialog.show();
    }

    public static void updateCustomerFormDialogBox(Customer customer) {
        FormWrapper.wrapUpdate(builder.buildCustomerForm(), customer);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opdater kunde");
        dialog.show();
    }

    public static void updateLoanFormDialogBox(Loan loan) {
        FormWrapper.wrapUpdate(builder.buildLoanForm(), loan);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opdater lån");
        dialog.show();
    }

    public static void updateEmployeeFormDialogBox(Employee employee) {
        FormWrapper.wrapUpdate(builder.buildEmployeeForm(), employee);
        Dialog<?> dialog = FormWrapper.getDialog();
        dialog.setTitle("Opdater sælger");
        dialog.show();
    }
}

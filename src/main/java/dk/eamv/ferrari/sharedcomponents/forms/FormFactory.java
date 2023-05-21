package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.customer.Customer;
import javafx.scene.control.Dialog;

public final class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private static Form.Builder builder = new Form.Builder();
    private static Dialog<?> dialog;

    public static void createCustomerFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildCustomerForm(), CRUDType.CUSTOMER);
        dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret kunde");
        dialog.show();
    }

    public static void createCarFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildCarForm(), CRUDType.CAR);
        dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret bil");
        dialog.show();
    }

    public static void createLoanFormDialogBox() {
        FormWrapper.wrapCreate(builder.buildLoanForm(), CRUDType.LOAN);
        dialog = FormWrapper.getDialog();
        dialog.setTitle("Opret lån");
        dialog.show();
    }

    public static void updateCarFormDialogBox(Car car) {
        FormWrapper.wrapUpdate(builder.buildCarForm(), car);
        dialog = FormWrapper.getDialog();
        dialog.setTitle("Opdater bil");
        dialog.show();
    }

    public static void updateCustomerFormDialogBox(Customer customer) {
        FormWrapper.wrapUpdate(builder.buildCustomerForm(), customer);
        dialog = FormWrapper.getDialog();
        dialog.setTitle("Opdater kunde");
        dialog.show();
    }
}

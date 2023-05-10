package dk.eamv.ferrari.sharedcomponents.filter.forms;

import javafx.scene.control.Dialog;

public final class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private static Form.Builder builder = new Form.Builder();

    public static void createCustomerFormDialogBox() {
        Dialog<Void> dialog = FormWrapper.wrap(builder.buildCustomerForm(), CRUDType.CUSTOMER);
        dialog.setTitle("Opret kunde");
        dialog.show();
    }

    public static void createCarFormDialogBox() {
        Dialog<Void> dialog = FormWrapper.wrap(builder.buildCarForm(), CRUDType.CAR);
        dialog.setTitle("Opret bil");
        dialog.show();
    }

    public static void createLoanFormDialogBox() {
        Dialog<Void> dialog = FormWrapper.wrap(builder.buildLoanForm(), CRUDType.LOAN);
        dialog.setTitle("Opret lån");
        dialog.show();
    }
}

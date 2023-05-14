package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.Car;
import javafx.scene.control.Dialog;

public final class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private static Form.Builder builder = new Form.Builder();

    public static void createCustomerFormDialogBox() {
        Dialog<Void> dialog = FormWrapper.wrapCreate(builder.buildCustomerForm(), CRUDType.CUSTOMER);
        dialog.setTitle("Opret kunde");
        dialog.show();
    }

    public static void createCarFormDialogBox() {
        Dialog<Void> dialog = FormWrapper.wrapCreate(builder.buildCarForm(), CRUDType.CAR);
        dialog.setTitle("Opret bil");
        dialog.show();
    }

    public static void createLoanFormDialogBox() {
        Dialog<Void> dialog = FormWrapper.wrapCreate(builder.buildLoanForm(), CRUDType.LOAN);
        dialog.setTitle("Opret lån");
        dialog.show();
    }

    public static void updateCarFormDialogBox(Car car) {
        Dialog<Void> dialog = FormWrapper.wrapUpdate(builder.buildCarForm(), car);
        dialog.setTitle("Opdater bil");
        dialog.show();
    }
}

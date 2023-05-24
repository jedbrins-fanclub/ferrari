package dk.eamv.ferrari.sharedcomponents.forms;

import javafx.scene.control.Label;

public class FormStatusHandler {
    private static Label errorLabel = FormWrapper.getErrorLabel();

    private static void showCreditRatingError() {
        errorLabel.setText("Kunde har kreditværdighed D");
        errorLabel.setVisible(true);
    }

    protected static Label getErrorLabel() {
        return errorLabel;
    }

    private static void displayErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}

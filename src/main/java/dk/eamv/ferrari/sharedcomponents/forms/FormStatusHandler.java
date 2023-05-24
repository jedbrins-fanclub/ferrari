package dk.eamv.ferrari.sharedcomponents.forms;

import javafx.scene.control.Label;

public class FormStatusHandler {
    //TODO: Figure a smarter way to display statuses
    private static Label errorLabel = FormWrapper.getErrorLabel();

    protected static void showCreditRatingError() {
        errorLabel.setText("Kunde har kreditværdighed D");
        errorLabel.setVisible(true);
    }

    protected static Label getErrorLabel() {
        return errorLabel;
    }

    protected static void displayErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}

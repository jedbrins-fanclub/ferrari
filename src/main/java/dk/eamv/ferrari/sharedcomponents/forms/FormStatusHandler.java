package dk.eamv.ferrari.sharedcomponents.forms;

import javafx.scene.control.Label;

// Made by: Benjamin and Christian
public class FormStatusHandler {
    private static Label statusLabel = new Label();

    protected static Label getStatusLabel() {
        return statusLabel;
    }

    /**
     * Displays the statusLabel in the dialog.
     * @param isError - whether it's an error or not
     * @param message - the message to be displayed
     */
    protected static void displayMessage(boolean isError, String message) {
        statusLabel.getStyleClass().setAll(isError ? "dialog-error-label" : "dialog-status-label");
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }

    /**
     * Hides the statusLabel in the dialog.
     */
    protected static void hideStatusLabel() {
        statusLabel.setVisible(false);
    }
}

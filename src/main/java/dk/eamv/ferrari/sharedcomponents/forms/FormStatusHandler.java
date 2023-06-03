package dk.eamv.ferrari.sharedcomponents.forms;

import javafx.scene.control.Label;

// Made by: Benjamin and Christian
public class FormStatusHandler {
    private static Label statusLabel = new Label();

    protected static Label getStatusLabel() {
        return statusLabel;
    }

    /**
     * Displays the statusLabel in the dialog, colored red.
     * @param message - the message to be displayed
     */
    protected static void displayErrorMessage(String message) {
        statusLabel.getStyleClass().setAll("dialog-error-label");
        statusLabel.setText(message);
        statusLabel.setVisible(true);
    }

    /**
     * Displays the statusLabel in the dialog, colored gold.
     * @param message - the message to be displayed
     */
    protected static void displayStatusMessage(String message) {
        statusLabel.getStyleClass().setAll("dialog-status-label");
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

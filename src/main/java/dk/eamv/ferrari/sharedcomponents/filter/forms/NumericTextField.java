package dk.eamv.ferrari.sharedcomponents.filter.forms;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        // Save the previous text for restoration if invalid input
        String prev = getText();
        super.replaceText(start, end, text.trim());

        // Validate the text, reset the text and caret if not numeric
        if (!validate(getText())) {
            int position = getCaretPosition();
            setText(prev);
            positionCaret(position);
        }
    }

    // Validate if the text is numeric
    private boolean validate(String text) {
        // If it's empty there's no reason to try parsing it.
        if (text.isEmpty()) {
            return true;
        }

        // If it's blank (contains spaces) then it's not numeric
        if (text.isBlank()) {
            return false;
        }

        // If it can be parsed to a double, then it works
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}

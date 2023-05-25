package dk.eamv.ferrari.sharedcomponents.nodes;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField {
    private boolean allowDecimals;
    private int maxLength;

    public NumericTextField() {
        maxLength = -1;
        allowDecimals = true;
    }
    public NumericTextField(boolean allowDecimals, int maxLength) {
        this.allowDecimals = allowDecimals;
        this.maxLength = maxLength;
    }

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

        // If it's blank (contains spaces) or it contains letters then it's not numeric
        if (text.isBlank() || text.matches(".*[a-zA-Z].*")) {
            return false;
        }

        // Validate that input is shorter than max
        if (maxLength > 0 && text.length() > maxLength) {
            return false;
        }

        // If it can be parsed to a double, then it works
        try {
            if (allowDecimals) {
                Double.parseDouble(text);
            } else {
                Integer.parseInt(text);
            }
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}

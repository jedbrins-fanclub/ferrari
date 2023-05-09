package dk.eamv.ferrari.sharedcomponents.filter.forms;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        boolean isValid = true;
        try {
            Double.parseDouble(text);
        } catch (NumberFormatException exception) {
            isValid = false;
        }
        return isValid;
    }
}

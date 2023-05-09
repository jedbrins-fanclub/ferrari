package dk.eamv.ferrari.sharedcomponents.filter.forms;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/*
 * Lavet af: Christian
 * Wrapper class forms, linking a GridPane to an ArrayList of its fields.
 * Made so that we can iterate over the list of fields to check if theres content, when "OK" button is clicked.
 */
public class Form {
    private GridPane gridPane;
    private ArrayList<TextField> fieldsList;

    public Form(GridPane gridPane, ArrayList<TextField> fieldsList) {
        this.gridPane = gridPane;
        this.fieldsList = fieldsList;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ArrayList<TextField> getFieldsList() {
        return fieldsList;
    }
    
    public boolean hasFilledFields() {
        for (TextField i : fieldsList) {
            if (i.getText().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}

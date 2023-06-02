package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.scene.control.Button;

/**
 * Created by: Mikkel
 * <p>
 * Class makes it possible to create a button to affect a selected row in FilteredTables, with the feature of being
 * disabled until a row is selected for the given table.
 * <p>
 * It is not used in the current state of the program, as this task is handled by buttons within the tables themselves.
 */
public class ControlButton extends Button {
    private final FilteredTableBuilderInfo<?> filteredTableBuilderInfo;

    /**
     * @param filteredTableBuilderInfo interface to pass information about the FilteredTable
     * @param buttonText text to be displayed in the button
     */
    public ControlButton(FilteredTableBuilderInfo<?> filteredTableBuilderInfo, String buttonText) {
        this.filteredTableBuilderInfo = filteredTableBuilderInfo;

        setText(buttonText);
        setDisable(true);
        setupListener();
    }

    /**
     * Sets the listener for given TableView in order to disable the ControlButton until a row is selected.
     */
    private void setupListener() {
        // Wildcard used as the type is not relevant for setting the listener
        FilteredTable<?> filteredTable = filteredTableBuilderInfo.getFilteredTable();

        // Enable the button when a row is selected
        filteredTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDisable(newValue == null);
        });
    }
}

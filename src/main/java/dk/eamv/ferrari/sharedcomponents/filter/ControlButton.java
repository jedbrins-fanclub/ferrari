package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.scene.control.Button;

public class ControlButton extends Button {

    private final FilteredTableBuilderInfo<?> filteredTableBuilderInfo;

    public ControlButton(FilteredTableBuilderInfo<?> filteredTableBuilderInfo, String buttonText) {
        this.filteredTableBuilderInfo = filteredTableBuilderInfo;

        setText(buttonText);
        setDisable(true);
        setupListener();
    }

    private void setupListener() {
        FilteredTable<?> filteredTable = filteredTableBuilderInfo.getFilteredTable();

        // Enable the button when a row is selected
        filteredTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDisable(newValue == null);
        });
    }
}

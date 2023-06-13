package dk.eamv.ferrari.scenes.table;

import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TableView {
    protected static FilteredTable<?> tableView;
    protected static SearchContainer searchContainer;
    protected static HBox buttonRow = new HBox(5);

    public static StackPane getTableScene() {
        tableView.setPrefHeight(1200);

        for (Node button : buttonRow.getChildren()) {
            button.getStyleClass().add("significant-button");
        }

        HBox containerAboveTable = new HBox(10);
        containerAboveTable.setAlignment(Pos.CENTER_LEFT);
        containerAboveTable.setPadding(new Insets(0, 10, 0, 0));
        containerAboveTable.getChildren().addAll(searchContainer, buttonRow); // Put search box top right of table

        VBox tableContainer = new VBox(25);
        tableContainer.setAlignment(Pos.BOTTOM_CENTER);
        tableContainer.setMaxWidth(Double.MAX_VALUE);
        tableContainer.setPadding(new Insets(25));
        tableContainer.getStyleClass().add("table-view-container");
        tableContainer.getChildren().addAll(containerAboveTable, tableView);

        // Apply drop shadow to parentContainer to avoid applying it to VBox children
        StackPane parentContainer = new StackPane(tableContainer);
        parentContainer.getStyleClass().add("drop-shadow-effect");

        StackPane pane = new StackPane(parentContainer);
        pane.setPadding(new Insets(50));

        return pane;
    }

    protected static <E> void initSearchContainer(FilteredTableBuilder<E> filteredTable) {
        searchContainer = new SearchContainer(new FilterTextField<>(filteredTable));
    }

    public static void refreshTableView() {
        tableView.refresh();
        tableView.sort();
    }
}

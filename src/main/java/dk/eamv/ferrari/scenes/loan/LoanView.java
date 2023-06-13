package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoanView {
    private static FilteredTable<Loan> tableView;
    private static SearchContainer searchContainer;
    private static HBox buttonRow = new HBox(5);

    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.LOANS);

        scene.setCenter(getLoanView());

        return scene;
    }

    private static StackPane getLoanView() {
        LoanController.initFilterBuilder();

        initTableView();
        initSearchContainer();
        initButtonRow();

        HBox containerAboveTable = new HBox();
        containerAboveTable.setAlignment(Pos.CENTER_LEFT);
        containerAboveTable.setPadding(new Insets(0, 10, 0, 0));
        containerAboveTable.setSpacing(10);
        containerAboveTable.getChildren().addAll(searchContainer, buttonRow); // Put search box top right of table

        VBox tableContainer = new VBox();
        tableContainer.setAlignment(Pos.BOTTOM_CENTER);
        tableContainer.setMaxWidth(Double.MAX_VALUE);
        tableContainer.setPadding(new Insets(25));
        tableContainer.setSpacing(25);
        tableContainer.getStyleClass().add("table-view-container");
        tableContainer.getChildren().addAll(containerAboveTable, tableView);


        // Apply drop shadow to parentContainer to avoid applying it to VBox children
        StackPane parentContainer = new StackPane(tableContainer);
        parentContainer.getStyleClass().add("drop-shadow-effect");


        StackPane window = new StackPane(parentContainer);
        window.setPadding(new Insets(50));

        return window;
    }

    private static void initTableView() {
        tableView = LoanController.filteredTableBuilder.build();
        tableView.setPrefHeight(1200);
        tableView.setRowFactory(tableview -> {
            TableRow<Loan> row = new TableRow<>();
            return row ;
        });
    }

    private static void initSearchContainer() {
        searchContainer = new SearchContainer(new FilterTextField<>(LoanController.filteredTableBuilder));
    }

    private static void initButtonRow() {
        Button buttonCreate = new Button("Opret ny låneaftale");
        Button buttonExport = new Button("Eksporter alle lån");

        buttonCreate.getStyleClass().add("significant-button");
        buttonExport.getStyleClass().add("significant-button");

        buttonCreate.setOnAction(e -> LoanController.showCreateLoan());
        buttonExport.setOnAction(e -> LoanController.exportAllLoans());

        buttonRow.getChildren().setAll(buttonCreate, buttonExport);
    }

    protected static void showEditLoanDialog(Loan selectedLoan) {
        LoanController.updateLoan(selectedLoan);
    }

    public static void refreshTableView() {
        tableView.refresh();
        tableView.sort();
    }
}

package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sharedcomponents.filter.ControlButton;
import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoanView {

    private static FilteredTable<Loan> tableView;
    private static SearchContainer searchContainer;
    private static Button buttonCreate;

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

        initButtonCreate();
        initButtonEdit();
        initButtonDelete();

        HBox containerAboveTable = new HBox();
        containerAboveTable.setAlignment(Pos.CENTER_RIGHT);
        containerAboveTable.setPadding(new Insets(0, 10, 0, 0));
        containerAboveTable.getChildren().addAll(buttonCreate, searchContainer); // Put search box top right of table

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
        window.setPadding(new Insets(75));
        window.setStyle("-fx-background-color: lightgrey");

        return window;
    }

    private static void initTableView() {
        tableView = LoanController.filteredTableBuilder.build();
        tableView.setPrefHeight(700);
    }

    private static void initSearchContainer() {
        searchContainer = new SearchContainer(new FilterTextField<>(LoanController.filteredTableBuilder));
    }

    private static void initButtonCreate() {
        buttonCreate = new Button("Opret ny låneaftale");

        buttonCreate.setOnAction(e -> LoanController.createLoan());
    }

    private static void initButtonEdit() {
        Button buttonEdit = new ControlButton(LoanController.filteredTableBuilder, "Rediger denne låneaftale");

        buttonEdit.setOnAction(e -> {
            Loan selectedLoan = tableView.getSelectionModel().getSelectedItem();
            if (selectedLoan != null) {
                showEditLoanDialog(selectedLoan);
            }
        });
    }

    private static void initButtonDelete() {
        Button buttonDelete = new ControlButton(LoanController.filteredTableBuilder, "Slet denne låneaftale");

        buttonDelete.setOnAction(e -> {
            Loan selectedLoan = tableView.getSelectionModel().getSelectedItem();
            LoanController.deleteLoan(selectedLoan);
        });
    }

    protected static void showEditLoanDialog(Loan selectedLoan) {
        //TODO: Insert dialog for editing Loans here
    }

    public static void refreshTableView() {
        tableView.refresh();
        tableView.sort();
    }
}

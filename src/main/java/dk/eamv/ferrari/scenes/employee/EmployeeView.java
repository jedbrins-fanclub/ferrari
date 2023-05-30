package dk.eamv.ferrari.scenes.employee;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EmployeeView {

    private static FilteredTable<Employee> tableView;
    private static SearchContainer searchContainer;
    private static Button buttonCreate;

    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.SELLERS);

        scene.setCenter(getEmployeeView());

        return scene;
    }

    private static StackPane getEmployeeView() {
        EmployeeController.initFilterBuilder();

        initTableView();
        initSearchContainer();
        initButtonCreate();

        HBox containerAboveTable = new HBox();
        containerAboveTable.setAlignment(Pos.CENTER_LEFT);
        containerAboveTable.setPadding(new Insets(0, 10, 0, 0));
        containerAboveTable.setSpacing(10);
        containerAboveTable.getChildren().addAll(searchContainer, buttonCreate); // Put search box top right of table

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
        window.setStyle("-fx-background-color: lightgrey");

        return window;
    }

    private static void initTableView() {
        tableView = EmployeeController.filteredTableBuilder.build();
        tableView.setPrefHeight(1200);
    }

    private static void initSearchContainer() {
        searchContainer = new SearchContainer(new FilterTextField<>(EmployeeController.filteredTableBuilder));
    }

    private static void initButtonCreate() {
        buttonCreate = new Button("Registrer ny medarbejder");
        buttonCreate.getStyleClass().add("create-button");

        buttonCreate.setOnAction(e -> {
            EmployeeController.showCreateEmployee();
        }); 
    }

    protected static void showEditEmployeeDialog(Employee selectedEmployee) {
        EmployeeController.updateEmployee(selectedEmployee);
    }

    public static void refreshTableView() {
        tableView.refresh();
        tableView.sort();
    }
}

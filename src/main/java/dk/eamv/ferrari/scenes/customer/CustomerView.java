package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.sharedcomponents.filter.ControlButton;
import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerView {

    private static FilteredTable<Customer> tableView;
    private static SearchContainer searchContainer;
    private static Button buttonCreate;

    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.CUSTOMERS);

        scene.setCenter(getCustomerView());

        return scene;
    }

    private static StackPane getCustomerView() {
        CustomerController.initFilterBuilder();

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
        tableView = CustomerController.filteredTableBuilder.build();
        tableView.setPrefHeight(700);
    }

    private static void initSearchContainer() {
        // When instantiating the FilterTextField, the instance of the builder is passed as a parameter
        // This is in order to give the FilterTextField access to the FilteredTable and ProperValueGetters
        searchContainer = new SearchContainer(new FilterTextField<>(CustomerController.filteredTableBuilder));
    }

    private static void initButtonCreate() {
        buttonCreate = new Button("Registrer ny kunde");

        buttonCreate.setOnAction(e -> CustomerController.createCustomer());
    }

    private static void initButtonEdit() {
        Button buttonEdit = new ControlButton(CustomerController.filteredTableBuilder, "Rediger denne kunde");

        buttonEdit.setOnAction(e -> {
            Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                showEditCustomerDialog(selectedCustomer);
            }
        });
    }

    private static void initButtonDelete() {
        Button buttonDelete = new ControlButton(CustomerController.filteredTableBuilder, "Slet denne kunde");

        buttonDelete.setOnAction(e -> {
            Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            CustomerController.deleteCustomer(selectedCustomer);
        });
    }

    protected static void showEditCustomerDialog(Customer selectedCustomer) {
        //TODO: Insert dialog for editing customers here
    }

    public static void refreshTableView() {
        tableView.refresh();
        tableView.sort();
    }
}

package dk.eamv.ferrari.scenes.customer;

import java.util.Optional;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.scenes.table.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

// Made by: Mikkel and Stefan

public class CustomerView extends TableView {
    public static Pane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.CUSTOMERS);

        scene.setCenter(getCustomerView());

        return scene;
    }

    private static StackPane getCustomerView() {
        CustomerController.initFilterBuilder();
        initTableView();
        initSearchContainer(CustomerController.filteredTableBuilder);
        initButtons();
        return getTableScene();
    }

    private static void initTableView() {
        tableView = CustomerController.filteredTableBuilder.build();
    }

    private static void initButtons() {
        Button buttonCreate = new Button("Registrer ny kunde");
        buttonCreate.setOnAction(e -> CustomerController.showCreateCustomer());
        buttonRow.getChildren().setAll(buttonCreate);
    }

    protected static void showEditCustomerDialog(Customer selectedCustomer) {
        CustomerController.updateCustomer(selectedCustomer);
    }

    protected static boolean confirmBan(Customer customer) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Ban Kunde");
        alert.setHeaderText(null);
        alert.setContentText(
            String.format("Er du sikker på at du vil banlyse %s %s?", customer.getFirstName(), customer.getLastName())
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}

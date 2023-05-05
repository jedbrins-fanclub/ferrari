package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTable;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Lavet af: Mikkel
 */

public class CarView {

    private static FilteredTable<Car> tableView;
    private static SearchContainer searchContainer;
    private static Button buttonEdit;
    private static Button buttonDelete;

    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.CARS);

        scene.setCenter(getCarView());

        return scene;
    }

    private static VBox getCarView() {
        CarController.initFilterBuilder();

        initTableView();
        initSearchContainer();

        initButtonEdit();
        initButtonDelete();

        HBox containerAboveTable = new HBox();
        containerAboveTable.getChildren().add(searchContainer); // Put search box top right of table
        containerAboveTable.setAlignment(Pos.CENTER_RIGHT);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(600);
        container.getChildren().addAll(containerAboveTable, tableView, buttonEdit, buttonDelete);

        return container;
    }

    private static void initTableView() {
        tableView = CarController.filterBuilder.build();
    }

    private static void initSearchContainer() {
        searchContainer = new SearchContainer(CarController.filterBuilder.withFilterTextField(tableView));
    }

    private static void initButtonEdit() {
        /* Pass the creation of the button to the instance of FilterBuilder
         * This allows the listener to be set once in the builder method "withControlButton"
         * Takes a FilteredTable as a parameter, so the listener is set for that instance
         */
        buttonEdit = CarController.filterBuilder.withControlButton("Edit this car", tableView);

        buttonEdit.setOnAction(e -> {
            Car selectedCar = tableView.getSelectionModel().getSelectedItem();
            if (selectedCar != null) {
                showEditCarDialog(selectedCar);
            }
        });
    }

    private static void initButtonDelete() {
        buttonDelete = CarController.filterBuilder.withControlButton("Delete this car", tableView);
        buttonDelete.setOnAction(e -> CarController.deleteCar(tableView.getSelectionModel().getSelectedItem()));
    }

    private static void showEditCarDialog(Car selectedCar) {
        // Create and configure the dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Edit Car");

        // Create and configure UI components for editing the car's properties
        Label modelLabel = new Label("Model:");
        TextField modelField = new TextField(selectedCar.getModel());

        Label yearLabel = new Label("Årgang:");
        TextField yearField = new TextField(Integer.toString(selectedCar.getYear()));

        Label priceLabel = new Label("Pris:");
        TextField priceField = new TextField(Double.toString(selectedCar.getPrice()));

        // Create and configure a "Save" button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Update the car's properties based on user input
            selectedCar.setModel(modelField.getText());
            selectedCar.setYear(Integer.parseInt(yearField.getText()));
            selectedCar.setPrice(Double.parseDouble(priceField.getText()));

            // Update the car in the ObservableList and the database
            CarController.updateCar(selectedCar);

            // Close the dialog
            dialog.close();
        });

        // Create a GridPane layout for the form fields
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(modelLabel, 0, 0);
        gridPane.add(modelField, 1, 0);
        gridPane.add(yearLabel, 0, 1);
        gridPane.add(yearField, 1, 1);
        gridPane.add(priceLabel, 0, 2);
        gridPane.add(priceField, 1, 2);

        // Create a VBox layout for the form and the "Save" button
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        container.getChildren().addAll(gridPane, saveButton);

        // Set the dialog's scene and show the dialog
        Scene scene = new Scene(container);
        dialog.setScene(scene);
        dialog.show();
    }

    public static void refreshTableView() {
        tableView.refresh();
    }
}
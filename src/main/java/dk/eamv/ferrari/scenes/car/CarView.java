package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.scenes.table.TableView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

// Made by: Mikkel
public class CarView extends TableView {
    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.CARS);

        scene.setCenter(getCarView());

        return scene;
    }

    private static StackPane getCarView() {
        CarController.initFilterBuilder();
        initTableView(CarController.filteredTableBuilder.build());
        initSearchContainer(CarController.filteredTableBuilder);
        initButtons();
        return getTableScene();
    }

    private static void initButtons() {
        Button buttonCreate = new Button("Opret ny bil");
        buttonCreate.setOnAction(e -> CarController.showCreateCar());
        buttonRow.getChildren().setAll(buttonCreate);
    }

    protected static void showEditCarDialog(Car selectedCar) {
        CarController.updateCar(selectedCar);
    }
}

package dk.eamv.ferrari.car;

import dk.eamv.ferrari.sharedcomponents.filter.FilterTextField;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableView;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableViewBuilder;
import dk.eamv.ferrari.sharedcomponents.filter.SearchContainer;
import dk.eamv.ferrari.sidebar.SidebarButton;
import dk.eamv.ferrari.sidebar.SidebarView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CarView {

    private static FilteredTableView<Car> tableView;
    private static SearchContainer searchContainer;
    private static Button buttonDelete;

    public static BorderPane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.CARS);

        scene.setCenter(getCarView());

        return scene;
    }

    private static VBox getCarView() {
        CarController.initCarBuilder();

        initTableView();
        initSearchContainer();
        initButtonDelete();

        VBox container = new VBox();
        container.getChildren().addAll(searchContainer, tableView, buttonDelete);

        return container;
    }



    private static void initTableView() {
        tableView = CarController.carBuilder.build();
    }

    private static void initSearchContainer() {
        searchContainer = new SearchContainer(CarController.carBuilder.withFilterTextField(tableView));
    }

    private static void initButtonDelete() {
        buttonDelete = new Button("Delete this car");
        buttonDelete.setOnAction(e -> CarController.deleteCar(tableView.getSelectionModel().getSelectedItem()));
    }
}

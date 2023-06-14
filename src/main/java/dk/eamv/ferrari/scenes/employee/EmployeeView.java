package dk.eamv.ferrari.scenes.employee;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.scenes.table.TableView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class EmployeeView extends TableView {
    public static Pane getScene() {
        BorderPane scene = new BorderPane();

        scene.setLeft(SidebarView.getSidebarView());
        SidebarView.getSidebarView().setActiveToggleButton(SidebarButton.SELLERS);

        scene.setCenter(getEmployeeView());

        return scene;
    }

    private static StackPane getEmployeeView() {
        EmployeeController.initFilterBuilder();
        initTableView(EmployeeController.filteredTableBuilder.build());
        initSearchContainer(EmployeeController.filteredTableBuilder);
        initButtons();
        return getTableScene();
    }

    private static void initButtons() {
        Button buttonCreate = new Button("Registrer ny medarbejder");
        buttonCreate.setOnAction(e -> EmployeeController.showCreateEmployee()); 
        buttonRow.getChildren().setAll(buttonCreate);
    }

    protected static void showEditEmployeeDialog(Employee selectedEmployee) {
        EmployeeController.updateEmployee(selectedEmployee);
    }
}

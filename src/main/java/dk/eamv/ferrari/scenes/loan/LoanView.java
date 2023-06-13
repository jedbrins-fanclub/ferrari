package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.scenes.sidebar.SidebarButton;
import dk.eamv.ferrari.scenes.sidebar.SidebarView;
import dk.eamv.ferrari.scenes.table.TableView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LoanView extends TableView {
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
        initSearchContainer(LoanController.filteredTableBuilder);
        initButtons();
        return getTableScene();
    }

    private static void initTableView() {
        tableView = LoanController.filteredTableBuilder.build();
    }

    private static void initButtons() {
        Button buttonCreate = new Button("Opret ny låneaftale");
        Button buttonExport = new Button("Eksporter alle lån");

        buttonCreate.setOnAction(e -> LoanController.showCreateLoan());
        buttonExport.setOnAction(e -> LoanController.exportAllLoans());

        buttonRow.getChildren().setAll(buttonCreate, buttonExport);
    }

    protected static void showEditLoanDialog(Loan selectedLoan) {
        LoanController.updateLoan(selectedLoan);
    }
}

package dk.eamv.ferrari.scenes.loan;

import java.util.Optional;

import dk.eamv.ferrari.csv.CSVWriter;
import dk.eamv.ferrari.managers.SessionManager;
import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

// Made by: Mikkel
// Modified by: Benjamin (extended CRUD for sales manager)
public class LoanController {
    protected static FilteredTableBuilder<Loan> filteredTableBuilder;
    private static final ObservableList<Loan> loans = FXCollections.observableArrayList(LoanModel.readAll());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Loan>()
            .withData(loans)
            .withColumn("id", Loan::getId)
            .withColumn("Bil", Loan::getCarLabel)
            .withColumn("Kunde", Loan::getCustomerLabel)
            .withColumn("Sælger", Loan::getEmployeeLabel)
            .withColumn("Lån (DKK)", Loan::getLoanSize)
            .withColumn("Udbetaling (DKK)", Loan::getDownPayment)
            .withColumn("Rente (%)", Loan::getInterestRate)
            .withColumn("Start", Loan::getStartDate)
            .withColumn("Slut", Loan::getEndDate)
            .withProgressColumn("", Loan::getStartDate, Loan::getEndDate)
            .withStatusColumn("Status", Loan::getStatus)
            .withButtonColumn(SVGResources.getChangeStatusIcon(), LoanController::updateLoanStatus);
        
        if (SessionManager.getUser().isSalesManager()) {
            filteredTableBuilder
                .withButtonColumn(SVGResources.getEditIcon(), LoanView::showEditLoanDialog)
                .withButtonColumn(SVGResources.getDeleteIcon(), LoanController::deleteLoan);
        }

        filteredTableBuilder.withButtonColumn(SVGResources.getExportCSVIcon(), LoanController::exportLoan);
    }

    protected static void showCreateLoan() {
        FormFactory.createLoanFormDialogBox();
    }

    public static void createLoan(Loan loan) {
        LoanModel.create(loan);
    }

    protected static void updateLoan(Loan loan) {
        FormFactory.updateLoanFormDialogBox(loan);
        LoanView.refreshTableView();
    }

    protected static void deleteLoan(Loan loan) {
        LoanModel.delete(loan.getId());

        // When removing the Loan from the ObservableList, the TableView updates automatically
        loans.remove(loan);
    }

    protected static void updateLoanStatus(Loan loan) {
        // Create a ChoiceBox to enable the user to set the status of this loan
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (LoanState state : LoanState.values()) {
            choiceBox.getItems().add(new LoanStatus(state).getDisplayName());
        }

        // Initial value of the ChoiceBox is set to the current status of the loan
        choiceBox.setValue(loan.getStatus().getDisplayName());

        // Use a dialog for the ChoiceBox
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Opdater status");
        dialog.setHeaderText("Vælg ny status for dette lån");
        dialog.getDialogPane().setContent(choiceBox);
        Optional<ButtonType> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }

        if (result.get() == ButtonType.OK) {
            for (LoanState state : LoanState.values()) {
                if (new LoanStatus(state).getDisplayName().equals(choiceBox.getValue())) {
                    loan.setStatus(new LoanStatus(state));
                    LoanModel.update(loan);
                    LoanView.refreshTableView(); // TableView is refreshed so the new status is shown
                    break;
                }
            }
        }
    }

    private static void exportLoan(Loan loan) {
        new Thread(() -> {
            CSVWriter writer = new CSVWriter(String.format("%d_%s_%s.csv", loan.getCustomer_id(), loan.getStartDate(), loan.getEndDate()));
            writer.writeHeader(
                "car id", "customer id", "employee id",
                "loan size", "downpayment", "interest rate",
                "start date", "end date", "status"
            );

            writer.writeRow(
                loan.getCar_id(), loan.getCustomer_id(), loan.getEmployee_id(),
                loan.getLoanSize(), loan.getDownPayment(), loan.getInterestRate(),
                loan.getStartDate(), loan.getEndDate(), loan.getStatus().getDisplayName()
            );
            writer.close();
        }).start();
    }

    public static ObservableList<Loan> getLoans() {
        return loans;
    }
}

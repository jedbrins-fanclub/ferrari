package dk.eamv.ferrari.scenes.loan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import dk.eamv.ferrari.csv.CSVWriter;
import dk.eamv.ferrari.managers.SessionManager;
import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormType;
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
            .withColumn("Start", Loan::getStartDateFormatted)
            .withColumn("Slut", Loan::getEndDateFormatted)
            .withProgressColumn("", Loan::getStartDate, Loan::getEndDate)
            .withStatusColumn("Status", Loan::getStatus)
            .withButtonColumn(SVGResources.getChangeStatusIcon(), LoanController::updateLoanStatus);
        
        if (SessionManager.getUser().isSalesManager()) {
            filteredTableBuilder
                .withButtonColumn(SVGResources.getEditIcon(), LoanView::showEditLoanDialog)
                .withButtonColumn(SVGResources.getDeleteIcon(), LoanController::deleteLoan);
        }

        filteredTableBuilder.withButtonColumn(SVGResources.getExportCSVIcon(), LoanController::export);
    }

    protected static void showCreateLoan() {
        FormFactory.createDialogBox(FormType.LOAN, "Opret Lån");
    }

    public static void createLoan(Loan loan) {
        LoanModel.create(loan);
    }

    protected static void updateLoan(Loan loan) {
        FormFactory.updateDialogBox(FormType.LOAN, "Opdater lån", loan);
    }

    protected static void deleteLoan(Loan loan) {
        LoanModel.delete(loan.getId());
        loans.remove(loan);
    }

    protected static void updateLoanStatus(Loan loan) {
        // Create a ChoiceBox to enable the user to set the status of this loan
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (LoanStatus state : LoanStatus.values()) {
            choiceBox.getItems().add(state.getDisplayName());
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
            for (LoanStatus state : LoanStatus.values()) {
                if (state.getDisplayName().equals(choiceBox.getValue())) {
                    loan.setStatus(state);
                    LoanModel.update(loan);
                    LoanView.refreshTableView(); // TableView is refreshed so the new status is shown
                    break;
                }
            }
        }
    }
    
    protected static void exportAllLoans() {
        export(Arrays.copyOf(loans.toArray(), loans.size(), Loan[].class));
    }
    
    private static void export(Loan... list) {
        new Thread(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = LocalDate.now().format(formatter);

            CSVWriter writer = new CSVWriter(formattedDate + ".csv");
            writer.writeHeader(
                "car id", "customer id", "employee id",
                "loan size", "downpayment", "interest rate",
                "start date", "end date", "status"
            );

            for (Loan loan : list) {
                writer.writeRow(
                    loan.getCar_id(), loan.getCustomer_id(), loan.getEmployee_id(),
                    loan.getLoanSize(), loan.getDownPayment(), loan.getInterestRate(),
                    loan.getStartDate(), loan.getEndDate(), loan.getStatus().getDisplayName()
                );
            }

            writer.close();
        }).start();
    }

    public static ObservableList<Loan> getLoans() {
        return loans;
    }
}

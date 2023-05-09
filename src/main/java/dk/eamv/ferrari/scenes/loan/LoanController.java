package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class LoanController {

    protected static FilteredTableBuilder<Loan> filteredTableBuilder;
    private static final ObservableList<Loan> loans = FXCollections.observableArrayList(fetchLoans());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Loan>()
                .withData(loans)
                .withColumn("id", Loan::getId)
                .withColumn("car_id", Loan::getCar_id)
                .withColumn("customer_id", Loan::getCustomer_id)
                .withColumn("employee_id", Loan::getEmployee_id)
                .withColumn("Lån", Loan::getLoanSize)
                .withColumn("Udbetaling", Loan::getDownPayment)
                .withColumn("Rente", Loan::getInterestRate)
                .withColumn("Start", Loan::getStartDate)
                .withColumn("Slut", Loan::getEndDate)
                .withColumn("Status", loan -> loan.getStatus().getDisplayName())
                .withButtonColumn("", "Rediger", LoanView::showEditLoanDialog)
                .withButtonColumn("", "Slet", LoanController::deleteLoan);
    }

    protected static ArrayList<Loan> fetchLoans() {
        return LoanModel.readAll();
    }

    protected static void createLoan(Loan loan) {
        System.out.println("Call method in LoanModel create loan with id: " + loan.getId());

        LoanView.refreshTableView();
    }

    protected static void updateLoan(Loan loan) {
        System.out.println("Call method in LoanModel update loan with id: " + loan.getId());

        LoanView.refreshTableView();
    }

    protected static void deleteLoan(Loan loan) {
        System.out.println("Call method in LoanModel to delete loan with id: " + loan.getId());

        // When removing the Loan from the ObservableList, the TableView updates automatically
        loans.remove(loan);
    }
}

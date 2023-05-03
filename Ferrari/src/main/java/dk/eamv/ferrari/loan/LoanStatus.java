package dk.eamv.ferrari.loan;

public enum LoanStatus {
    PENDING_APPROVAL("Afventer godkendelse"), // loan is submitted for approval
    APPROVED("Godkendt"), // if the sales manager has confirmed the loan, but the period has not started
    REJECTED("Afvist"), // loan has been rejected by the sales manager
    ACTIVE("Aktiv"), // loan has been accepted and is now within its payment period
    COMPLETED("Fuldført"); // loan is fully paid

    // displayName can be showed in GUI
    private final String displayName;

    LoanStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

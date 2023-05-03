package dk.eamv.ferrari.loan;

public class LoanStatus {
    private final LoanState state;

    public LoanStatus(int value) {
        if (value < 0 || value > 5) {
            throw new RuntimeException(
                String.format("Invalid loan status, expected 0-4, got %d", value)
            );
        }
        this.state = LoanState.values()[value];
    }

    public String getDisplayName() {
        return switch (state) {
            case PENDING   -> "Afventer godkendelse";
            case APPROVED  -> "Godkendt";
            case REJECTED  -> "Afvist";
            case ACTIVE    -> "Aktiv";
            case COMPLETED -> "Fuldført";
            default        -> "Invalid";
        };
    }
}

package dk.eamv.ferrari.scenes.loan;

// Made by: Benjamin and Mikkel (Rewritten)

/**
 * Enum indicating the current state of a Loan.
 */
public enum LoanStatus {
    PENDING,
    APPROVED,
    REJECTED,
    ACTIVE,
    COMPLETED;

    /**
     * Creates a LoanState based on an integer value.
     * @param value the LoanState as an integer value
     */
    public static LoanStatus valueOf(int value) {
        assert value >= 0 && value <= 4: String.format("Invalid loan status, expected 0-4, got %d", value);
        return LoanStatus.values()[value];
    }

    /**
     * @return an integer representing the LoanState.
     */
    public int toInt() {
        return switch (this) {
            case PENDING   -> 0;
            case APPROVED  -> 1;
            case REJECTED  -> 2;
            case ACTIVE    -> 3;
            case COMPLETED -> 4;
        };
    }

    /**
     * @return a String showing the LoanState
     */
    public String getDisplayName() {
        return switch (this) {
            case PENDING   -> "Afventer " + "\u231B"; // ⌛ (U+231B) hourglass
            case APPROVED  -> "Godkendt " + "\u2714"; // ✔ (U+2714) checkmark
            case REJECTED  -> "Afvist " + "\u2716"; // ✖ (U+2716) cross
            case ACTIVE    -> "Aktiv " + "\u23F3"; // ⏳ (U+23F3) hourglass
            case COMPLETED -> "Fuldført " + "\u2714"; // ✔ (U+2714) checkmark
        };
    }
}

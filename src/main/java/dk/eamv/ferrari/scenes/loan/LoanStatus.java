package dk.eamv.ferrari.scenes.loan;

/**
 * Made by: Benjamin, Mikkel
 * Checked by:
 * Modified by:
 */
public class LoanStatus {
    private final LoanState state;

    /**
     * @param state the state of the LoanStatus to create
     */
    public LoanStatus(LoanState state) {
        this.state = state;
    }

    /**
     * Creates a loan status based on an integer value.
     * @param value the LoanState as an integer value
     */
    public LoanStatus(int value) {
        if (value < 0 || value > 5) {
            throw new RuntimeException(
                String.format("Invalid loan status, expected 0-4, got %d", value)
            );
        }
        this.state = LoanState.values()[value];
    }

    /**
     * @return an integer representing the LoanState.
     */
    public int getStatusNumber() {
        return switch (state) {
            case PENDING   -> 0;
            case APPROVED  -> 1;
            case REJECTED  -> 2;
            case ACTIVE    -> 3;
            case COMPLETED -> 4;
            default        -> -1;
        };
    }

    /**
     * @return a String showing the LoanState
     */
    public String getDisplayName() {
        return switch (state) {
            case PENDING   -> "Afventer " + "\u231B"; // ⌛ (U+231B) hourglass
            case APPROVED  -> "Godkendt " + "\u2714"; // ✔ (U+2714) checkmark
            case REJECTED  -> "Afvist " + "\u2716"; // ✖ (U+2716) cross
            case ACTIVE    -> "Aktiv " + "\u23F3"; // ⏳ (U+23F3) hourglass
            case COMPLETED -> "Fuldført " + "\u2714"; // ✔ (U+2714) checkmark
        };
    }

    public LoanState getState() {
        return state;
    }
}

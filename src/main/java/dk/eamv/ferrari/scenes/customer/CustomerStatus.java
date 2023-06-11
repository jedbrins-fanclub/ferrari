package dk.eamv.ferrari.scenes.customer;

public enum CustomerStatus {
    ACTIVE,
    DELETED;

    public static CustomerStatus valueOf(int value) {
        assert value == 0 || value < CustomerStatus.values().length;

        return switch (value) {
            case 0 -> CustomerStatus.ACTIVE;
            case 1 -> CustomerStatus.DELETED;
            default -> null;
        };
    }

    public int toInt() {
        return switch (this) {
            case ACTIVE -> 0;
            case DELETED -> 1;
            default -> -1;
        };
    }
}

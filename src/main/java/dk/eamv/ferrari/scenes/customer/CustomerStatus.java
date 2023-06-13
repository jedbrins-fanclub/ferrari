package dk.eamv.ferrari.scenes.customer;

public enum CustomerStatus {
    ACTIVE,
    DELETED,
    BANNED;

    public static CustomerStatus valueOf(int value) {
        assert value == 0 || value < CustomerStatus.values().length;

        return switch (value) {
            case 0 -> CustomerStatus.ACTIVE;
            case 1 -> CustomerStatus.DELETED;
            case 2 -> CustomerStatus.BANNED;
            default -> null;
        };
    }

    public int toInt() {
        return switch (this) {
            case ACTIVE -> 0;
            case DELETED -> 1;
            case BANNED -> 2;
            default -> -1;
        };
    }
}

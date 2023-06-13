package dk.eamv.ferrari.scenes.car;

public enum CarStatus {
    ACTIVE,
    DELETED;

    public static CarStatus valueOf(int value) {
        assert value == 0 || value < CarStatus.values().length;

        return switch (value) {
            case 0 -> CarStatus.ACTIVE;
            case 1 -> CarStatus.DELETED;
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

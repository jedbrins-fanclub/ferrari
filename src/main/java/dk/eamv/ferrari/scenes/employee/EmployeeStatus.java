package dk.eamv.ferrari.scenes.employee;

public enum EmployeeStatus {
    ACTIVE,
    DELETED;

    public static EmployeeStatus valueOf(int value) {
        assert value == 0 && value < EmployeeStatus.values().length;

        return switch (value) {
            case 0 -> EmployeeStatus.ACTIVE;
            case 1 -> EmployeeStatus.DELETED;
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

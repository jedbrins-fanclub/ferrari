package dk.eamv.ferrari.sessionmanager;

import dk.eamv.ferrari.scenes.employee.Employee;

public class SessionManager {
    private static Employee user;

    public static void setUser(Employee employee) {
        user = employee;
    }

    public static Employee getUser() {
        return user;
    }
}

package dk.eamv.ferrari.sessionmanager;

import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;

public class SessionManager {
    private static Employee user;

    public static void setUser(Employee employee) {
        user = employee;
    }

    public static Employee getUser() {
        // DEBUGGING
        if (user == null) {
            user = EmployeeModel.read(1);
        }
        return user;
    }
}

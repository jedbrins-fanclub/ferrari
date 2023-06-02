package dk.eamv.ferrari.sessionmanager;

import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;

// Made by: Benjamin
public class SessionManager {
    private static Employee user;

    /**
     * Set the user that's active in session
     * @param employee
     */
    public static void setUser(Employee employee) {
        user = employee;
    }

    /**
     * Get the current user logged in
     * @return the current Employee logged in
     */
    public static Employee getUser() {
        // For debugging purposes
        if (user == null) {
            user = EmployeeModel.read(1);
        }
        return user;
    }
}

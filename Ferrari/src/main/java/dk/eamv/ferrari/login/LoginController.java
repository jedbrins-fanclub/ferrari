package dk.eamv.ferrari.login;

import dk.eamv.ferrari.employee.Employee;
import dk.eamv.ferrari.employee.EmployeeModel;
import dk.eamv.ferrari.login.LoginView;;

/**
 * Lavet af: Christian
 * Tjekket af:
 * Modificeret af:
 */
public class LoginController {
    public static void authenticate() {
        Employee employee = EmployeeModel.authenticate(LoginView.getUsernameInput(), LoginView.getPasswordInput());
        if (employee != null) {

        }
        //Take the input from textfield & passwordfield. Use it in LoginModels login query.
        //if returned rows == 1 -> get enum(byte) containing status being seller or saleschief.
        //else displayLoginFailed(); 
        System.out.println("Authenticate Placeholder. Delete when implemented.");
    }

    private static void displayLoginFailed() {

    }

    private static void displayRecoverPassword() {

    }
}
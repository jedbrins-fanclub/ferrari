package dk.eamv.ferrari.scenes.login;

import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.frontpage.FrontpageView;
import dk.eamv.ferrari.scenemanager.SceneManager;

/**
 * Lavet af: Christian
 * Tjekket af:
 * Modificeret af: Benjamin
 */
public class LoginController {
    public static void authenticate() {
        Employee employee = LoginModel.authenticate(LoginView.getUsernameInput(), LoginView.getPasswordInput());
        if (employee != null) {
            System.out.println("Changing to frontpage");
            SceneManager.changeScene(FrontpageView.getScene());
            LoginView.showErrorMessage(false);
        } else {
            LoginView.showErrorMessage(true);
        }
    }
}
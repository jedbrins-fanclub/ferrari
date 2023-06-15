package dk.eamv.ferrari.scenes.login;

import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.frontpage.FrontpageView;
import dk.eamv.ferrari.managers.SceneManager;
import dk.eamv.ferrari.managers.SessionManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

// Made by: Christian
// Checked by: Benjamin
// Modified by: Benjamin
public class LoginController {
    protected static void authenticate() {
        Employee employee = LoginModel.authenticate(LoginView.getUsernameInput(), LoginView.getPasswordInput());
        if (employee != null) {
            SessionManager.setUser(employee);
            SceneManager.changeScene(FrontpageView.getScene());
            LoginView.getErrorLabel().setVisible(false);
        } else {
            LoginView.getErrorLabel().setVisible(true);
        }
    }

    protected static void keypress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            authenticate();
        }
    }
}
package dk.eamv.ferrari.scenes.sidebar;

import dk.eamv.ferrari.scenemanager.SceneManager;
import dk.eamv.ferrari.scenes.car.CarView;
import dk.eamv.ferrari.scenes.customer.CustomerView;
import dk.eamv.ferrari.scenes.employee.EmployeeView;
import dk.eamv.ferrari.scenes.frontpage.FrontpageView;
import dk.eamv.ferrari.scenes.loan.LoanView;
import dk.eamv.ferrari.scenes.settings.SettingsView;
import dk.eamv.ferrari.scenes.login.LoginView;
import javafx.event.ActionEvent;

/**
 * Created by: Mikkel
 * <p>
 *     Handles buttons in the Sidebar in order to switch scene
 * </p>
 */
public class SidebarController {

    protected static void onDashboardButtonClick(ActionEvent event) {
        SceneManager.changeScene(FrontpageView.getScene());
    }

    protected static void onLoansButtonClick(ActionEvent event) {
        SceneManager.changeScene(LoanView.getScene());
    }

    protected static void onReportsButtonClick(ActionEvent event) {
        // Reports scene is not implemented
    }

    protected static void onCarsButtonClick(ActionEvent event) {
        SceneManager.changeScene(CarView.getScene());
    }

    protected static void onCustomersButtonClick(ActionEvent event) {
        SceneManager.changeScene(CustomerView.getScene());
    }

    protected static void onSellersButtonClick(ActionEvent event) {
        SceneManager.changeScene(EmployeeView.getScene());
    }

    protected static void onSettingsButtonClick(ActionEvent event) {
        SceneManager.changeScene(SettingsView.getScene());
    }
  
    protected static void onLogOutButtonClick(ActionEvent event) {
        SceneManager.changeScene(LoginView.getScene());
    }

}

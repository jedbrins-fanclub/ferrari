package dk.eamv.ferrari.scenes.sidebar;

import dk.eamv.ferrari.scenemanager.SceneManager;
import dk.eamv.ferrari.scenes.car.CarView;
import dk.eamv.ferrari.scenes.customer.CustomerView;
import dk.eamv.ferrari.scenes.employee.EmployeeView;
import dk.eamv.ferrari.scenes.frontpage.FrontpageView;
import dk.eamv.ferrari.scenes.loan.LoanView;
import javafx.event.ActionEvent;

public class SidebarController {

    static void onDashboardButtonClick(ActionEvent event) {
        SceneManager.changeScene(FrontpageView.getScene());
    }

    static void onLoansButtonClick(ActionEvent event) {
        SceneManager.changeScene(LoanView.getScene());
    }

    static void onReportsButtonClick(ActionEvent event) {
        System.out.println("Clicked: reports");
    }

    static void onCarsButtonClick(ActionEvent event) {
        SceneManager.changeScene(CarView.getScene());
    }

    static void onCustomersButtonClick(ActionEvent event) {
        SceneManager.changeScene(CustomerView.getScene());
    }

    static void onSellersButtonClick(ActionEvent event) {
        SceneManager.changeScene(EmployeeView.getScene());
    }

    static void onSettingsButtonClick(ActionEvent event) {
        System.out.println("Clicked: settings");
    }
}

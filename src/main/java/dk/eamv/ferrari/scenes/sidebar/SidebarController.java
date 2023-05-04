package dk.eamv.ferrari.scenes.sidebar;

import javafx.event.ActionEvent;

public class SidebarController {

    private final SidebarView sidebarView;

    public SidebarController(SidebarView sidebarView) {
        this.sidebarView = sidebarView;

        attachButtonListeners();
    }

    private void attachButtonListeners() {
        sidebarView.buttons.get(SidebarButton.DASHBOARD).setOnAction(this::onDashboardButtonClick);
        sidebarView.buttons.get(SidebarButton.LOANS).setOnAction(this::onLoansButtonClick);
        sidebarView.buttons.get(SidebarButton.REPORTS).setOnAction(this::onReportsButtonClick);
        sidebarView.buttons.get(SidebarButton.CARS).setOnAction(this::onCarsButtonClick);
        sidebarView.buttons.get(SidebarButton.CUSTOMERS).setOnAction(this::onCustomersButtonClick);
        sidebarView.buttons.get(SidebarButton.SELLERS).setOnAction(this::onSellersButtonClick);
        sidebarView.buttons.get(SidebarButton.SETTINGS).setOnAction(this::onSettingsButtonClick);
    }

    private void onDashboardButtonClick(ActionEvent event) {
        System.out.println("Clicked: dashboard");
    }

    private void onLoansButtonClick(ActionEvent event) {
        System.out.println("Clicked: loans");
    }

    private void onReportsButtonClick(ActionEvent event) {
        System.out.println("Clicked: reports");
    }

    private void onCarsButtonClick(ActionEvent event) {
        System.out.println("Clicked: cars");
    }

    private void onCustomersButtonClick(ActionEvent event) {
        System.out.println("Clicked: customers");
    }

    private void onSellersButtonClick(ActionEvent event) {
        System.out.println("Clicked: sellers");
    }

    private void onSettingsButtonClick(ActionEvent event) {
        System.out.println("Clicked: settings");
    }
}

package dk.eamv.ferrari.sidebar;

import javafx.event.ActionEvent;

public class SidebarController {

    private final SidebarView sidebarView;

    public SidebarController(SidebarView sidebarView) {
        this.sidebarView = sidebarView;

        attachButtonListeners();
    }

    private void attachButtonListeners() {
        sidebarView.dashboard.setOnAction(this::onDashboardButtonClick);
        sidebarView.loans.setOnAction(this::onLoansButtonClick);
        sidebarView.reports.setOnAction(this::onReportsButtonClick);
        sidebarView.cars.setOnAction(this::onCarsButtonClick);
        sidebarView.customers.setOnAction(this::onCustomersButtonClick);
        sidebarView.sellers.setOnAction(this::onSellersButtonClick);
        sidebarView.settings.setOnAction(this::onSettingsButtonClick);
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

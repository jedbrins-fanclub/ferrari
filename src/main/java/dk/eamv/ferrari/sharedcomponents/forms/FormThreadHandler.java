package dk.eamv.ferrari.sharedcomponents.forms;

import dk.api.bank.InterestRate;
import dk.api.rki.CreditRator;
import dk.eamv.ferrari.scenes.customer.Customer;
import javafx.application.Platform;
import javafx.stage.Window;

public class FormThreadHandler {
    protected static void checkRKI() {
        new Thread(() -> {
            Customer customer = FormInputHandler.getFromComboBox(form, "CPR & Kunde");
            if (customer == null) {
                return;
            }

            Platform.runLater(() -> {
                FormWrapper.getButtonOK().setDisable(true);
                
                errorLabel.setText("Finder kreditværdighed for kunde");
                errorLabel.setVisible(true);
            });

            String cpr = customer.getCpr();
            FormBinder.setCustomersCreditScore(CreditRator.i().rate(cpr));

            Platform.runLater(() -> {
                calculateInterestRate(form);
                if (creditRating.equals(Rating.D)) {
                    showCreditRatingError();
                } else {
                    errorLabel.setVisible(false);
                }

                window.setOnCloseRequest(prev);
                buttonOK.setDisable(false);
            });
        }).start();
    }

    protected static void checkRate() {
        new Thread(() -> {
            Window window = dialog.getDialogPane().getScene().getWindow();
            EventHandler<WindowEvent> prev = window.getOnCloseRequest();
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                window.setOnCloseRequest(event -> {});

                errorLabel.setText("Finder dagens rente");
                errorLabel.setVisible(true);
            });

            FormBinder.setBanksInterestRate(InterestRate.i().todaysRate());
            Platform.runLater(() -> {
                errorLabel.setVisible(false);
                window.setOnCloseRequest(prev);
                buttonOK.setDisable(false);
            });
        }).start();
    }
}

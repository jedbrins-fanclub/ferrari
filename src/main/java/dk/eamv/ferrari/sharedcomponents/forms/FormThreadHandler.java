package dk.eamv.ferrari.sharedcomponents.forms;

import dk.api.bank.InterestRate;
import dk.api.rki.CreditRator;
import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.customer.Customer;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class FormThreadHandler {
    private static Button buttonOK = FormWrapper.getButtonOK();

    //TODO: Benjamin write Javadoc
    protected static void checkRKI() {
        new Thread(() -> {
            Customer customer = FormInputHandler.getEntityFromComboBox("CPR & Kunde");
            if (customer == null) {
                return;
            }

            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                FormStatusHandler.displayStatusMessage("Finder kreditværdighed for kunde");
            });

            String cpr = customer.getCpr();
            Rating creditRating = CreditRator.i().rate(cpr);

            FormBinder.setCustomersCreditScore(creditRating);

            Platform.runLater(() -> {
                FormBinder.calculateInterestRate();
                if (creditRating.equals(Rating.D)) {
                    FormStatusHandler.displayErrorMessage("Kunden har kreditværdighed D");
                } else {
                    FormStatusHandler.hideStatusLabel();
                }
                
                buttonOK.setDisable(false);
            });
        }).start();
    }

    //TODO: Benjamin write Javadoc
    protected static void checkRate() {
        new Thread(() -> {
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                FormStatusHandler.displayStatusMessage("Finder dagens rente");
            });

            FormBinder.setBanksInterestRate(InterestRate.i().todaysRate());
            Platform.runLater(() -> {
                FormStatusHandler.hideStatusLabel();
                buttonOK.setDisable(false);
            });
        }).start();
    }
}

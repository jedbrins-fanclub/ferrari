package dk.eamv.ferrari.sharedcomponents.forms;

import dk.api.bank.InterestRate;
import dk.api.rki.CreditRator;
import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.customer.Customer;
import javafx.application.Platform;
import javafx.scene.control.Button;

// Made by: Benjamin and Christian
public class FormThreadHandler {
    private static Button buttonOK = FormWrapper.getButtonOK();

    protected static void checkRKI() {
        new Thread(() -> {
            Customer customer = FormInputHandler.getEntityFromComboBox("CPR & Kunde");
            if (customer == null) {
                return;
            }

            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                FormWrapper.showStatusLabel(false, "Finder kreditværdighed for kunde");
            });

            String cpr = customer.getCpr();
            Rating creditRating = CreditRator.i().rate(cpr);

            FormBinder.setCustomersCreditScore(creditRating);
            FormBinder.calculateInterestRate();

            Platform.runLater(() -> {
                if (creditRating.equals(Rating.D)) {
                    FormWrapper.showStatusLabel(true, "Kunden har kreditværdighed D");
                } else {
                    FormWrapper.hideStatusLabel();
                }
                
                buttonOK.setDisable(false);
            });
        }).start();
    }

    protected static void checkRate() {
        new Thread(() -> {
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                FormWrapper.showStatusLabel(false, "Finder dagens rente");
            });

            FormBinder.setBanksInterestRate(InterestRate.i().todaysRate());
            Platform.runLater(() -> {
                buttonOK.setDisable(false);
                FormWrapper.hideStatusLabel();
            });
        }).start();
    }
}

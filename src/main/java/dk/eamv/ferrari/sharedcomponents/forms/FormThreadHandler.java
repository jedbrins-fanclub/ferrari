package dk.eamv.ferrari.sharedcomponents.forms;

import dk.api.bank.InterestRate;
import dk.api.rki.CreditRator;
import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.customer.Customer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FormThreadHandler {
    private static Button buttonOK = FormWrapper.getButtonOK();
    //TODO: Better status implementation here.
    private static Label errorLabel = FormWrapper.getErrorLabel();

    //TODO: Benjamin write Javadoc
    protected static void checkRKI() {
        new Thread(() -> {
            Customer customer = FormInputHandler.getEntityFromComboBox("CPR & Kunde");
            if (customer == null) {
                return;
            }

            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                
                //TODO: Better status implementation here.
                errorLabel.setText("Finder kreditværdighed for kunde");
                errorLabel.setVisible(true);
            });

            String cpr = customer.getCpr();
            Rating creditRating = CreditRator.i().rate(cpr);

            FormBinder.setCustomersCreditScore(creditRating);

            Platform.runLater(() -> {
                FormBinder.calculateInterestRate();
                if (creditRating.equals(Rating.D)) {
                    //TODO: Better status implementation here.
                    FormStatusHandler.showCreditRatingError();
                } else {
                    //TODO: Better status implementation here.
                    errorLabel.setVisible(false);
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

                errorLabel.setText("Finder dagens rente");
                errorLabel.setVisible(true);
            });

            FormBinder.setBanksInterestRate(InterestRate.i().todaysRate());
            Platform.runLater(() -> {
                errorLabel.setVisible(false);
                buttonOK.setDisable(false);
            });
        }).start();
    }
}

package dk.eamv.ferrari.sharedcomponents.forms;

public class FormThreadHandler {
    private static void checkRKI(Form form) {
        new Thread(() -> {
            Customer customer = getFromComboBox(form, "CPR & Kunde");
            if (customer == null) {
                return;
            }

            Window window = dialog.getDialogPane().getScene().getWindow();
            EventHandler<WindowEvent> prev = window.getOnCloseRequest();
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                window.setOnCloseRequest(event -> {});

                errorLabel.setText("Finder kreditværdighed for kunde");
                errorLabel.setVisible(true);
            });

            String cpr = customer.getCpr();
            creditRating = CreditRator.i().rate(cpr);

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

    private static void checkRate(Form form) {
        new Thread(() -> {
            Window window = dialog.getDialogPane().getScene().getWindow();
            EventHandler<WindowEvent> prev = window.getOnCloseRequest();
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                window.setOnCloseRequest(event -> {});

                errorLabel.setText("Finder dagens rente");
                errorLabel.setVisible(true);
            });

            interestRate = InterestRate.i().todaysRate();
            Platform.runLater(() -> {
                errorLabel.setVisible(false);
                window.setOnCloseRequest(prev);
                buttonOK.setDisable(false);
            });
        }).start();
    }
}

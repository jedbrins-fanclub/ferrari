package dk.eamv.ferrari;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FormFactory {
    /*
     * Lavet af: Christian & Stefan
     */

    private static GridPane getForm(String... input) {
        int column = 0;
        int row = 0;
        GridPane gridPane = new GridPane();
        gridPane.setVgap(25);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);
        for (String i : input) {
            VBox vBox = new VBox();
            Label heading = new Label(i);
            TextField textField = new TextField();
            textField.setPromptText(i);
            vBox.getChildren().addAll(heading, textField);
            if (column > 2) {
                column = 0;
                row++;
            }
            gridPane.add(vBox, column, row);
            column++;
        }
        return gridPane;
    }

    public static GridPane createCustomerForm() {
        return getForm("Fornavn", "Efternavn", "Årstal", "By", "Adresse", "Vejnavn", "Postnummer");
    }

    public static GridPane createCarForm() {
        return getForm("Stelnummer", "Model", "Årgang");
    }

    public static GridPane createLoanForm() {
        return getForm();
    }
}

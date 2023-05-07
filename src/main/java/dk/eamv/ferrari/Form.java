package dk.eamv.ferrari;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Form {
    /*
     * Tekstfelter
     * I en GridPane
     * Overskrift
     * 
     * Parameter af String ind,
     * Stringen => Lav et tekstfelt => sæt promptteksten til Stringen
     * Stringen => Lav et label => sæt label til Stringen
     * Put dem i en VBox.
     * 
     * Gentag for alle vores argumenter.
     * 
     * 3 bred => gå en linie ned
     * 
     * returner gridpanen
     */

    public static GridPane getForm(String... input) {
        int column = 0;
        int row = 0;
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(200, 200, 200, 200));
        gridPane.setVgap(25);
        gridPane.setHgap(50);
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
}

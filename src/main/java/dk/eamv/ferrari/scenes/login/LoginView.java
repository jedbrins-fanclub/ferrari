package dk.eamv.ferrari.scenes.login;

import dk.eamv.ferrari.resources.SVGResources;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

// Made by: Christian
// Modified by: Mikkel (CSS og små visuelle ændringer)
public class LoginView {
    private static final TextField usernameField = new TextField();
    private static final PasswordField passwordField = new PasswordField();
    private static final Label errorLabel = new Label("Login ikke fundet");

    public static StackPane getScene() {
        StackPane scene = new StackPane();
        scene.setMaxWidth(Double.MAX_VALUE);

        Rectangle background = getBackground();
        background.widthProperty().bind(scene.widthProperty());
        background.heightProperty().bind(scene.heightProperty());

        scene.getChildren().addAll(
            background,
            getRepeatingLogo(),
            getLoginBox()
        );

        errorLabel.setVisible(false);
        return scene;
    }

    private static VBox getLoginBox() {
        VBox vbox = new VBox();
        vbox.getStyleClass().addAll("login-box", "drop-shadow-effect");
        
        Label loginLabel = new Label("LOGIN");
        loginLabel.getStyleClass().add("login-header");

        HBox hbox = new HBox(loginLabel);
        hbox.setPrefHeight(50);
        hbox.setAlignment(Pos.TOP_CENTER);

        Button loginButton = new Button("LOGIN");
        loginButton.setOnMouseClicked(e -> LoginController.authenticate());

        errorLabel.getStyleClass().setAll(".login-error");

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox, errorLabel, getUsernameBox(), getPasswordBox(), loginButton);
        return vbox;
    }

    private static VBox getUsernameBox() {
        Label usernameLabel = new Label("Brugernavn");
        
        SVGPath usernameIcon = new SVGPath();
        usernameIcon.setContent(SVGResources.getUsernameIcon());
        usernameField.setPromptText("Indtast brugernavn");

        HBox usernameFieldBox = new HBox(usernameIcon, usernameField);
        VBox usernameBox = new VBox(usernameLabel, usernameFieldBox, new Separator(Orientation.HORIZONTAL));
        usernameBox.getStyleClass().add("login-field-box");

        return usernameBox;
    }
    
    private static VBox getPasswordBox() {
        Label passwordLabel = new Label("Password");

        SVGPath passwordIcon = new SVGPath();
        passwordIcon.setContent(SVGResources.getPasswordIcon());
        passwordField.setPromptText("Indtast password");

        HBox passwordFieldBox = new HBox(passwordIcon, passwordField);
        VBox passwordBox = new VBox(passwordLabel, passwordFieldBox, new Separator(Orientation.HORIZONTAL));
        passwordBox.getStyleClass().add("login-field-box");

        return passwordBox;
    }

    private static Rectangle getBackground() {
        Stop[] stops = new Stop[]{
            new Stop(0, Color.web("5A060D")),
            new Stop(1, Color.web("#EF1A2D"))
        };

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
        Rectangle background = new Rectangle();
        background.setFill(gradient);

        return background;
    }

    private static GridPane getRepeatingLogo() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.setHgap(100);
        grid.setVgap(100);

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                SVGPath logo = new SVGPath();
                logo.setContent(SVGResources.getLogoHorse());
                logo.getStyleClass().add("login-logo-horse");
                grid.add(logo, col, row);
            }
        }

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(33);
        grid.getColumnConstraints().addAll(column, column);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(33);
        grid.getRowConstraints().addAll(row, row);

        return grid;
    }

    protected static String getUsernameInput() {
        return usernameField.getText();
    }

    protected static String getPasswordInput() {
        return passwordField.getText();
    }

    protected static Label getErrorLabel() {
        return errorLabel;
    }
}
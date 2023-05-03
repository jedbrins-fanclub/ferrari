package dk.eamv.ferrari.login;

import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.utils.Align;
import dk.eamv.ferrari.utils.RoundCorners;
import dk.eamv.ferrari.utils.ScreenBounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Lavet af: Christian 
 * Tjekket af:
 * Modificeret af: Mikkel (CSS, små visuelle ændringer)
 */
public class LoginView {
    private static TextField usernameTextField;
    private static PasswordField passwordPasswordField;
    private static final Button loginButton = new Button("LOGIN");
    private static Label forgottenPassword;

    public static AnchorPane getScene() {
        //Background
        Stop[] stops = new Stop[] { new Stop(0, Color.web("5A060D")), new Stop(1, Color.web("#EF1A2D")) };
        LinearGradient linearGradient = new LinearGradient(200, 200, ScreenBounds.getWidth() - 200, ScreenBounds.getHeight() - 200, false, CycleMethod.REFLECT, stops);
        Rectangle background = new Rectangle(ScreenBounds.getWidth(), ScreenBounds.getHeight());
        background.setFill(linearGradient);

        AnchorPane scene = new AnchorPane(background);

        //Loginbox
        Rectangle loginBox = new Rectangle(500, ScreenBounds.getHeight() - 350, Color.web("#D9D9D9"));
        Align.screenCenter(loginBox);
        loginBox.setLayoutY(150);
        RoundCorners.round(loginBox, loginBox.getHeight() * 0.05);
        DropShadow dropShadow = new DropShadow(25, -1, 1, Color.web("#555555"));
        loginBox.setEffect(dropShadow);

        //Loginbox content
        AnchorPane loginBoxContent = new AnchorPane();
        loginBoxContent.setMinWidth(loginBox.getWidth());
        loginBoxContent.setMinHeight(loginBox.getHeight());
        loginBoxContent.setLayoutX(loginBox.getLayoutX());
        loginBoxContent.setLayoutY(loginBoxContent.getLayoutY());

        //Login button
        loginButton.setLayoutY(750);
        loginButton.setLayoutX(50);
        loginButton.setOnAction(e -> LoginController.authenticate());

        //Fields
        Label loginHeader = new Label("LOGIN");
        loginHeader.getStyleClass().add("login-header");
        loginHeader.setLayoutY(175);

        VBox username = new VBox(10);
        username.setPadding(new Insets(225, 0, 0, 0));
        Label usernameLabel = new Label("Brugernavn");
        HBox usernameField = new HBox();
        SVGPath usernameIcon = new SVGPath();
        usernameIcon.setContent(SVGResources.getUsernameIcon());
        usernameTextField = new TextField();
        usernameTextField.setFocusTraversable(false);
        usernameTextField.setPromptText("Indtast brugernavn");
        usernameField.getChildren().addAll(usernameIcon, usernameTextField);
        Line usernameLine = new Line();
        usernameLine.setEndX(400);
        username.getChildren().addAll(usernameLabel, usernameField, usernameLine);

        VBox password = new VBox(10);
        Label passwordLabel = new Label("Password");
        HBox passwordField = new HBox();
        SVGPath passwordIcon = new SVGPath();
        passwordIcon.setContent(SVGResources.getPasswordIcon());
        passwordPasswordField = new PasswordField();
        passwordPasswordField.setFocusTraversable(false);
        passwordPasswordField.setPromptText("Indtast password");
        passwordField.getChildren().addAll(passwordIcon, passwordPasswordField);
        Line passwordLine = new Line();
        passwordLine.setEndX(400);
        forgottenPassword = new Label("Glemt kodeord?");
        forgottenPassword.getStyleClass().add("login-forgotten-password");
        password.getChildren().addAll(passwordLabel, passwordField, passwordLine, forgottenPassword);

        VBox fields = new VBox(username, password);
        Align.center(loginBox, fields, passwordLine);
        fields.setSpacing(50);
        fields.setLayoutY(150);

        loginBoxContent.getChildren().addAll(loginButton, loginHeader, fields);

        scene.getChildren().addAll(getLogos(), loginBox, loginBoxContent);
        scene.getStyleClass().add("login");

        return scene;
    }
    
    private static VBox getLogos() {
        VBox logos = new VBox(40);
        logos.getChildren().addAll(
            getFullLine(),
            getNotFullLine(),
            getFullLine(),
            getNotFullLine()
        );
        return logos;
    }
    
    private static HBox getFullLine() {
        HBox fullLine = new HBox(190);
        for (int i = 0; i < 6; i++) {
            fullLine.getChildren().add(getLogo());
        }

        return fullLine;
    }

    private static HBox getNotFullLine() {
        HBox notFullLine = new HBox(190);
        notFullLine.setPadding(new Insets(0, 190, 0, 190));
        for (int i = 0; i < 5; i++) {
            notFullLine.getChildren().add(getLogo());
        }

        return notFullLine;
    }

    private static SVGPath getLogo() {
        SVGPath logo = new SVGPath();
        logo.setContent(SVGResources.getLogoHorse());
        logo.getStyleClass().add("login-logo-horse");

        return logo;
    }

    public String getUsernameInput() {
        return usernameTextField.getText();
    }

    public String getPasswordInput() {
        return passwordPasswordField.getText();
    }

    public Button getLoginButton() {
        return loginButton;
    }
}
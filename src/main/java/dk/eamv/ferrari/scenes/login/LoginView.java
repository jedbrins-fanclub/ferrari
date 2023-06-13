package dk.eamv.ferrari.scenes.login;

import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.utils.Align;
import dk.eamv.ferrari.utils.RoundCorners;
import dk.eamv.ferrari.utils.ScreenBounds;
import dk.eamv.ferrari.utils.ToggleVisible;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
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

// Made by: Christian
// Modified by: Mikkel (CSS og små visuelle ændringer)
public class LoginView implements ToggleVisible {
    private static TextField usernameTextField;
    private static PasswordField passwordPasswordField;
    private static final Button loginButton = new Button("LOGIN");
    private static Label wrongLogin;

    public static StackPane getScene() {
        StackPane scene = new StackPane();
        scene.setMaxWidth(Double.MAX_VALUE);

        Rectangle background = getBackground();
        background.widthProperty().bind(scene.widthProperty());
        background.heightProperty().bind(scene.heightProperty());

        scene.getChildren().addAll(
            background,
            getLoginBox()
        );

        return scene;
    }

    public static VBox getLoginBox() {
        VBox vbox = new VBox();
        vbox.getStyleClass().add("login-box");
        
        Label login = new Label("LOGIN");
        login.getStyleClass().add("login-header");

        HBox hbox = new HBox(login);
        hbox.setPrefHeight(50);
        hbox.setAlignment(Pos.TOP_CENTER);

        Button loginButton = new Button("LOGIN");

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox, getUsernameBox(), getPasswordBox(), loginButton);
        return vbox;
    }

    private static VBox getUsernameBox() {
        Label usernameLabel = new Label("Brugernavn");
        
        SVGPath usernameIcon = new SVGPath();
        usernameIcon.setContent(SVGResources.getUsernameIcon());
        TextField usernameField = new TextField();
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
        PasswordField passwordField = new PasswordField();
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
}
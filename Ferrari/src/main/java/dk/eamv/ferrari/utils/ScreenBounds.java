package dk.eamv.ferrari.utils;

import javafx.stage.Screen;

public class ScreenBounds {
    public static double getWidth() {
        return Screen.getPrimary().getVisualBounds().getWidth();
    }

    public static double getHeight() {
        return Screen.getPrimary().getVisualBounds().getHeight();
    }
}
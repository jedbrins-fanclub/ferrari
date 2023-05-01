package utils;

import javafx.scene.shape.Rectangle;

public class RoundCorners {
    public static void round(Rectangle rectangle, int amount) {
        rectangle.setArcWidth(amount);
        rectangle.setArcHeight(amount);
    }

    public static void round(Rectangle rectangle, double amount) {
        rectangle.setArcWidth(amount);
        rectangle.setArcHeight(amount);
    }
}
package dk.eamv.ferrari.utils;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Align {
    //2 nodes X axis.
    public static void left(Node parent, Node child) {
        child.setLayoutX(0);
    }

    public static void left(Node parent, Region moveThis, Node childWidth) {
        moveThis.setLayoutX(0);
    }

    public static void center(Node parent, Node child) {
        child.setLayoutX((getWidth(parent) - getWidth(child)) / 2);
    }

    public static void center(Node parent, Region moveThis, Node childWidth) {
        moveThis.setLayoutX((getWidth(parent) - getWidth(childWidth)) / 2);
    }

    public static void right(Node parent, Node child) {
        child.setLayoutX(getWidth(parent) - getWidth(child));
    }

    public static void right(Node parent, Region moveThis, Node childWidth) {
        moveThis.setLayoutX(getWidth(parent) - getWidth(childWidth));
    }

    //2 nodes Y axis.
    public static void top(Node parent, Node child) {
        child.setLayoutY(0);
    }

    public static void top(Node parent, Region moveThis, Node childHeight) {
        moveThis.setLayoutY(0);
    }

    public static void middle(Node parent, Node child) {
        child.setLayoutY((getHeight(parent) - getHeight(child)) / 2);
    }

    public static void middle(Node parent, Region moveThis, Node childHeight) {
        moveThis.setLayoutY((getHeight(parent) - getHeight(childHeight)) / 2);
    }

    public static void bottom(Node parent, Node child) {
        child.setLayoutY(getHeight(parent) - getHeight(child));
    }

    public static void bottom(Node parent, Region moveThis, Node childHeight) {
        moveThis.setLayoutY(getHeight(parent) - getHeight(childHeight));
    }

    //1 node, screen size X axis.
    public static void screenLeft(Node child) {
        child.setLayoutX(0);
    }

    public static void screenCenter(Node child) {
        child.setLayoutX(ScreenBounds.getWidth() / 2 - getWidth(child) / 2);
    }

    public static void screenRight(Node child) {
        child.setLayoutX(ScreenBounds.getWidth() - getWidth(child));
    }

    //1 node, screen size Y axis.
    public static void screenTop(Node child) {
        child.setLayoutY(0);
    }

    public static void screenMiddle(Node child) {
        child.setLayoutY(ScreenBounds.getHeight() / 2 - getHeight(child) / 2);
    }

    public static void screenBottom(Node child) {
        child.setLayoutY(ScreenBounds.getHeight() - getHeight(child));
    }
    
    //Service methods to get X & Y of different types of nodes.
    private static double getWidth(Node node) {
        if (node instanceof Circle) {
            return ((Circle) node).getRadius();
        } else if (node instanceof Line) {
            Line line = ((Line) node);
            return line.getEndX() - line.getStartX();
        } else if (node instanceof Rectangle) {
            return ((Rectangle) node).getWidth();
        } else if (node instanceof Region) {
            return ((Region) node).getWidth();
        } else {
            System.out.println("Didnt match a node to get the width of. Returning -1");
            return -1;
        }
    }

    private static double getHeight(Node node) {
        if (node instanceof Circle) {
            return ((Circle) node).getRadius();
        } else if (node instanceof Line) {
            Line line = ((Line) node);
            return line.getEndY() - line.getStartY();
        } else if (node instanceof Rectangle) {
            return ((Rectangle) node).getHeight();
        } else if (node instanceof Region) {
            return ((Region) node).getHeight();
        } else {
            System.out.println("Didnt match a node to get the width of. Returning -1");
            return -1;
        }
    }
}
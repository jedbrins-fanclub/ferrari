package dk.eamv.ferrari.utils;

import javafx.scene.Node;

public interface ToggleVisible {
    public static void toggleVisible(Node toggleThis) {
        toggleThis.setVisible(!toggleThis.isVisible());
    }
}

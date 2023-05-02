package dk.eamv.ferrari.sidebar;

import java.util.Map;

import javafx.scene.control.ToggleButton;

public final class SidebarMediator {
    //TODO: make protected when done testing
    //TODO: add eventlisteners for the buttons.
    public static void setButtonActive(ToggleButton toggleButton) {
        for (Map.Entry<ToggleButton, String> entry : SidebarView.getSidebarView().getButtonsWithIcons().entrySet()) {
            entry.getKey().getStyleClass().add("ToggleButtonSelected");

        }

        toggleButton.getStyleClass().add("");
    }
    
}

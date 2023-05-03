package dk.eamv.ferrari.sidebar;

import java.util.Map;

import javafx.scene.control.ToggleButton;

public final class SidebarMediator {
    //TODO: make protected when done testing
    //TODO: add eventlisteners for the buttons.
    //TODO: Somehow make getStyleClass() work, instead of setStyle.
    public static void setButtonActive(ToggleButton toggleButton) {
        
        for (Map.Entry<ToggleButton, String> entry : SidebarView.getSidebarView().getButtonsWithIcons().entrySet()) {
            entry.getKey().setStyle("-fx-background-color: #1F1F1F");
            //entry.getKey().getStyleClass().add("ToggleButton");
        }

        toggleButton.setStyle("-fx-background-color: #F50000");
        //toggleButton.getStyleClass().add("ToggleButtonSelected");
    }   
}

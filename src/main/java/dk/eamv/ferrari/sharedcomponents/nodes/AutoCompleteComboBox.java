package dk.eamv.ferrari.sharedcomponents.nodes;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;

// Made by Benjamin and Christian
public class AutoCompleteComboBox<E> extends ComboBox<String> {
    private HashMap<String, E> elements = new HashMap<String, E>();

    /**
     * Create a new AutoCompleteCombobBox
     * @param entities ObservableList of the entities to show
     */
    public AutoCompleteComboBox(ObservableList<E> entities) {
        setEditable(true);
        for (E entity : entities) {
            elements.put(entity.toString(), entity);
        }

        FilteredList<String> filteredItems = new FilteredList<String>(FXCollections.observableArrayList(elements.keySet()), p -> true);
        getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final String selected = getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(newValue)) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        return item.toUpperCase().contains(newValue.toUpperCase());
                    });
                }
            });
        });

        setItems(filteredItems);
        getStyleClass().add("combobox");
    }

    /**
     * Get the selected item from the AutoCompleteComboBox
     * @return E generic type
     */
    public E getSelectedItem() {
        return elements.get(getSelectionModel().getSelectedItem());
    }

    /**
     * Check if no value has been selected
     * @return boolean true if nothing is selected
     */
    public boolean isEmpty() {
        return elements.get(getSelectionModel().getSelectedItem()) == null;
    }
}

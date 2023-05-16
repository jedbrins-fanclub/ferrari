package dk.eamv.ferrari.sharedcomponents.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AutoCompleteComboBox<E> extends ComboBox<String> {
    private HashMap<String, E> map = new HashMap<String, E>();

    public AutoCompleteComboBox(ObservableList<E> content) {
        setEditable(true);

        ArrayList<String> items = new ArrayList<String>();
        for (E element : content) {
            map.put(element.toString(), element);
            items.add(element.toString());
        }

        FilteredList<String> filteredItems = new FilteredList<String>(FXCollections.observableArrayList(items), p -> true);
        getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = getEditor();
            final String selected = getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toString().toUpperCase().contains(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        setItems(filteredItems);
        setStyle("-fx-font-family: \"COURIER NEW\";");
    }

    public E getSelectedItem() {
        return map.get(getSelectionModel().getSelectedItem());
    }
}

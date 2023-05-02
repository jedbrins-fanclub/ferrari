package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.scene.control.TextField;
import java.util.List;
import java.util.function.Function;

public class FilterTextField<T> extends TextField {
    private final FilteredTableView<T> filteredTableView;
    private final List<Function<T, String>> propertyValueGetters;

    public FilterTextField(FilteredTableView<T> filteredTableView, List<Function<T, String>> propertyValueGetters) {
        this.filteredTableView = filteredTableView;
        this.propertyValueGetters = propertyValueGetters;
        setPromptText("Filter");
        setupFiltering();
    }

    private void setupFiltering() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            String filterText = newValue == null ? "" : newValue.toLowerCase(); // Convert filter text to lowercase only once
            filteredTableView.setFilter(item -> {
                if (filterText.isEmpty()) {
                    return true;
                }

                for (Function<T, String> propertyValueGetter : propertyValueGetters) {
                    String propertyValue = propertyValueGetter.apply(item);
                    if (propertyValue != null && propertyValue.toLowerCase().contains(filterText)) { // Avoid NullPointerException
                        return true;
                    }
                }
                return false;
            });
        });
    }
}

package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Lavet af: Mikkel
 */
public class FilteredTableView<T> extends TableView<T> {
    // TableView takes generic type T in order to be able to use different types (Car, Customer etc)

    private final FilteredList<T> filteredData;
    private final SortedList<T> sortedData;

    // When class is instantiated, it takes the original data that is to be filtered (list of Car objects etc)
    public FilteredTableView(ObservableList<T> data) {
        // Data will initially be shown like this (everything)
        filteredData = new FilteredList<>(data, predicate -> true);
        sortedData = new SortedList<>(filteredData);

        // Binds data from list to the TableView - If user sorts in the tableview, list order is updated
        sortedData.comparatorProperty().bind(comparatorProperty());
        setItems(sortedData);

        // Has some problems regarding the colors
        // getStylesheets().add(Objects.requireNonNull(getClass().getResource("/tableview.css")).toExternalForm());
    }

    // This is explained in detail in the FieldTextField class
    public void setFilter(Predicate<T> predicate) {
        filteredData.setPredicate(predicate);
    }
}

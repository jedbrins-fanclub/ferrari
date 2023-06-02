package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by: Mikkel
 * <p>
 * Class to add functionality to the standard JavaFX TableView class
 */
public class FilteredTable<T> extends TableView<T> {
    // TableView takes generic type T in order to be able to use different types (Car, Customer etc)

    private final FilteredList<T> filteredData;

    /**
     * Adds the filtering functionality to the TableViews
     * @param data the original data that is to be filtered (list of Car objects etc)
     */
    public FilteredTable(ObservableList<T> data) {
        // Data will initially be shown like this (everything)
        filteredData = new FilteredList<>(data, predicate -> true);
        SortedList<T> sortedData = new SortedList<>(filteredData);

        // Binds data from list to the TableView - If user sorts in the tableview, list order is updated
        sortedData.comparatorProperty().bind(comparatorProperty());
        setItems(sortedData);

        // Sets stylesheet for tables
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/tableview.css")).toExternalForm());
    }

    /**
     * Method to decide whether to update the content of the TableView according to the TextFields content
     * @param predicate method that returns true or false
     * @see FilterTextField
     */
    public void setFilter(Predicate<T> predicate) {
        filteredData.setPredicate(predicate);
    }
}

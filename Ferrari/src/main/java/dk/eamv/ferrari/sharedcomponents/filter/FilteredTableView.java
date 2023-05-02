package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

import java.util.function.Predicate;

public class FilteredTableView<T> extends TableView<T> {
    // TableView takes generic type T in order to be able to use different types (Car, Customer etc)

    private final FilteredList<T> filteredData;
    private final SortedList<T> sortedData;

    // When class is instantiated, it takes the original data that is to be filtered (list of Car objects etc)
    public FilteredTableView(ObservableList<T> data) {
        // Data will initially be shown like this (everything)
        filteredData = new FilteredList<>(data, p -> true);
        sortedData = new SortedList<>(filteredData);

        // Binds data from list to the TableView - If user sorts in the tableview, list order is updated
        sortedData.comparatorProperty().bind(comparatorProperty());
        setItems(sortedData);
    }

    public void setFilter(Predicate<T> predicate) {
        filteredData.setPredicate(predicate);
    }
}
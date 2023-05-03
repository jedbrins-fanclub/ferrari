package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Lavet af: Mikkel
 */
public class FilteredTableViewBuilder<T> {

    private ObservableList<T> data;
    private List<Pair<String, Function<T, Object>>> columnInfo;
    public FilteredTableViewBuilder() {
        columnInfo = new ArrayList<>();
    }

    // Adds the list of objects to the tableview (could be any object like Car, Customer etc)
    public FilteredTableViewBuilder<T> withData(ObservableList<T> data) {
        this.data = data;
        return this;
    }

    public FilteredTableViewBuilder<T> withColumn(String columnName, Function<T, Object> propertyValueGetter) {

        // Every time this method is called, a specific column and its list of Value Getter methods is added to the list
        columnInfo.add(new Pair<>(columnName, propertyValueGetter));
        return this;
    }

    public FilteredTableView<T> build() {
        FilteredTableView<T> tableView = new FilteredTableView<>(data);
        for (Pair<String, Function<T, Object>> info : columnInfo) {
            TableColumn<T, String> column = new TableColumn<>(info.getKey());
            column.setCellValueFactory(cellData -> new SimpleStringProperty(info.getValue().apply(cellData.getValue()).toString()));
            tableView.getColumns().add(column);
        }
        return tableView;
    }

    public FilterTextField<T> withFilterTextField(FilteredTableView<T> tableView) {
        return new FilterTextField<>(tableView, columnInfo.stream().map(Pair::getValue).collect(Collectors.toList()));
    }
}

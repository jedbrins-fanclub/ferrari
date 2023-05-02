package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilteredTableViewBuilder<T> {

    private ObservableList<T> data;
    private List<Pair<String, Function<T, String>>> columnInfo;
    public FilteredTableViewBuilder() {
        columnInfo = new ArrayList<>();
    }

    // Adds the list of objects to the tableview (could be any object like Car, Customer etc)
    public FilteredTableViewBuilder<T> withData(ObservableList<T> data) {
        this.data = data;
        return this;
    }

    // Adds
    public FilteredTableViewBuilder<T> withColumn(String columnName, Function<T, String> propertyValueGetter) {
        columnInfo.add(new Pair<>(columnName, propertyValueGetter));
        return this;
    }

    public FilteredTableView<T> build() {
        FilteredTableView<T> tableView = new FilteredTableView<>(data);
        for (Pair<String, Function<T, String>> info : columnInfo) {
            TableColumn<T, String> column = new TableColumn<>(info.getKey());
            column.setCellValueFactory(cellData -> new SimpleStringProperty(info.getValue().apply(cellData.getValue())));
            tableView.getColumns().add(column);
        }
        return tableView;
    }

    public FilterTextField<T> withFilterTextField(FilteredTableView<T> tableView) {
        return new FilterTextField<>(tableView, columnInfo.stream().map(Pair::getValue).collect(Collectors.toList()));
    }
}

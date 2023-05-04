package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Lavet af: Mikkel
 */
public class FilterBuilder<T> {

    private ObservableList<T> data;
    private List<Pair<String, Function<T, Object>>> columnInfo;
    private List<TableColumn<T, ?>> progressColumns;
    public FilterBuilder() {
        columnInfo = new ArrayList<>();
        progressColumns = new ArrayList<>();
    }

    // Adds the list of objects to the tableview (could be any object like Car, Customer etc)
    public FilterBuilder<T> withData(ObservableList<T> data) {
        this.data = data;
        return this;
    }

    public FilterBuilder<T> withColumn(String columnName, Function<T, Object> propertyValueGetter) {

        // Every time this method is called, a specific column and its list of Value Getter methods is added to the list
        columnInfo.add(new Pair<>(columnName, propertyValueGetter));
        return this;
    }

    public FilterBuilder<T> withProgressColumn(String columnName, Function<T, Double> startValueGetter, Function<T, Double> endValueGetter, Function<T, Double> currentValueGetter) {
        TableColumn<T, Double> progressColumn = new TableColumn<>(columnName);
        progressColumn.setCellValueFactory(cellData -> {
            double start = startValueGetter.apply(cellData.getValue());
            double end = endValueGetter.apply(cellData.getValue());
            double current = currentValueGetter.apply(cellData.getValue());
            double progress = (current - start) / (end - start);
            return new ReadOnlyObjectWrapper<>(progress);
        });

        progressColumn.setCellFactory(column -> new TableCell<T, Double>() {
            private final ProgressBar progressBar = new ProgressBar();

            @Override
            protected void updateItem(Double progress, boolean empty) {
                super.updateItem(progress, empty);

                if (empty || progress == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(progress);
                    setGraphic(progressBar);
                }
            }
        });

        // Add the progress column to the separate list
        progressColumns.add(progressColumn);
        return this;
    }

    public FilterTextField<T> withFilterTextField(FilteredTable<T> tableView) {
        return new FilterTextField<>(tableView, columnInfo.stream().map(Pair::getValue).collect(Collectors.toList()));
    }

    public Button withControlButton(String buttonText, FilteredTable<T> tableView) {
        Button button = new Button(buttonText);
        button.setDisable(true); // Initially set the button as disabled

        // Enable the button when a row is selected
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            button.setDisable(newValue == null);
        });

        return button;
    }

    public FilteredTable<T> build() {
        FilteredTable<T> tableView = new FilteredTable<>(data);
        for (Pair<String, Function<T, Object>> info : columnInfo) {
            TableColumn<T, String> column = new TableColumn<>(info.getKey());
            column.setCellValueFactory(cellData -> new SimpleStringProperty(info.getValue().apply(cellData.getValue()).toString()));
            tableView.getColumns().add(column);
        }

        // Add progress columns to the TableView
        tableView.getColumns().addAll(progressColumns);

        return tableView;
    }
}

package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Lavet af: Mikkel
 */

/**
 * @param <T> The type of the elements contained in the table row. This is generic to allow the FilteredTableBuilder to
 *            work with all our types, so the creation of TableViews with filter functionality is not repeated.
 *            When this class is instantiated, it is necessary to supply the desired type.
 */

public class FilteredTableBuilder<T> implements FilteredTableBuilderInfo<T> {
    private ObservableList<T> data;
    /**
     * <p> {@code String} - the name of the column.</p>
     * <p> {@code Function<T, Object>} - a lambda or method reference that defines how to extract the value for
     * the column from an object of type {@code <T>}. </p>
     * <p>The functions being passed here will most often be getters from the entity class of the type being assigned
     * when the {@link #FilteredTableBuilder} is instantiated.</p>
     * <p>For more details go to: {@link #withColumn(String, Function)}</p>
     */
    private final List<Pair<String, Function<T, Object>>> columnInfo;
    private final List<TableColumn<T, ?>> progressColumns;
    private final List<TableColumn<T, ?>> buttonColumns;
    private FilteredTable<T> filteredTable;

    /**
     * Constructs a new {@code FilteredTableBuilder} instance with empty lists of column information,
     * progress columns, and button columns.
     * <p>The {@link #columnInfo} list will contain information about each column in the table, such as
     * its header text and data type.
     * <p>The {@link #progressColumns} list will contain the indices of columns that
     * should display a progress bar.
     * <p>The {@link #buttonColumns} list will contain the indices of columns that
     * should display buttons.
     * <p>These columns will be added together in the {@link #build()} method.
     */
    public FilteredTableBuilder() {
        columnInfo = new ArrayList<>();
        progressColumns = new ArrayList<>();
        buttonColumns = new ArrayList<>();
    }

    /**
     * Method for adding data to the builder during its configuration.
     * @param data the ObservableList of objects to be displayed in the FilteredTable
     * @return Itself in order to allow for method chaining when configuring the instance of the builder
     */
    public FilteredTableBuilder<T> withData(ObservableList<T> data) {
        this.data = data;
        return this;
    }

    /**
     *
     * @param columnName name of the column in the FilteredTable
     * @param propertyValueGetter a lambda or method reference to get the desired content from the objects entity class
     * @return Itself in order to allow for method chaining when configuring the instance of the builder
     */
    public FilteredTableBuilder<T> withColumn(String columnName, Function<T, Object> propertyValueGetter) {

        // Every time this method is called, a specific column and its list of Value Getter methods is added to the list
        columnInfo.add(new Pair<>(columnName, propertyValueGetter));
        return this;
    }

    public FilteredTableBuilder<T> withProgressColumn(String columnName, Function<T, Double> startValueGetter, Function<T, Double> endValueGetter, Function<T, Double> currentValueGetter) {
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

    public FilteredTableBuilder<T> withButtonColumn(String columnName, String buttonName, Consumer<T> onButtonClick) {
        TableColumn<T, Void> buttonColumn = new TableColumn<>(columnName);
        buttonColumn.setCellFactory(param -> new ButtonTableCell<>(buttonName, onButtonClick));
        buttonColumns.add(buttonColumn);
        return this;
    }

    public FilteredTable<T> build() {
        filteredTable = new FilteredTable<>(data);
        for (Pair<String, Function<T, Object>> info : columnInfo) {
            TableColumn<T, Object> column = new TableColumn<>(info.getKey());
            column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(info.getValue().apply(cellData.getValue())));
            filteredTable.getColumns().add(column);
        }

        // Add progress columns to the TableView
        filteredTable.getColumns().addAll(progressColumns);
        filteredTable.getColumns().addAll(buttonColumns);

        return filteredTable;
    }

    @Override
    public FilteredTable<T> getFilteredTable() {
        return filteredTable;
    }

    @Override
    public List<Function<T, Object>> getPropertyValueGetters() {
        return columnInfo.stream().map(Pair::getValue).collect(Collectors.toList());
    }
}

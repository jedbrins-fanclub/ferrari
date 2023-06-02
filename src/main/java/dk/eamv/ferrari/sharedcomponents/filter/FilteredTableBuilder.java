package dk.eamv.ferrari.sharedcomponents.filter;

import dk.eamv.ferrari.scenes.loan.LoanStatus;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

// Made by: Mikkel

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
    private final List<TableColumn<T, ?>> statusColumns;
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
        statusColumns = new ArrayList<>();
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

    public FilteredTableBuilder<T> withStatusColumn(String columnName, Function<T, LoanStatus> loanStatusGetter) {
        TableColumn<T, LoanStatus> statusColumn = new TableColumn<>(columnName);
        statusColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(loanStatusGetter.apply(cellData.getValue())));

        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LoanStatus item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    // Update the text and colour depending on LoanState enum
                    setText(item.getDisplayName());
                    switch (item.getState()) {
                        case PENDING -> setTextFill(Color.ORANGE);
                        case APPROVED -> setTextFill(Color.GREEN);
                        case REJECTED -> setTextFill(Color.RED);
                        case ACTIVE -> setTextFill(Color.web("#323232"));
                        case COMPLETED -> setTextFill(Color.GRAY);
                        default -> setTextFill(Color.BLACK);
                    }
                }
            }
        });

        statusColumns.add(statusColumn);
        return this;
    }

    public FilteredTableBuilder<T> withProgressColumn(String columnName, Function<T, Date> startDateGetter, Function<T, Date> endDateGetter) {
        TableColumn<T, Double> progressColumn = new TableColumn<>(columnName);
        progressColumn.setCellValueFactory(cellData -> {
            long start = startDateGetter.apply(cellData.getValue()).getTime();
            long end = endDateGetter.apply(cellData.getValue()).getTime();
            long current = new Date().getTime();
            double progress = (double) (current - start) / (end - start);

            /* We make sure the progress is not below 0 or above 1
             * The main problem it is solving is the progress being negative, if: current date < start date
             * In this case the progress cannot be negative, and it will set to and shown as 0 instead.
             * If this is not normalized, the progressbar will display a loading animation.
             * Whether it should show 0 progress or a loading bar seems a design choice.
             */
            progress = Math.max(0, Math.min(1, progress));


            return new ReadOnlyObjectWrapper<>(progress);
        });

        progressColumn.setCellFactory(column -> new TableCell<>() {
            private final ProgressBar progressBar = new ProgressBar();

            @Override
            protected void updateItem(Double progress, boolean empty) {
                super.updateItem(progress, empty);

                if (empty || progress == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(progress);
                    if (progress == 1) {
                        progressBar.getStyleClass().add("progress-bar-finished");
                    }
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

    // Method is overloaded to allow for buttons with text and with an icon
    public FilteredTableBuilder<T> withButtonColumn(String svg, Consumer<T> onButtonClick) {
        TableColumn<T, Void> buttonColumn = new TableColumn<>();
        buttonColumn.setCellFactory(param -> new ButtonTableCell<>(onButtonClick, svg));
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

        // Adding columns like below hardcodes the order of the columns in this class which is not optimal
        // Add other columns to the view
        filteredTable.getColumns().addAll(statusColumns);
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

package dk.eamv.ferrari.sharedcomponents.filter;

import java.util.function.Function;

public class ColumnConfig<T> {
    private final String columnName;
    private final Function<T, String> propertyValueGetter;

    public ColumnConfig(String columnName, Function<T, String> propertyValueGetter) {
        this.columnName = columnName;
        this.propertyValueGetter = propertyValueGetter;
    }

    public String getColumnName() {
        return columnName;
    }

    public Function<T, String> getPropertyValueGetter() {
        return propertyValueGetter;
    }
}

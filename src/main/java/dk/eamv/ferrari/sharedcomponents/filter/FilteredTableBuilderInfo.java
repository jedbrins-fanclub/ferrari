package dk.eamv.ferrari.sharedcomponents.filter;

import java.util.List;
import java.util.function.Function;

public interface FilteredTableBuilderInfo<T> {
    FilteredTable<T> getFilteredTable();
    List<Function<T, Object>> getPropertyValueGetters();
}

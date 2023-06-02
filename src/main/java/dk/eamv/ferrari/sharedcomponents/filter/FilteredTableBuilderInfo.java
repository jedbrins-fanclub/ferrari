package dk.eamv.ferrari.sharedcomponents.filter;

import java.util.List;
import java.util.function.Function;

/**
 * Created by: Mikkel
 * <p>
 * When it is necessary to pass information about the FilteredTable instance and its contents to create UI elements
 * that relate to it, this interface is used to limit access to only these methods.
 * The UI elements do not need to access other parts of the instance of FilteredTableBuilder, so
 * we only give access to what is necessary with this interface.
 * <p>
 * Previously the builder class took care of the creation of these UI elements (FilterTextField and ControlButton).
 * Since any given instance of these elements would obviously be coupled closely with an instance of a FilteredTable
 * it seemed to make sense to create them in the builder, where they had access to all the information they needed.
 * Although this was practical, it violated the single responsibility principle - the builder class was made to
 * build FilteredTables, not to create UI elements. With this approach, each class only takes on the
 * responsibility that is necessary and logical.
 */
public interface FilteredTableBuilderInfo<T> {
    FilteredTable<T> getFilteredTable();
    List<Function<T, Object>> getPropertyValueGetters();
}

package dk.eamv.ferrari.sharedcomponents.filter;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import java.util.function.Consumer;

/**
 * A custom table cell that contains a button.
 * Allows the withButtonColumn method the FilteredTableBuilder class to not also take on the responsibility of
 * creating the ButtonTableCall with an innerclass folliwing the single responsibility principle
 * @see FilteredTableBuilder
 *
 */
public class ButtonTableCell<T> extends TableCell<T, Void> {
    private final Button btn;

    /**
     * Creates a ButtonTableCell with the given button name and action.
     *
     * @param buttonName    the name to display on the button
     * @param onButtonClick the action to perform when the button is clicked
     */
    public ButtonTableCell(String buttonName, Consumer<T> onButtonClick) {
        btn = createButton(buttonName, onButtonClick);
    }

    /**
     * Creates a button with the given name and action.
     *
     * @param name   the name to display on the button
     * @param action the action to perform when the button is clicked
     * @return the created button
     */
    private Button createButton(String name, Consumer<T> action) {
        Button button = new Button(name);
        button.setOnAction(e -> handleButtonClick(action));
        return button;
    }

    /**
     * Handles the button click event and performs the given action.
     *
     * @param action the action to perform when the button is clicked
     */
    private void handleButtonClick(Consumer<T> action) {
        T item = getTableView().getItems().get(getIndex());
        action.accept(item);
    }

    /**
     * Updates the item in the cell and sets the graphic based on the cell's state.
     *
     * @param item  the item to update
     * @param empty true if the cell is empty, false otherwise
     */
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : btn);
    }
}

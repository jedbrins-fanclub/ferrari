package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Lavet af: Mikkel
 */

public class CarController {

    protected static FilteredTableBuilder<Car> filteredTableBuilder;
    private static ObservableList<Car> cars = FXCollections.observableArrayList(CarModel.readAll());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Car>()
                .withData(cars)
                .withColumn("Stelnummer", Car::getId)
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", Car::getYear)
                .withColumn("Pris (DKK)", Car::getPrice) //TODO: Decide how to display price (maybe store in Ks)
                .withIconButtonColumn(SVGResources.getEditIcon(), CarView::showEditCarDialog)
                .withIconButtonColumn(SVGResources.getDeleteIcon(), CarController::deleteCar);
    }

    protected static void createCar() {
        FormFactory.createCarFormDialogBox();
    }

    protected static void updateCar(Car car) {
        FormFactory.updateCarFormDialogBox(car);

        CarView.refreshTableView();
    }

    protected static void deleteCar(Car car) {
        CarModel.delete(car.getId());

        // When removing the car from the ObservableList, the TableView updates automatically
        cars.remove(car);
    }

    public static ObservableList<Car> getCars() {
        return cars;
    }
}

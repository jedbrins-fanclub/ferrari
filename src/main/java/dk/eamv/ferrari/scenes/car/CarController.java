package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;

/**
 * Lavet af: Mikkel
 */

public class CarController {

    protected static FilteredTableBuilder<Car> filteredTableBuilder;
    private static ObservableList<Car> cars = FXCollections.observableArrayList();

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Car>()
                .withData(fetchCars())
                .withColumn("Stelnummer", Car::getId)
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", Car::getYear)
                .withColumn("Pris", Car::getPrice); //TODO: Decide how to display price (maybe store in Ks)
    }

    protected static ObservableList<Car> fetchCars() {
        for (int i = 1; i <= 28; i++) {
            cars.add(CarModel.getFromID(i));
        }
        return cars;
    }

    protected static void updateCar(Car car) {
        System.out.println("Call method in CarModel update car with id: " + car.getId());

        CarView.refreshTableView();
    }

    protected static void deleteCar(Car car) {
        System.out.println("Call method in CarModel to delete car with id: " + car.getId());

        // When removing the car from the ObservableList, the TableView updates automatically
        cars.remove(car);
    }
}

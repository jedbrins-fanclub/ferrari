package dk.eamv.ferrari.scenes.car;

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
                .withData(fetchCars())
                .withColumn("Stelnummer", Car::getId)
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", Car::getYear)
                .withColumn("Pris", Car::getPrice) //TODO: Decide how to display price (maybe store in Ks)
                .withButtonColumn("", "Rediger", CarView::showEditCarDialog)
                .withButtonColumn("", "Slet", CarController::deleteCar)
                .withButtonColumn("...", "...", CarController::buttonIsClicked);
    }

    private static void buttonIsClicked(Car car) {
        System.out.println("Controller method is called with car: " + car);
    }

    protected static ObservableList<Car> fetchCars() {
        cars.addAll(0, CarModel.readAll());
        return cars;
    }

    //TODO: create car dialog calls this method
    protected static void createCar() {
        FormFactory.createCarFormDialogBox();
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

    public static ObservableList<Car> getCars() {
        return cars;
    }
}

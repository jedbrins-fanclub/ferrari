package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.sharedcomponents.filter.FilterBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarController {

    protected static FilterBuilder<Car> carBuilder;
    private static ObservableList<Car> cars = FXCollections.observableArrayList();

    protected static void initCarBuilder() {
        carBuilder = new FilterBuilder<Car>()
                .withData(fetchCars())
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", car -> Integer.toString(car.getYear()))
                .withColumn("Pris", car -> Double.toString(car.getPrice()));
    }

    protected static ObservableList<Car> fetchCars() {
        return cars; // Place method to retrieve all cars from database here
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

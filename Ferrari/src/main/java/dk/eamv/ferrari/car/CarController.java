package dk.eamv.ferrari.car;

import dk.eamv.ferrari.resources.TestData;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableViewBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarController {

    protected static FilteredTableViewBuilder<Car> carBuilder;
    private static final TestData testData = new TestData();
    private static ObservableList<Car> cars = FXCollections.observableArrayList(testData.getAllCars());

    protected static void initCarBuilder() {
        carBuilder = new FilteredTableViewBuilder<Car>()
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

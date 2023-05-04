package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.sharedcomponents.filter.FilterBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

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
        generateCars();
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

    public static void generateCars() {
        cars.add(new Car(1, "Ferrari F8 Tributo", 2022, 1650000.00));
        cars.add(new Car(2, "Ferrari 812 Superfast", 2021, 2000000.00));
        cars.add(new Car(3, "Ferrari Portofino M", 2023, 1450000.00));
        cars.add(new Car(4, "Ferrari SF90 Stradale", 2022, 3000000.00));
        cars.add(new Car(5, "Ferrari Roma", 2021, 1325000.00));
        cars.add(new Car(6, "Ferrari GTC4Lusso", 2020, 1800000.00));
        cars.add(new Car(7, "Ferrari 488 Pista", 2019, 2100000.00));
        cars.add(new Car(8, "Ferrari 458 Italia", 2014, 1300000.00));
        cars.add(new Car(9, "Ferrari 812 GTS", 2021, 2150000.00));
        cars.add(new Car(10, "Ferrari 488 GTB", 2018, 1550000.00));
        cars.add(new Car(11, "Ferrari California T", 2016, 1200000.00));
        cars.add(new Car(12, "Ferrari F12 Berlinetta", 2015, 1800000.00));
        cars.add(new Car(13, "Ferrari LaFerrari", 2014, 9500000.00));
        cars.add(new Car(14, "Ferrari 599 GTO", 2011, 3700000.00));
        cars.add(new Car(15, "Ferrari FF", 2012, 1250000.00));
        cars.add(new Car(16, "Ferrari F430 Scuderia", 2009, 1000000.00));
        cars.add(new Car(17, "Ferrari 612 Scaglietti", 2008, 750000.00));
        cars.add(new Car(18, "Ferrari Enzo", 2002, 12000000.00));
        cars.add(new Car(19, "Ferrari 360 Modena", 2001, 650000.00));
        cars.add(new Car(20, "Ferrari 575M Maranello", 2003, 850000.00));
        cars.add(new Car(21, "Ferrari 550 Barchetta", 2000, 1100000.00));
        cars.add(new Car(22, "Ferrari F50", 1995, 8000000.00));
        cars.add(new Car(23, "Ferrari 456 GT", 1994, 550000.00));
        cars.add(new Car(24, "Ferrari 348 Spider", 1993, 450000.00));
        cars.add(new Car(25, "Ferrari F40", 1989, 10000000.00));
        cars.add(new Car(26, "Ferrari 328 GTS", 1988, 400000.00));
        cars.add(new Car(27, "Ferrari Testarossa", 1986, 850000.00));
        cars.add(new Car(28, "Ferrari 512 BB", 1983, 750000.00));
    }
}

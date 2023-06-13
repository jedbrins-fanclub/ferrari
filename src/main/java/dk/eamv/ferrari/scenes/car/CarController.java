package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.managers.SessionManager;
import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.CRUDType;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Made by: Mikkel
// Modified by: Benjamin (extended CRUD for sales manager)
public class CarController {

    protected static FilteredTableBuilder<Car> filteredTableBuilder;
    private static ObservableList<Car> cars = FXCollections.observableArrayList(CarModel.readAll());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Car>()
            .withData(cars)
            .withColumn("Stelnummer", Car::getId)
            .withColumn("Model", Car::getModel)
            .withColumn("Årgang", Car::getYear)
            .withColumn("Pris (DKK)", Car::getPrice);

        if (SessionManager.getUser().isSalesManager()) {
            filteredTableBuilder
                .withButtonColumn(SVGResources.getEditIcon(), CarView::showEditCarDialog)
                .withButtonColumn(SVGResources.getDeleteIcon(), CarController::deleteCar);
        }
    }

    protected static void showCreateCar() {
        FormFactory.createDialogBox(CRUDType.CAR, "Opret Bil");
    }

    public static void createCar(Car car) {
        CarModel.create(car);
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

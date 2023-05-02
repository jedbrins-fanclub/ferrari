package dk.eamv.ferrari;

import dk.eamv.ferrari.car.Car;
import dk.eamv.ferrari.customer.Customer;
import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.resources.TestData;
import dk.eamv.ferrari.sharedcomponents.filter.*;
import dk.eamv.ferrari.sidebar.SidebarController;
import dk.eamv.ferrari.sidebar.SidebarView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private final BorderPane root = new BorderPane();
    private static final SidebarView sidebarView = new SidebarView();

    @Override
    public void start (Stage stage) {
        Database.init();

        new SidebarController(sidebarView);
        root.setLeft(sidebarView);

        TestData testData = new TestData();
        ObservableList<Car> cars = FXCollections.observableArrayList(testData.getAllCars());
        ObservableList<Customer> customers = FXCollections.observableArrayList(testData.getAllCustomers());

        FilteredTableViewBuilder<Car> carBuilder = new FilteredTableViewBuilder<Car>()
                .withData(cars)
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", car -> Integer.toString(car.getYear()))
                .withColumn("Pris", car -> Double.toString(car.getPrice()));

        FilteredTableView<Car> carTableView = carBuilder.build();
        FilterTextField<Car> carFilter = carBuilder.withFilterTextField(carTableView);

        FilteredTableViewBuilder<Customer> customerBuilder = new FilteredTableViewBuilder<Customer>()
                .withData(customers)
                .withColumn("Fornavn", Customer::getFirstName)
                .withColumn("Efternavn", Customer::getLastName)
                .withColumn("Telefonnummer", Customer::getPhoneNumber)
                .withColumn("E-mail", Customer::getEmail)
                .withColumn("Adresse", Customer::getAddress);
                //.withColumn("CPR", Customer::getCpr); // Does not need to add all fields of the given object

        FilteredTableView<Customer> customerTableView = customerBuilder.build();
        FilterTextField<Customer> customerFilter = customerBuilder.withFilterTextField(customerTableView);


        VBox container = new VBox();
        container.getChildren().addAll(carFilter, carTableView, customerFilter, customerTableView);

        root.setCenter(container);

        init(stage, root);
    }

    private void init(Stage stage, Parent root) {
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        Scene scene = new Scene(root);

        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Ferrari");
        stage.getIcons().add(new Image("file:src/main/resources/media/ferrari-emoji.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
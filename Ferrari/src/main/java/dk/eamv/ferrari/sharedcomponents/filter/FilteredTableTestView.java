package dk.eamv.ferrari.sharedcomponents.filter;

import dk.eamv.ferrari.car.Car;
import dk.eamv.ferrari.customer.Customer;
import dk.eamv.ferrari.loan.Loan;
import dk.eamv.ferrari.resources.TestData;
import dk.eamv.ferrari.employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilteredTableTestView extends HBox {

    public FilteredTableTestView() {
        TestData testData = new TestData();

        ObservableList<Car> cars = FXCollections.observableArrayList(testData.getAllCars());

        FilteredTableViewBuilder<Car> carBuilder = new FilteredTableViewBuilder<Car>()
                .withData(cars)
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", car -> Integer.toString(car.getYear()))
                .withColumn("Pris", car -> Double.toString(car.getPrice()));

        FilteredTableView<Car> carTableView = carBuilder.build();
        FilterTextField<Car> carFilter = carBuilder.withFilterTextField(carTableView);
        SearchContainer carSearch = new SearchContainer(carFilter);

        ObservableList<Customer> customers = FXCollections.observableArrayList(testData.getAllCustomers());

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
        SearchContainer customerSearch = new SearchContainer(customerFilter);

        ObservableList<Employee> sellers = FXCollections.observableArrayList(testData.getAllSellers());

        FilteredTableViewBuilder<Employee> sellerBuilder = new FilteredTableViewBuilder<Employee>()
                .withData(sellers)
                .withColumn("Fornavn", Employee::getFirstName)
                .withColumn("Efternavn", Employee::getLastName)
                .withColumn("Telefonnummer", Employee::getPhoneNumber)
                .withColumn("E-mail", Employee::getEmail)
                .withColumn("Max lån", Employee::getMaxLoan);

        FilteredTableView<Employee> sellerTableView = sellerBuilder.build();
        FilterTextField<Employee> sellerFilter = sellerBuilder.withFilterTextField(sellerTableView);
        SearchContainer sellerSearch = new SearchContainer(sellerFilter);

        ObservableList<Loan> loans = FXCollections.observableArrayList(testData.getAllLoans());

        FilteredTableViewBuilder<Loan> loanBuilder = new FilteredTableViewBuilder<Loan>()
                .withData(loans)
                .withColumn("Bil id", Loan::getCar_id)
                .withColumn("Kunde id", Loan::getCustomer_id)
                .withColumn("Sælger id", Loan::getEmployee_id)
                .withColumn("Lån", Loan::getLoanSize)
                .withColumn("Indskud", Loan::getDownPayment)
                .withColumn("Rente", Loan::getInterestRate)
                .withColumn("Status", loan -> loan.getStatus().getDisplayName());

        FilteredTableView<Loan> loanTableView = loanBuilder.build();
        FilterTextField<Loan> loanFilter = loanBuilder.withFilterTextField(loanTableView);
        SearchContainer loanSearch = new SearchContainer(loanFilter);


        VBox leftContainer = new VBox();
        leftContainer.setMinWidth(500);
        leftContainer.getChildren().addAll(carSearch, carTableView, customerSearch, customerTableView);

        VBox rightContainer = new VBox();
        rightContainer.setMinWidth(500);
        rightContainer.getChildren().addAll(sellerSearch, sellerTableView, loanSearch, loanTableView);

        getChildren().addAll(leftContainer, rightContainer);
    }
}

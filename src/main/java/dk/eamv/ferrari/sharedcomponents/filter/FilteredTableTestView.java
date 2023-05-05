package dk.eamv.ferrari.sharedcomponents.filter;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilteredTableTestView extends HBox {

    public FilteredTableTestView() {

        ObservableList<Car> cars = FXCollections.observableArrayList();

        FilteredTableBuilder<Car> carBuilder = new FilteredTableBuilder<Car>()
                .withData(cars)
                .withColumn("Model", Car::getModel)
                .withColumn("Årgang", car -> Integer.toString(car.getYear()))
                .withColumn("Pris", car -> Double.toString(car.getPrice()));

        FilteredTable<Car> carTableView = carBuilder.build();
        FilterTextField<Car> carFilter = new FilterTextField<>(carBuilder);
        SearchContainer carSearch = new SearchContainer(carFilter);

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        FilteredTableBuilder<Customer> customerBuilder = new FilteredTableBuilder<Customer>()
                .withData(customers)
                .withColumn("Fornavn", Customer::getFirstName)
                .withColumn("Efternavn", Customer::getLastName)
                .withColumn("Telefonnummer", Customer::getPhoneNumber)
                .withColumn("E-mail", Customer::getEmail)
                .withColumn("Adresse", Customer::getAddress);
        //.withColumn("CPR", Customer::getCpr); // Does not need to add all fields of the given object

        FilteredTable<Customer> customerTableView = customerBuilder.build();
        FilterTextField<Customer> customerFilter = new FilterTextField<>(customerBuilder);
        SearchContainer customerSearch = new SearchContainer(customerFilter);

        ObservableList<Employee> sellers = FXCollections.observableArrayList();

        FilteredTableBuilder<Employee> sellerBuilder = new FilteredTableBuilder<Employee>()
                .withData(sellers)
                .withColumn("Fornavn", Employee::getFirstName)
                .withColumn("Efternavn", Employee::getLastName)
                .withColumn("Telefonnummer", Employee::getPhoneNumber)
                .withColumn("E-mail", Employee::getEmail)
                .withColumn("Max lån", Employee::getMaxLoan);

        FilteredTable<Employee> sellerTableView = sellerBuilder.build();
        FilterTextField<Employee> sellerFilter = new FilterTextField<>(sellerBuilder);
        SearchContainer sellerSearch = new SearchContainer(sellerFilter);

        ObservableList<Loan> loans = FXCollections.observableArrayList();

        FilteredTableBuilder<Loan> loanBuilder = new FilteredTableBuilder<Loan>()
                .withData(loans)
                .withColumn("Bil id", Loan::getCar_id)
                .withColumn("Kunde id", Loan::getCustomer_id)
                .withColumn("Sælger id", Loan::getEmployee_id)
                .withColumn("Lån", Loan::getLoanSize)
                .withColumn("Indskud", Loan::getDownPayment)
                .withColumn("Rente", Loan::getInterestRate)
                .withColumn("Status", loan -> loan.getStatus().getDisplayName())
                .withColumn("Startdato", Loan::getStartDate)
                .withColumn("Slutdato", Loan::getEndDate);

        FilteredTable<Loan> loanTableView = loanBuilder.build();
        FilterTextField<Loan> loanFilter = new FilterTextField<>(loanBuilder);
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

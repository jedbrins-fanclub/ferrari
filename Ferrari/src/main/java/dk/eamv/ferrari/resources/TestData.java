package dk.eamv.ferrari.resources;

import dk.eamv.ferrari.car.Car;
import dk.eamv.ferrari.customer.Customer;
import dk.eamv.ferrari.employee.Employee;
import dk.eamv.ferrari.loan.Loan;
import dk.eamv.ferrari.loan.LoanState;
import dk.eamv.ferrari.loan.LoanStatus;
import java.sql.Date;
import java.util.ArrayList;

public class TestData {

    private final ArrayList<Car> cars;
    private final ArrayList<Customer> customers;
    private final ArrayList<Loan> loans;
    private final ArrayList<Employee> sellers;

    public TestData() {
        cars = createCars();
        customers = createCustomers();
        loans = createLoans();
        sellers = createSellers();
    }

    private ArrayList<Car> createCars() {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car(1, "Portofino", 2018, 1500000.00));
        cars.add(new Car(2, "F8 Tributo", 2020, 2300000.00));
        cars.add(new Car(3, "SF90 Stradale", 2021, 3500000.00));
        cars.add(new Car(4, "488 GTB", 2016, 1800000.00));
        cars.add(new Car(5, "812 Superfast", 2019, 2800000.00));
        return cars;
    }

    private ArrayList<Customer> createCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John", "Doe", "12345678", "john@example.com", "Some Street 1", "123456-7890"));
        customers.add(new Customer(2, "Jane", "Doe", "23456789", "jane@example.com", "Some Street 2", "234567-8901"));
        customers.add(new Customer(3, "James", "Smith", "34567890", "james@example.com", "Some Street 3", "345678-9012"));
        return customers;
    }

    private ArrayList<Loan> createLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan(1, 1, 1, 1, 1000000.00, 500000.00, 3.5, new Date(2000, 6, 11), new Date(2000, 6, 11), new LoanStatus(LoanState.PENDING)));
        loans.add(new Loan(2, 2, 2, 1, 2000000.00, 300000.00, 4.0, new Date(2000, 6, 11), new Date(2000, 6, 11), new LoanStatus(LoanState.ACTIVE)));
        loans.add(new Loan(3, 3, 3, 2, 3000000.00, 500000.00, 3.0, new Date(2000, 6, 11), new Date(2000, 6, 11), new LoanStatus(LoanState.COMPLETED)));
        return loans;
    }

    private ArrayList<Employee> createSellers() {
        ArrayList<Employee> sellers = new ArrayList<>();
        sellers.add(new Employee(1, "Michael", "Johnson", "56789012", "michael@example.com", "password1", 5000000.00));
        sellers.add(new Employee(2, "Samantha", "Williams", "67890123", "samantha@example.com", "password2", 4000000.00));
        return sellers;
    }

    public ArrayList<Car> getAllCars() {
        return cars;
    }

    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }

    public ArrayList<Loan> getAllLoans() {
        return loans;
    }

    public ArrayList<Employee> getAllSellers() {
        return sellers;
    }
}

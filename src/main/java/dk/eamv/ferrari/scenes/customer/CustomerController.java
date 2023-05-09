package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class CustomerController {

    protected static FilteredTableBuilder<Customer> filteredTableBuilder;
    private static final ObservableList<Customer> customers = FXCollections.observableArrayList(fetchCustomers());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Customer>()
                .withData(customers)
                .withColumn("Kundenummer", Customer::getId)
                .withColumn("Fornavn", Customer::getFirstName)
                .withColumn("Efternavn", Customer::getLastName)
                .withColumn("Telefonnummer", Customer::getPhoneNumber)
                .withColumn("Email", Customer::getPhoneNumber)
                .withColumn("Adresse", Customer::getAddress)
                .withColumn("CPR-nummer", Customer::getCpr)
                .withButtonColumn("", "Rediger", CustomerView::showEditCustomerDialog)
                .withButtonColumn("", "Slet", CustomerController::deleteCustomer);
    }

    protected static ArrayList<Customer> fetchCustomers() {
        return CustomerModel.readAll();
    }

    protected static void createCustomer(Customer customer) {
        System.out.println("Call method in CustomerModel create customer with id: " + customer.getId());

        CustomerView.refreshTableView();
    }

    protected static void updateCustomer(Customer customer) {
        System.out.println("Call method in CustomerModel update customer with id: " + customer.getId());

        CustomerView.refreshTableView();
    }

    protected static void deleteCustomer(Customer customer) {
        System.out.println("Call method in CustomerModel to delete customer with id: " + customer.getId());

        // When removing the customer from the ObservableList, the TableView updates automatically
        customers.remove(customer);
    }
}

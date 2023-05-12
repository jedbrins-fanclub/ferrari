package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class CustomerController {

    protected static FilteredTableBuilder<Customer> filteredTableBuilder;
    private static final ObservableList<Customer> customers = FXCollections.observableArrayList(CustomerModel.readAll());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Customer>()
                .withData(customers)
                .withColumn("Kundenr", Customer::getId)
                .withColumn("Fornavn", Customer::getFirstName)
                .withColumn("Efternavn", Customer::getLastName)
                .withColumn("Telefonnummer", Customer::getPhoneNumber)
                .withColumn("Email", Customer::getEmail)
                .withColumn("Adresse", Customer::getAddress)
                .withColumn("CPR-nummer", Customer::getCpr)
                .withButtonColumn("", "Ny låneaftale", CustomerController::createLoan)
                .withButtonColumn("", "Rediger", CustomerView::showEditCustomerDialog)
                .withButtonColumn("", "Slet", CustomerController::deleteCustomer);
    }

    protected static void createCustomer() {
        FormFactory.createCustomerFormDialogBox();
    }

    protected static void updateCustomer(Customer customer) {
        //TODO: Open dialog to update this customer

        CustomerView.refreshTableView();
    }

    protected static void deleteCustomer(Customer customer) {
        CustomerModel.delete(customer.getId());

        // When removing the customer from the ObservableList, the TableView updates automatically
        customers.remove(customer);
    }

    protected static void createLoan(Customer customer) {
        //TODO: Open dialog to create loan agreement for this customer
        System.out.println("opening new loan dialog for customer: " + customer.getId());
    }

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }
}

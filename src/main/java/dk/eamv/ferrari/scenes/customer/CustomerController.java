package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                .withIconButtonColumn(SVGResources.getEditIcon(), CustomerView::showEditCustomerDialog)
                .withIconButtonColumn(SVGResources.getDeleteIcon(), CustomerController::deleteCustomer);
    }

    protected static void createCustomer() {
        FormFactory.createCustomerFormDialogBox();
    }

    protected static void updateCustomer(Customer customer) {
        FormFactory.updateCustomerFormDialogBox(customer);

        CustomerView.refreshTableView();
    }

    protected static void deleteCustomer(Customer customer) {
        CustomerModel.delete(customer.getId());

        // When removing the customer from the ObservableList, the TableView updates automatically
        customers.remove(customer);
    }

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }
}

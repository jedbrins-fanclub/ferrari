package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.managers.SessionManager;
import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormType;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Made by: Mikkel
// Modified by: Benjamin (extended CRUD for sales manager)
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
            .withColumn("CPR-nummer", Customer::getCpr);

        if (SessionManager.getUser().isSalesManager()) {
                filteredTableBuilder
                .withButtonColumn(SVGResources.getEditIcon(), CustomerView::showEditCustomerDialog)
                .withButtonColumn(SVGResources.getDeleteIcon(), CustomerController::deleteCustomer)
                .withButtonColumn(SVGResources.getBanIcon(), CustomerController::banCustomer); // TODO: Dialog
        }
    }

    protected static void showCreateCustomer() {
        FormFactory.createDialogBox(FormType.CUSTOMER, "Opret Kunde");
    }

    public static void createCustomer(Customer customer) {
        CustomerModel.create(customer);
    }

    protected static void updateCustomer(Customer customer) {
        FormFactory.updateDialogBox(FormType.CUSTOMER, "Opdater kunde", customer);
    }

    protected static void deleteCustomer(Customer customer) {
        CustomerModel.delete(customer.getId());
        customers.remove(customer);
    }

    protected static void banCustomer(Customer customer) {
        customer.setStatus(CustomerStatus.BANNED);
        CustomerModel.update(customer);
        customers.remove(customer);
    }

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }
}

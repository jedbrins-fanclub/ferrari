package dk.eamv.ferrari.scenes.employee;

import dk.eamv.ferrari.managers.SessionManager;
import dk.eamv.ferrari.resources.SVGResources;
import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import dk.eamv.ferrari.sharedcomponents.forms.FormType;
import dk.eamv.ferrari.sharedcomponents.forms.FormFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Made by: Mikkel
// Modified by: Benjamin (extended CRUD for sales manager)
public class EmployeeController {
    protected static FilteredTableBuilder<Employee> filteredTableBuilder;
    private static final ObservableList<Employee> employees = FXCollections.observableArrayList(EmployeeModel.readAll());

    protected static void initFilterBuilder() {
        filteredTableBuilder = new FilteredTableBuilder<Employee>()
            .withData(employees)
            .withColumn("Medarbejdernummer", Employee::getId)
            .withColumn("Fornavn", Employee::getFirstName)
            .withColumn("Efternavn", Employee::getLastName)
            .withColumn("Telefonnummer", Employee::getPhoneNumber)
            .withColumn("Email", Employee::getEmail)
            .withColumn("Max lån (DKK)", Employee::getMaxLoan);
            
        if (SessionManager.getUser().isSalesManager()) {
            filteredTableBuilder
                .withButtonColumn(SVGResources.getEditIcon(), EmployeeView::showEditEmployeeDialog)
                .withButtonColumn(SVGResources.getDeleteIcon(), EmployeeController::deleteEmployee);
        }
    }

    protected static void showCreateEmployee() {
        FormFactory.createDialogBox(FormType.EMPLOYEE, "Opret Sælger");
    }

    public static void createEmployee(Employee employee) {
        EmployeeModel.create(employee);
    }

    protected static void updateEmployee(Employee employee) {
        FormFactory.updateDialogBox(FormType.EMPLOYEE, "Opdater sælger", employee);
    }

    protected static void deleteEmployee(Employee employee) {
        EmployeeModel.delete(employee.getId());

        // When removing the Employee from the ObservableList, the TableView updates automatically
        employees.remove(employee);
    }

    public static ObservableList<Employee> getEmployees() {
        return employees;
    }
}

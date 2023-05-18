package dk.eamv.ferrari.scenes.employee;

import dk.eamv.ferrari.sharedcomponents.filter.FilteredTableBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

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
                .withColumn("Max lån (DKK)", Employee::getMaxLoan)
                .withButtonColumn("", "Rediger", EmployeeView::showEditEmployeeDialog)
                .withButtonColumn("", "Slet", EmployeeController::deleteEmployee);
    }

    protected static void createEmployee(Employee employee) {
        System.out.println("Call method in EmployeeModel create employee with id: " + employee.getId());

        EmployeeView.refreshTableView();
    }

    protected static void updateEmployee(Employee employee) {
        System.out.println("Call method in EmployeeModel update employee with id: " + employee.getId());

        EmployeeView.refreshTableView();
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

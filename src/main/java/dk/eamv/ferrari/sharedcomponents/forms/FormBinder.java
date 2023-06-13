package dk.eamv.ferrari.sharedcomponents.forms;

import java.time.LocalDate;
import java.time.Period;

import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

// Made by: Christian

/**
 * Handles the logic of binding fields in loanforms, manually setting the fields and setting mouselisteners for the dialogboxes.
 */

public class FormBinder {
    private static Form form;
    private static double banksInterestRate;
    private static Rating customersCreditScore;

    /**
     * Sets the form instance variable.
     * @param form - the active form.
     */
    protected static void setForm(Form form) {
        FormBinder.form = form;
    }

    /**
     * Applies all the bind methods to the loan form.
     */
    protected static void applyLoanFormBinds() {
        bindLoanSize();
        bindFieldsCar();
        bindDatepickers();
        bindFieldsCustomer();
        bindFieldsEmployee();
    }

    /**
     * Manually refreshes the binds. Used when updating, since the binds dont automatically refresh when the update button is clicked.
     */
    protected static void refreshLoanFormBinds(Loan loan) {
        setFieldsLoanCar(FormInputHandler.getEntityFromComboBox("Bil"));
        setFieldsLoanCustomer(FormInputHandler.getEntityFromComboBox("CPR & Kunde"));
        setFieldsLoanEmployee(FormInputHandler.getEntityFromComboBox("Medarbejder"));
        setFieldsLoanLoan(loan);
    }

    /**
     * Binds the fields related to the Car dropdown.
     * Binds it to action (On dropdown selected) to autofill the fields related to the car.
     */
    protected static void bindFieldsCar() {
        TextField loanSize = FormInputHandler.getTextField("Lånets størrelse");
        AutoCompleteComboBox<Car> comboBox = FormInputHandler.getAutoCompleteComboBox("Bil");
        comboBox.setOnAction(e -> {
            Car car = comboBox.getSelectedItem();
            if (car != null) {
                setFieldsLoanCar(car);
                loanSize.setText(calculateLoanSize());
                calculateInterestRate();
            }
        });
    }

    /**
     * Binds the fields related to the Customer dropdown.
     * Binds it to action (On dropdown selected) to autofill the fields related to the Customer.
     */
    protected static void bindFieldsCustomer() {
        AutoCompleteComboBox<Customer> comboBox = FormInputHandler.getAutoCompleteComboBox("CPR & Kunde");
        comboBox.setOnAction(e -> {
            Customer customer = comboBox.getSelectedItem();
            if (customer != null) {
                FormThreadHandler.checkRKI();
                setFieldsLoanCustomer(customer);
            }
        });
    }

    /**
     * Binds the fields related to the Employee dropdown.
     * Binds it to action (On dropdown selected) to autofill the fields related to the Employee.
     */
    protected static void bindFieldsEmployee() {
        AutoCompleteComboBox<Employee> comboBox = FormInputHandler.getAutoCompleteComboBox("Medarbejder");
        comboBox.setOnAction(e -> {
            Employee employee = comboBox.getSelectedItem();
            if (employee != null) {
                setFieldsLoanEmployee(employee);
            }
        });
    }

    /**
     * Binds the fields related to the Loan dropdown.
     * Binds it to action (On dropdown selected) to autofill the fields related to the Loan.
     */
    protected static void bindLoanSize() {
        TextField loanSize = FormInputHandler.getTextField("Lånets størrelse");
        TextField downPayment = FormInputHandler.getTextField("Udbetaling");

        downPayment.setOnKeyTyped(e -> {
            loanSize.setText(calculateLoanSize());
            calculateInterestRate();
        });
    }

    /**
     * Binds the fields related to the DatePickers.
     * Binds it to action (On datepicked selected) to autofill the fields related to the Dates / loanperiod.
     */
    protected static void bindDatepickers() {
        FormInputHandler.getDatePicker("Start dato DD/MM/ÅÅÅÅ").setOnAction(e -> calculateInterestRate());
        FormInputHandler.getDatePicker("Slut dato DD/MM/ÅÅÅÅ").setOnAction(e -> calculateInterestRate());
    }

    /**
     * Sets the fields related to Car, in the loan form, when called.
     * @param car - the Car object whose properties will fill the fields.
     */
    protected static void setFieldsLoanCar(Car car) {
        FormInputHandler.getTextField("Model").setText(car.getModel());
        FormInputHandler.getTextField("Årgang").setText(String.valueOf(car.getYear()));
        FormInputHandler.getTextField("Pris").setText(String.valueOf(car.getPrice()));
        FormInputHandler.getTextField("Stelnummer").setText(String.valueOf(car.getId()));
    }
    
    /**
     * Sets the fields related to Customer, in the loan form, when called.
     * @param customer - the Customer object whose properties will fill the fields.
     */
    protected static void setFieldsLoanCustomer(Customer customer) {
        FormInputHandler.getTextField("Kundens Fornavn").setText(customer.getFirstName());
        FormInputHandler.getTextField("Kundens Efternavn").setText(customer.getLastName());
        FormInputHandler.getTextField("Kundens CPR").setText(customer.getCpr());
        FormInputHandler.getTextField("Kundens Telefon nr.").setText(customer.getPhoneNumber());
        FormInputHandler.getTextField("Kundens Adresse").setText(customer.getAddress());
        FormInputHandler.getTextField("Kundens Email").setText(customer.getEmail());
    }

    /**
     * Sets the fields related to Employee, in the loan form, when called.
     * @param employee - the Employee object whose properties will fill the fields.
     */
    protected static void setFieldsLoanEmployee(Employee employee) {
        FormInputHandler.getTextField("Medarbejderens Fornavn").setText(employee.getFirstName());
        FormInputHandler.getTextField("Medarbejderens Efternavn").setText(employee.getLastName());
        FormInputHandler.getTextField("Medarbejderens ID").setText(String.valueOf(employee.getId()));
        FormInputHandler.getTextField("Medarbejderens Telefon nr.").setText(employee.getPhoneNumber());
        FormInputHandler.getTextField("Medarbejderens Email").setText(employee.getEmail());
    }

    /**
     * Sets the fields related to Loan, in the loan form, when called.
     * @param loan - the Loan object whose propersties will fill the fields.
     */
    protected static void setFieldsLoanLoan(Loan loan) {
        FormInputHandler.getTextField("Rente").setText(String.valueOf(loan.getInterestRate()));
        FormInputHandler.getTextField("Lånets størrelse").setText(String.valueOf(loan.getLoanSize()));
        FormInputHandler.getTextField("Udbetaling").setText(String.valueOf(loan.getDownPayment()));
    }
    
    /**
     * Sets the mouse listener for the "OK" button in the form, based on the passed argument.
     * @param type - the CRUD Type (Car, Customer, Employee, Loan)
     */
    protected static void setCreateMouseListener(CRUDType type) {
        Button buttonOK = FormWrapper.getButtonOK();

        buttonOK.setOnMouseClicked(e -> {
            if (!form.verifyHasFilledFields()) {
                FormWrapper.showStatusLabel(true, "Mangler input i de markerede felter");
                return;
            }

            switch (type) {
                case CAR: {
                    Car car = FormInputHandler.getFieldsCar();
                    CarController.getCars().add(car);
                    CarController.createCar(car);
                    break;
                }

                case CUSTOMER: {
                    Customer customer = FormInputHandler.getFieldsCustomer();
                    CustomerController.getCustomers().add(customer);
                    CustomerController.createCustomer(customer);
                    break;
                }

                case EMPLOYEE: {
                    Employee employee = FormInputHandler.getFieldsEmployee();
                    EmployeeController.getEmployees().add(employee);
                    EmployeeController.createEmployee(employee);
                    break;
                }

                case LOAN: {
                    if (customersCreditScore.equals(Rating.D)) {
                        FormWrapper.showStatusLabel(true, "Kunden har kreditværdighed D");
                        return;
                    }

                    if (Double.valueOf(calculateLoanSize()) < 0) {
                        FormWrapper.showStatusLabel(true, "Lånets størrelse kan ikke være mindre end det udbetalte beløb");
                        return;
                    }

                    Employee employee = FormInputHandler.getEntityFromComboBox("Medarbejder");
                    if (employee.getMaxLoan() < FormInputHandler.getDouble("Lånets størrelse")) {
                        FormWrapper.showStatusLabel(true, "Lånets størrelse overskrider medarbejderens beføjelser.");
                        form.getForwardBoss().setVisible(true);
                        return;
                    }

                    Loan loan = FormInputHandler.getFieldsLoan();
                    LoanController.getLoans().add(loan);
                    LoanController.createLoan(loan);
                    break;
                }
            }

            FormWrapper.closeDialog(FormWrapper.getDialog());
        });
    }

    /**
     * Calculates the loan size, based on the input in the downpayment field & the Car chosen in the dropdown.
     * @return returned as a String, not an int, so it can be directly used to set the value of a TextField.
     * @see FormInputHandler#getDouble(String)
     */
    private static String calculateLoanSize() {
        //Both start at 0, so we can return something (0 in this case) if theyre null & empty.
        double price = 0;
        double downpayment = 0;

        Car car = FormInputHandler.getEntityFromComboBox("Bil");
        if (car != null) {
            price = car.getPrice();
        }

        TextField textField = FormInputHandler.getTextField("Udbetaling");
        if (!textField.getText().isEmpty()) {
            downpayment = FormInputHandler.getDouble("Udbetaling"); //call this instead of textField, to format ,s to .s (check javadoc).
        }

        return String.valueOf(price - downpayment);
    }

    /**
     * Calculates the total interest rate, based on the listed conditions in the program requirements.
     * @see FormInputHandler#getDouble(String)
     */
    protected static void calculateInterestRate() {
        double totalInterestRate = 0.0;
        double downpayment = 0;
        double carPrice = 0;

        totalInterestRate += banksInterestRate; //add banks rate

        totalInterestRate += getCreditScoreInterestRate(customersCreditScore); //add interest rate based on customers credit score

        TextField downpaymentField = FormInputHandler.getTextField("Udbetaling");
        if (!downpaymentField.getText().isEmpty()) {
            downpayment = FormInputHandler.getDouble("Udbetaling"); //calls this method to convert ","s to "."s (check javadoc).
        }

        Car selectedCar = FormInputHandler.getEntityFromComboBox("Bil");
        if (selectedCar != null) {
            carPrice = selectedCar.getPrice();
        }

        if (!downpaymentField.getText().isEmpty() && selectedCar != null) {
            if (downpayment / carPrice < 0.5) { //add 1% if loansize > 50%
                totalInterestRate += 1;
            }
        }

        DatePicker start = FormInputHandler.getDatePicker("Start dato DD/MM/ÅÅÅÅ");
        DatePicker end = FormInputHandler.getDatePicker("Slut dato DD/MM/ÅÅÅÅ");

        if (start.getValue() != null && end.getValue() != null) {
            if (calculateDaysBetween(start, end) > 3 * 365) { //add 1% if loan period > 3 years.
                totalInterestRate += 1;
            }
        }

        TextField interestField = FormInputHandler.getTextField("Rente");
        interestField.setText(String.format("%.2f", totalInterestRate));
    }

    /**
     * Sets the banks interestrate to the argument.
     * @param interestRate - the banks interest rate.
     */
    protected static void setBanksInterestRate(double interestRate) {
        banksInterestRate = interestRate;
    }

    /**
     * Sets the customers credit score to the argument.
     * @param rating - the customers credit score.
     */
    protected static void setCustomersCreditScore(Rating rating) {
        customersCreditScore = rating;
    }
    
    /**
     * Takes the customers rating and returns the added interest rate as an int.
     * @param creditRating - the customers credit score.
     * @return - the interest rate based on the customers credit score.
     */
    private static int getCreditScoreInterestRate(Rating creditRating) {
        int interestRate = 0;

        if (creditRating == null) {
            return interestRate;
        }

        switch (creditRating) {
            case A -> interestRate += 1;
            case B -> interestRate += 2;
            case C -> interestRate += 3;
            default -> interestRate += 0;
        }

        return interestRate;
    }

    /**
    * Takes the DatePickers and gets their value. Then calculates the days between the inputs.
     * @param start - the start date picker
     * @param end - the end date picker
     * @return the days between the 2 selected dates, as an int.
     */
    private static int calculateDaysBetween(DatePicker start, DatePicker end) {
        LocalDate startDate = start.getValue();
        LocalDate endDate = end.getValue();
        Period period = Period.between(startDate, endDate);
        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();

        double totalDays = days + months * 30.5 + years * 365;

        return (int) totalDays;
    }
}

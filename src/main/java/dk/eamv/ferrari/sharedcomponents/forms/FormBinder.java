package dk.eamv.ferrari.sharedcomponents.forms;

import java.time.LocalDate;
import java.time.Period;

import dk.api.rki.CreditRator;
import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class FormBinder {
    // Lavet af Christian.

    /**
     * Handles the logic of binding fields in loanforms, manually setting the fields & 
     * setting mouselisteners for the dialogboxes.
     */

    /**
     * Binds the fields related to the Car dropdown.
     * Casts the Control into a TextField, then binds it to action (On dropdown selected)
     * to autofill the fields related to the car.
     * @param form - the active form.
     */
    private static void bindFieldsCar(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        AutoCompleteComboBox<Car> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Bil");
        comboBox.setOnAction(e -> {
            Car car = FormInputHandler.getFromComboBox(form, "Bil");
            Car car = FormInputHandler.getFromComboBox(form, "Bil");
            if (car != null) {
                setFieldsLoanCar(form, car);
                loanSize.setText(calculateLoanSize(form));
                calculateInterestRate(form);
            }
        });
    }

    /**
     * Binds the fields related to the Customer dropdown.
     * Casts the Control into a TextField, then binds it to action (On dropdown selected)
     * to autofill the fields related to the Customer.
     * @param form - the active form.
     */
    private static void bindFieldsCustomer(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("CPR & Kunde");
        comboBox.setOnAction(e -> {
            Customer customer = FormInputHandler.getFromComboBox(form, "CPR & Kunde");
            if (customer != null) {
                //TODO: checkRKI(form);
                //TODO: setFieldsCustomer(form, customer);
            }
        });
    }

    /**
     * Binds the fields related to the Employee dropdown.
     * Casts the Control into a TextField, then binds it to action (On dropdown selected)
     * to autofill the fields related to the Employee.
     * @param form - the active form.
     */
    private static void bindFieldsEmployee(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Medarbejder");
        comboBox.setOnAction(e -> {
            Employee employee = FormInputHandler.getFromComboBox(form, "Medarbejder");
            if (employee != null) {
                setFieldsLoanEmployee(form, employee);
            }
        });
    }

    /**
     * Binds the fields related to the Loan dropdown.
     * Casts the Control into a TextField, then binds it to action (On dropdown selected)
     * to autofill the fields related to the Loan.
     * @param form - the active form.
     */
    private static void bindLoanSize(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        TextField downPayment = (TextField) form.getFieldMap().get("Udbetaling");
        downPayment.setOnKeyTyped(e -> {
            loanSize.setText(calculateLoanSize(form));
            calculateInterestRate(form);
        });
    }

    /**
     * Binds the fields related to the DatePickers.
     * Casts the Controls into DatePickers, then binds it to action (On datepicked selected)
     * to autofill the fields related to the Dates / loanperiod.
     * @param form - the active form.
     */
    private static void bindDatepickers(Form form) {
        DatePicker starDatePicker = ((DatePicker) form.getFieldMap().get("Start dato DD/MM/ÅÅÅÅ"));
        starDatePicker.setOnAction(e -> calculateInterestRate(form));
        DatePicker endDatePicker = ((DatePicker) form.getFieldMap().get("Slut dato DD/MM/ÅÅÅÅ"));
        endDatePicker.setOnAction(e -> calculateInterestRate(form));
    }

    /**
     * Sets the fields related to Car, in the loan form, manually, when called.
     * @param form - the active form.
     * @param car - the Car object whose properties will fill the fields.
     */
    private static void setFieldsLoanCar(Form form, Car car) {
        FormInputHandler.setText(form, "Model", car.getModel());
        FormInputHandler.setText(form, "Årgang", String.valueOf(car.getYear()));
        FormInputHandler.setText(form, "Pris", String.valueOf(car.getPrice()));
        FormInputHandler.setText(form, "Stelnummer", String.valueOf(car.getId()));
    }
    
    /**
     * Sets the fields related to Customer, in the loan form, manually, when called.
     * @param form - the active form.
     * @param customer - the Customer object whose properties will fill the fields.
     */
    private static void setFieldsLoanCustomer(Form form, Customer customer) {
        FormInputHandler.setText(form, "Kundens Fornavn", customer.getFirstName());
        FormInputHandler.setText(form, "Kundens Efternavn", customer.getLastName());
        FormInputHandler.setText(form, "Kundens CPR", customer.getCpr());
        FormInputHandler.setText(form, "Kundens Telefon nr.", customer.getPhoneNumber());
        FormInputHandler.setText(form, "Kundens Adresse", customer.getAddress());
        FormInputHandler.setText(form, "Kundens Email", customer.getEmail());
    }

    /**
     * Sets the fields related to Employee, in the loan form, manually, when called.
     * @param form - the active form.
     * @param employee - the Employee object whose properties will fill the fields.
     */
    private static void setFieldsLoanEmployee(Form form, Employee employee) {
        FormInputHandler.setText(form, "Medarbejderens Fornavn", employee.getFirstName());
        FormInputHandler.setText(form, "Medarbejderens Efternavn", employee.getLastName());
        FormInputHandler.setText(form, "Medarbejderens ID", String.valueOf(employee.getId()));
        FormInputHandler.setText(form, "Medarbejderens Telefon nr.", employee.getPhoneNumber());
        FormInputHandler.setText(form, "Medarbejderens Email", employee.getEmail());
    }

    /**
     * Sets the fields related to Loan, in the loan form, manually, when called.
     * @param form - the active form.
     * @param loan - the Loan object whose propersties will fill the fields.
     */
    private static void setFieldsLoanLoan(Form form, Loan loan) {
        FormInputHandler.setText(form, "Rente", String.valueOf(loan.getInterestRate()));
        FormInputHandler.setText(form, "Lånets størrelse", String.valueOf(loan.getLoanSize()));
        FormInputHandler.setText(form, "Udbetaling", String.valueOf(loan.getDownPayment()));
    }

    private static void setFieldsLoanDownpayment(Form form, Loan loan) {
        TextField textField = ((TextField) form.getFieldMap().get("Udbetaling"));
        textField.setText(String.valueOf(loan.getDownPayment()));
    }

    private static void setCreateMouseListener(CRUDType type, Form form, Dialog dialog) {
        switch (type) {
            case LOAN:
                bindLoanSize(form);
                bindFieldsCar(form);
                bindDatepickers(form);
                bindFieldsCustomer(form);
                bindFieldsEmployee(form);

                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }

                    if (creditRating.equals(Rating.D)) {
                        showCreditRatingError();
                        return;
                    }

                    Customer customer = getFromComboBox(form, "CPR & Kunde");
                    if (CreditRator.i().rate(customer.getCpr()).equals(Rating.D)) {
                        displayErrorMessage("Kunden har kreditværdighed D");
                        return;
                    }

                    if (Double.valueOf(calculateLoanSize(form)) < 0) {
                        displayErrorMessage("Lånets størrelse kan ikke være mindre end det udbetalte beløb");
                        return;
                    }

                    Employee employee = getFromComboBox(form, "Medarbejder");
                    if (employee.getMaxLoan() < getDouble(form, "Lånets størrelse")) {
                        displayErrorMessage("Lånets størrelse overskrider medarbejderens beføjelser.");
                        form.getForwardBoss().setVisible(true);
                        return;
                    }

                    Loan loan = getFieldsLoan(form);
                    LoanController.getLoans().add(loan);
                    LoanModel.create(loan);
                    dialog.close();
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        getErrorLabel().setText("Mangler input i markerede felter");
                        return;
                    }

                    Customer customer = getFieldsCustomer(form);
                    CustomerController.getCustomers().add(customer);
                    CustomerModel.create(customer);
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }

                    Car car = getFieldsCar(form, dialog);
                    CarController.getCars().add(car);
                    CarModel.create(car);
                });
                break;

            case EMPLOYEE:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }

                    Employee employee = getFieldsEmployee(form, dialog);
                    EmployeeController.getEmployees().add(employee);
                    EmployeeModel.create(employee);
                });
                break;

            default:
                break;
        }
    }
    
    private static String calculateLoanSize(Form form) {
        double price = 0;
        double downpayment = 0;
        
        Car car = getFromComboBox(form, "Bil");
        if (car != null) {
            price = car.getPrice();
        } 

        TextField textField = ((TextField) form.getFieldMap().get("Udbetaling"));
        if (!textField.getText().isEmpty()) {
            downpayment = Double.valueOf(textField.getText());
        } 

        return String.valueOf(price - downpayment);
    }

    private static void calculateInterestRate(Form form) {
        double totalInterestRate = 0.0;
        
        totalInterestRate += interestRate; //add banks rate

        if (creditRating != null) {
            switch (creditRating) { //add based on creditscore
                case A:
                    totalInterestRate += 1;
                    break;

                case B:
                    totalInterestRate += 2;
                    break;

                case C:
                    totalInterestRate += 3;
                    break;

                default:
                    break;
            }
        }
        
        TextField downpaymentField = (TextField) form.getFieldMap().get("Udbetaling");
        double downpayment = 0;
        if (!downpaymentField.getText().isEmpty()) {
            downpayment = Double.valueOf(downpaymentField.getText());
        }

        Car selectedCar = (Car) getFromComboBox(form, "Bil");
        double carPrice = 0;
        if (selectedCar != null) {
            carPrice = selectedCar.getPrice();
        }
        
        if (!downpaymentField.getText().isEmpty() && selectedCar != null) {
            if (downpayment / carPrice < 0.5) { //add 1% if loansize > 50%
                totalInterestRate += 1;
            }
        }

        DatePicker start = (DatePicker) form.getFieldMap().get("Start dato DD/MM/ÅÅÅÅ");
        DatePicker end = (DatePicker) form.getFieldMap().get("Slut dato DD/MM/ÅÅÅÅ");

        if (start.getValue() != null && end.getValue() != null) {
            if (calculateDaysBetween(start, end) > 3 * 365) { //add 1% if loan period > 3 years.
                totalInterestRate += 1;
            }
        }

        TextField interestField = (TextField) form.getFieldMap().get("Rente");
        interestField.setText(String.format("%.2f", totalInterestRate));
    }   

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

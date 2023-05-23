package dk.eamv.ferrari.sharedcomponents.forms;

public class FormBinder {
    // Lavet af Christian.

    /**
     * Handles the logic of binding fields in loanforms.
     * 
     */

    private static void bindFieldsCar(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        AutoCompleteComboBox<Car> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Bil");
        comboBox.setOnAction(e -> {
            Car car = getFromComboBox(form, "Bil");
            if (car != null) {
                setFieldsLoanCar(form, car);
                loanSize.setText(calculateLoanSize(form));
                calculateInterestRate(form);
            }
        });
    }

    private static void bindFieldsCustomer(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("CPR & Kunde");
        comboBox.setOnAction(e -> {
            Customer customer = getFromComboBox(form, "CPR & Kunde");
            if (customer != null) {
                checkRKI(form);
                setFieldsCustomer(form, customer);
            }
        });
    }

    private static void bindFieldsEmployee(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Medarbejder");
        comboBox.setOnAction(e -> {
            Employee employee = getFromComboBox(form, "Medarbejder");
            if (employee != null) {
                setFieldsLoanEmployee(form, employee);
            }
        });
    }

    private static void bindLoanSize(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        TextField downPayment = (TextField) form.getFieldMap().get("Udbetaling");
        downPayment.setOnKeyTyped(e -> {
            loanSize.setText(calculateLoanSize(form));
            calculateInterestRate(form);
        });
    }

    private static void bindDatepickers(Form form) {
        DatePicker starDatePicker = ((DatePicker) form.getFieldMap().get("Start dato DD/MM/ÅÅÅÅ"));
        starDatePicker.setOnAction(e -> calculateInterestRate(form));
        DatePicker endDatePicker = ((DatePicker) form.getFieldMap().get("Slut dato DD/MM/ÅÅÅÅ"));
        endDatePicker.setOnAction(e -> calculateInterestRate(form));
    }

    private static void setFieldsLoanCar(Form form, Car car) {
        setText(form, "Model", car.getModel());
        setText(form, "Årgang", String.valueOf(car.getYear()));
        setText(form, "Pris", String.valueOf(car.getPrice()));
        setText(form, "Stelnummer", String.valueOf(car.getId()));
    }
    
    private static void setFieldsLoanCustomer(Form form, Customer customer) {
        setText(form, "Kundens Fornavn", customer.getFirstName());
        setText(form, "Kundens Efternavn", customer.getLastName());
        setText(form, "Kundens CPR", customer.getCpr());
        setText(form, "Kundens Telefon nr.", customer.getPhoneNumber());
        setText(form, "Kundens Adresse", customer.getAddress());
        setText(form, "Kundens Email", customer.getEmail());
    }

    private static void setFieldsLoanEmployee(Form form, Employee employee) {
        setText(form, "Medarbejderens Fornavn", employee.getFirstName());
        setText(form, "Medarbejderens Efternavn", employee.getLastName());
        setText(form, "Medarbejderens ID", String.valueOf(employee.getId()));
        setText(form, "Medarbejderens Telefon nr.", employee.getPhoneNumber());
        setText(form, "Medarbejderens Email", employee.getEmail());
    }

    private static void setFieldsMiscLoan(Form form, Loan loan) {
        getTextField(form, "Rente").setText(String.valueOf(loan.getInterestRate()));
        getTextField(form, "Lånets størrelse").setText(String.valueOf(loan.getLoanSize()));
        getTextField(form, "Udbetaling").setText(String.valueOf(loan.getDownPayment()));
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
